package skarlat.dev.ecoproject.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.databinding.ActivitySignInBinding
import timber.log.Timber

class AuthActivity : AppCompatActivity() {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .build()

    private val googleSignInClient by lazy { GoogleSignIn.getClient(this, gso) }
    private val signInResultApi = registerForActivityResult(
            // TODO move this calls to authenticator and add logout
            object : ActivityResultContract<Unit, User?>() {
                override fun createIntent(context: Context, input: Unit?): Intent {
                    return googleSignInClient.signInIntent
                }

                override fun parseResult(resultCode: Int, intent: Intent?): User? {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                    return task.result?.user
                }

            },
            object : ActivityResultCallback<User?> {
                override fun onActivityResult(result: User?) {
                    Timber.d("onActivityResult $result")
                    result?.let { emmitUserToCache(it) }
                }
            }
    )

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.nextScreen.collectLatest {
                findNavController(R.id.nav_host).navigate(it)
                finishAfterTransition()
            }
        }
    }

    fun signInWithGoogle() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            emmitUserToCache(account.user)
        } else {
            launchSignInWithGoogleActivity()
        }
    }

    private fun launchSignInWithGoogleActivity() {
        signInResultApi.launch(Unit)
    }

    private fun emmitUserToCache(user: User) {
        lifecycleScope.launch {
            EcoTipsApp.appComponent.getAppCache().setUser(user)
        }
    }

    private val GoogleSignInAccount.user
        get() = User(this.displayName?.substringBefore(" ").orEmpty(), this.email.orEmpty())

}