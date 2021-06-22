package skarlat.dev.ecoproject.authmanager.strategy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.authmanager.AuthError
import skarlat.dev.ecoproject.authmanager.AuthErrorHandler
import skarlat.dev.ecoproject.network.AppAuthenticator
import skarlat.dev.ecoproject.network.user
import timber.log.Timber

sealed class AuthStrategy {
    protected val appCache = EcoTipsApp.appComponent.getAppCache()
    var errorHandler: AuthErrorHandler? = null
    abstract val authType: AppAuthenticator.AuthMethod
    abstract fun authenticate(context: Bundle)
    open fun logout() {
        appCache.clear()
    }
}


class GoogleSignInStrategy(activity: AppCompatActivity) : AuthStrategy() {

    private val gso
        get() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build()

    private val signInResultApi = activity.registerForActivityResult(
            object : ActivityResultContract<Unit, User?>() {
                override fun createIntent(context: Context, input: Unit?): Intent {
                    return googleSignInClient.signInIntent
                }

                override fun parseResult(resultCode: Int, intent: Intent?): User? {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                    if (!task.isSuccessful) {
                        errorHandler?.onError(AuthError.Default())
                    }
                    return task.result?.user
                }

            }
    ) { result ->
        Timber.d("onActivityResult $result")
        result?.let { emmitUserToCache(it) }
    }

    private val googleSignInClient by lazy { GoogleSignIn.getClient(activity, gso) }
    private val coroutineScope = activity.lifecycleScope
    override val authType: AppAuthenticator.AuthMethod = AppAuthenticator.AuthMethod.Google

    override fun authenticate(context: Bundle) {
        signInResultApi.launch(Unit)
    }

    override fun logout() {
        super.logout()
        googleSignInClient.signOut()
    }


    private fun emmitUserToCache(user: User) {
        coroutineScope.launch {
            EcoTipsApp.appComponent.getAppCache().setUser(user)
        }
    }

    private val GoogleSignInAccount.user
        get() = User(this.displayName?.substringBefore(" ").orEmpty(), this.email.orEmpty())
}

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