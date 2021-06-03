package skarlat.dev.ecoproject.network

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.core.AppCache
import timber.log.Timber
import javax.inject.Inject

class FireBaseAuthenticator @Inject constructor(private val appCache: AppCache) : Authenticator() {

    private val fireBaseAuth  = FirebaseAuth.getInstance()

    override val currentUser: User?
        get() {
            val firebaseUser = fireBaseAuth.currentUser ?: return null
            val user = User(firebaseUser.displayName ?: "", firebaseUser.email ?: "")
            return user
        }
    override fun authenticate(bundle: Bundle) {
        when (bundle[Const.AUTH_METHOD] as? AuthMethod) {
            AuthMethod.PassLogin -> authLoginPass(bundle)
            AuthMethod.Google -> authGoogle()
        }
    }

    private fun authGoogle() {

    }

    private fun authLoginPass(authData: Bundle) {
        fireBaseAuth
                .signInWithEmailAndPassword(authData.email, authData.password)
                .addOnFailureListener { Timber.e(it,"authLoginPass is fail") }
                .addOnSuccessListener {
                    processAuthSuccess()
                }
    }

    private fun processAuthSuccess() {
        currentUser?.let { appCache.setUser(it) } ?: Timber.e("Firebase user is null")
    }

    override fun logout() {
        fireBaseAuth.signOut()
        appCache.clear()
    }

    @Serializable
    enum class AuthMethod {
        PassLogin,
        Google
    }

    private val Bundle.email : String
        get() = this[Const.AUTH_LOGIN] as? String ?: throw invalidBundleException

    private val Bundle.password : String
        get() = this[Const.AUTH_PASS] as? String ?: throw invalidBundleException

    private val invalidBundleException = Exception("Invalid bundle for auth")
}