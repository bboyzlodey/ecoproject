package skarlat.dev.ecoproject.authentication

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.Serializable
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.authentication.strategy.AuthStrategy
import skarlat.dev.ecoproject.data.AppCache
import skarlat.dev.ecoproject.data.User
import skarlat.dev.ecoproject.network.FirebaseAPI
import timber.log.Timber
import javax.inject.Inject

class AppAuthenticator @Inject constructor(private val appCache: AppCache, private val authStrategies: Map<AppAuthenticator.AuthMethod, AuthStrategy>) : Authenticator() {

    private val fireBaseAuth = FirebaseAuth.getInstance()

    override fun authenticate(bundle: Bundle) {
        authStrategy = authStrategies[AuthMethod.valueOf(bundle.authMethod)]
        authStrategy?.authenticate(bundle)
    }

    private fun processRegistrationSuccess() {
        fireBaseAuth.currentUser?.let {
            FirebaseAPI.increaseCountOfUsers()
            appCache.setUser(it.user)
        }
    }

    override suspend fun logout() {
        Timber.d("Logout")
        val logguedUser = appCache.userFlow.firstOrNull()
        Timber.d("loginned user: ${logguedUser}")
        authStrategy = authStrategies[logguedUser?.authMethod]
        appCache.clear()
        authStrategy?.logout()
    }

    override suspend fun register(bundle: Bundle) {
        fireBaseAuth.createUserWithEmailAndPassword(bundle.email, bundle.password)
                .addOnFailureListener { Timber.e(it, "register is fail") }
                .addOnSuccessListener {
                    it.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(bundle.name).build())
                            ?.addOnSuccessListener { processRegistrationSuccess() }
                }
    }

    @Serializable
    enum class AuthMethod {
        PassLogin,
        Google
    }

    private val Bundle.email: String
        get() = this[Const.AUTH_LOGIN] as? String ?: throw invalidBundleException

    private val Bundle.password: String
        get() = this[Const.AUTH_PASS] as? String ?: throw invalidBundleException

    private val Bundle.name: String
        get() = this[Const.REGISTER_NAME] as? String ?: throw invalidBundleException

    private val Bundle.authMethod: String
        get() = this[Const.AUTH_METHOD] as? String ?: throw invalidBundleException

    private val invalidBundleException = Exception("Invalid bundle for auth")
}

val FirebaseUser.user get() = User(displayName.orEmpty(), email.orEmpty(), AppAuthenticator.AuthMethod.PassLogin)