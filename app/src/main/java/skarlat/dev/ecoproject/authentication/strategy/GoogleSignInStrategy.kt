package skarlat.dev.ecoproject.authentication.strategy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.authentication.AppAuthenticator
import skarlat.dev.ecoproject.core.Error
import skarlat.dev.ecoproject.core.ErrorBus
import timber.log.Timber

class GoogleSignInStrategy(activity: AppCompatActivity, errorBus: ErrorBus) : AuthStrategy() {

    private val gso
        get() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .build()

    private val googleSignInClient by lazy { GoogleSignIn.getClient(activity, gso) }

    private val signInResultApi = activity.registerForActivityResult(
        object : ActivityResultContract<Unit, User?>() {
            override fun createIntent(context: Context, input: Unit?): Intent {
                return googleSignInClient.signInIntent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): User? {
                if (Activity.RESULT_OK == resultCode) {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                        if (!task.isSuccessful) {
                            errorBus.error.tryEmit(Error.AuthError.SignInWithGoogle(task.exception))
                        }
                        return task.result?.user
                    } catch (e: ApiException) {
                        errorBus.error.tryEmit(Error.AuthError.SignInWithGoogle(e))
                    }
                } else {
                    errorBus.error.tryEmit(Error.AuthError.SignInWithGoogle(null))
                }
                return null
            }

        }
    ) { result ->
        Timber.d("onActivityResult $result")
        result?.let { emmitUserToCache(it) }
    }
    private val coroutineScope = activity.lifecycleScope
    override val authType: AppAuthenticator.AuthMethod = AppAuthenticator.AuthMethod.Google

    override fun authenticate(context: Bundle) {
        signInResultApi.launch(Unit)
    }

    override fun logout() {
        googleSignInClient.signOut()
    }


    private fun emmitUserToCache(user: User) {
        coroutineScope.launch {
            EcoTipsApp.appComponent.getAppCache().setUser(user)
        }
    }

    private val GoogleSignInAccount.user
        get() = User(
            this.displayName?.substringBefore(" ").orEmpty(),
            this.email.orEmpty(),
            AppAuthenticator.AuthMethod.Google
        )
}