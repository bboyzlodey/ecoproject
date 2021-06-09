package skarlat.dev.ecoproject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.databinding.ActivitySignInBinding
import timber.log.Timber

class AuthActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private val TAG = "SignInActivity"
    private val RC_SIGN_IN = 10
    private val KEY_USENAME = "USERNAME"
    private var mGoogleApiClient: GoogleApiClient? = null
    private var signInClient: GoogleSignInClient? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    var binding: ActivitySignInBinding? = null

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        mFirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        signInClient = GoogleSignIn.getClient(this, gso)
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.nextScreen.collectLatest {
                if (it != null) {
                    findNavController(R.id.nav_host).navigate(it)
                    finishAfterTransition()
                }
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("onActivityResult")
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Const.RC_SIGN_IN_GOOGLE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!
            if (result.isSuccess) {
                Timber.e("Result is Success")
                // Google Sign-In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // Google Sign-In failed
                Toast.makeText(this, "Авторизация не удалась!", Toast.LENGTH_SHORT).show()
                Timber.e("Google Sign-In failed." + result.status.statusMessage)
            }
        }
    }

    private val RC_SIGN_IN_GOOGLE = 1
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Timber.d("firebaseAuthWithGooogle:" + acct!!.id)
        // TODO Re-implement it
        startActivityForResult(signInClient!!.signInIntent, RC_SIGN_IN_GOOGLE)

        /*AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "name: " + acct.getDisplayName() + "\n".concat(acct.getEmail()));
                            startActivity(new Intent(SignInActivity.this, AuthActivity.class));
                            finish();
                        }
                    }
                });*/
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        // TODO
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:$connectionResult")
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }
}