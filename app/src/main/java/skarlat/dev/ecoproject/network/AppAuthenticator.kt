package skarlat.dev.ecoproject.network

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.serialization.Serializable
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.core.AppCache
import timber.log.Timber
import javax.inject.Inject

class AppAuthenticator @Inject constructor(private val appCache: AppCache) : Authenticator() {

    private val fireBaseAuth = FirebaseAuth.getInstance()

    override fun authenticate(bundle: Bundle) {
        authStrategy?.authenticate(bundle)
    }

    private fun processRegistrationSuccess() {
        fireBaseAuth.currentUser?.let { appCache.setUser(it.user) }
    }

    override fun logout() {
        authStrategy?.logout()
        appCache.clear()
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

    private val invalidBundleException = Exception("Invalid bundle for auth")
}

val FirebaseUser.user get() = User(displayName.orEmpty(), email.orEmpty())