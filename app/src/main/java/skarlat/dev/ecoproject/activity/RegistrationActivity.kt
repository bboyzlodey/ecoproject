package skarlat.dev.ecoproject.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.databinding.ActivityRegistrationBinding
import skarlat.dev.ecoproject.network.FirebaseAPI
import timber.log.Timber

/**
 * RegistrationActivity - активность для регистрации нового юзера через FireBase
 */
class RegistrationActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    val TAG = "RegistrationActivity"
    private var nameEditText: TextInputEditText? = null
    private var emailEditText: TextInputEditText? = null
    private var passwdEditText: TextInputEditText? = null
    private var mAuth: FirebaseAuth? = null
    private var binding: ActivityRegistrationBinding? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        nameEditText = binding!!.userName
        emailEditText = binding!!.userEmail
        passwdEditText = binding!!.userPasswd
        binding!!.submit.isEnabled = false
        setListenerForEditTexts()
    }

    private fun setListenerForEditTexts() {
        binding!!.userEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                enableOrDisableButton(binding!!.submit)
            }
        })
        binding!!.userPasswd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.userPasswd.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable) {
                        enableOrDisableButton(binding!!.submit)
                    }
                })
            }
        })
        binding!!.userName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                enableOrDisableButton(binding!!.submit)
            }
        })
    }

    private fun enableOrDisableButton(view: View) {
        val userEmail = binding!!.userEmail.text.toString()
        val userPassword = binding!!.userPasswd.text.toString()
        if (userEmail != "" && userPassword != "") {
            view.isEnabled = true
        } else if (binding!!.signIn.isEnabled) {
            view.isEnabled = false
        }
    }

    fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.sign_in_google -> signInGoogle()
            R.id.sign_in -> onBackPressed()
            R.id.submit -> createNewUser()
        }
    }

    private fun createNewUser(): Boolean {
        val userEmail: String
        val userPasswd: String
        val userName: String
        val result = BooleanArray(1)
        userEmail = emailEditText!!.text.toString()
        userPasswd = passwdEditText!!.text.toString()
        userName = nameEditText!!.text.toString()
        result[0] = validateUserData(userEmail, userPasswd, userName)
        if (result[0]) {
            mAuth = FirebaseAuth.getInstance()
            mAuth!!.createUserWithEmailAndPassword(userEmail, userPasswd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful.also { result[0] = it }) {
                            Toast.makeText(applicationContext, R.string.registration_successful,
                                    Toast.LENGTH_SHORT).show()
                            increaseCountOfUsers()
                            if (setUserNameInFireBase(userName)) {
                                finish()
                            } else {
                                showMessage(R.string.name_attach_error)
                            }
                        } else {
                            showMessage(R.string.registration_failed)
                        }
                    }
        }
        return result[0]
    }

    private fun increaseCountOfUsers(){
        FirebaseAPI.increaseCountOfUsers()
    }

    private fun setUserNameInFireBase(userName: String): Boolean {
        val user = mAuth!!.currentUser ?: return false
        user.updateProfile(UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build())
        return true
    }

    private fun filledAllFields(vararg args: String?): Boolean {
        return args[0] != null && args[1] != null && args[2] != null
    }

    private fun validateUserData(eMail: String, password: String, name: String?): Boolean {
        return filledAllFields(eMail, password, name) && userDataValidation(password, eMail)
    }

    /**
     * Method for checking user password of security this password.
     * Rules for password security:
     * 1. Password must contains minimum 6 characters.
     * 2. Password contains minimum:
     * 2.1 One special character
     * 2.2 One uppercase and lowercase character
     * 2.3 One characters of number
     *
     * @param passwd - String of password for user what must be checked on security
     * @return boolean value. True - security is OK, else non OK.
     */
    private fun passwordCheckerSecurity(passwd: String): Boolean {
        var security: Boolean
        security = passwd.length >= Const.MIN_LENGTH_PASSWORD
        Timber.tag(TAG).d( "passwCheckerSecurity return: $security")
        return security
    }

    private fun eMailCheckerCorrect(eMail: String): Boolean {
        val valid: Boolean
        //        final String validPattern = "\\w+@\\w+\\.[a-zA-z]{2,4}";
        //TODO Create safety valid pattern

//        valid = (!eMail.equals("")) && eMail.matches(validPattern);
        valid = eMail != ""
        Timber.tag(Const.TAG).d("eMailCheckerCorrect return: $valid $eMail")
        return valid
    }

    private fun userDataValidation(passwd: String, eMail: String): Boolean {
        return passwordCheckerSecurity(passwd) && eMailCheckerCorrect(eMail)
    }

    private fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showMessage(@StringRes stringId: Int) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show()
    }

    private fun signInGoogle() {
        Log.i(TAG, "signInGoogle")
        mFirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, Const.RC_SIGN_IN_GOOGLE)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    Log.i(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        showMessage(R.string.registration_failed)
                        finish()
                    } else {
//                        User.currentUser = User(task.result!!.user!!.displayName)
//                        startActivity(Intent(baseContext, AuthActivity::class.java))
                        finish()
                    }
                }
    }

    @SuppressLint("ShowToast")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Const.RC_SIGN_IN_GOOGLE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result!!.isSuccess) {
                // Google Sign-In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            } else {
                // Google Sign-In failed
                showMessage(R.string.errorSignInWithGoogle)
            }
        }
    }
}