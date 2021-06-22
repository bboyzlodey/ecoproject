package skarlat.dev.ecoproject.authentication.strategy

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.authentication.AppAuthenticator
import skarlat.dev.ecoproject.authentication.user
import timber.log.Timber

class LoginPasswordStrategy : AuthStrategy() {
    private val fireBaseAuth = FirebaseAuth.getInstance()
    override val authType: AppAuthenticator.AuthMethod = AppAuthenticator.AuthMethod.PassLogin

    override fun authenticate(context: Bundle) {
        fireBaseAuth.signInWithEmailAndPassword(context.email, context.password)
                .addOnFailureListener {
                    errorHandler?.onError(it)
                    Timber.e(it, "authLoginPass is fail")
                }
                .addOnSuccessListener {
                    processAuthSuccess()
                }
    }

    private fun processAuthSuccess() {
        fireBaseAuth.currentUser?.let { appCache.setUser(it.user) }
    }

    override fun logout() {
        super.logout()
        fireBaseAuth.signOut()
    }


    private val Bundle.email: String
        get() = this[Const.AUTH_LOGIN] as? String ?: throw invalidBundleException

    private val Bundle.password: String
        get() = this[Const.AUTH_PASS] as? String ?: throw invalidBundleException

    private val invalidBundleException = Exception("Invalid bundle for auth")
}