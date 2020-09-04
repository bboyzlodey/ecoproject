package skarlat.dev.ecoproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.databinding.ActivityRegistrationBinding;

/**
 * RegistrationActivity - активность для регистрации нового юзера через FireBase
 */
public class RegistrationActivity extends AppCompatActivity implements
		GoogleApiClient.OnConnectionFailedListener{
	final String TAG = "RegistrationActivity";
	private TextInputEditText nameEditText;
	private TextInputEditText emailEditText;
	private TextInputEditText passwdEditText;
	private FirebaseAuth mAuth;
	private ActivityRegistrationBinding binding;
	private GoogleApiClient mGoogleApiClient;
	private FirebaseAuth mFirebaseAuth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		nameEditText = binding.userEmail;
		emailEditText = binding.userName;
		passwdEditText = binding.userPasswd;
	}
	
	public void onClick(View v){
		int id = v.getId();
		switch (id){
			case R.id.sign_in_google:
				signInGoogle();
				break;
			case R.id.sign_in:
				onBackPressed();
				break;
			case R.id.submit:
				createNewUser();
				break;
		}
	}
	
	
	
	protected boolean createNewUser(){
		String userEmail, userPasswd, userName;
		final boolean[] result = new boolean[1];
		
		userEmail = emailEditText.getText().toString();
		userPasswd = passwdEditText.getText().toString();
		userName = nameEditText.getText().toString();
		result[0] = validateUserData(userEmail, userPasswd, userName);
		if (result[0]){
			mAuth = FirebaseAuth.getInstance();
			mAuth.createUserWithEmailAndPassword(userEmail, userPasswd)
					.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if ((result[0] = task.isSuccessful())){
								Toast.makeText(getApplicationContext(), "Проверьте почту!",
										Toast.LENGTH_SHORT).show();
								Log.i(TAG, "createUserWithEmail: success");
								if (setUserNameInFireBase(userName)){
									Log.i(TAG, "setUserNameInFireBase: true");
								}
								else {
									Toast.makeText(getApplicationContext(), "Не получилось установить имя", Toast.LENGTH_SHORT);
									Log.e(TAG, "Не получилось установить имя");
								}
							}
							else{
								Toast.makeText(RegistrationActivity.this, "Registration failed.",
										Toast.LENGTH_SHORT).show();
								Log.e(TAG, "createUserWithEmail: failed");
							}
						}
					});
		}
		return result[0];
	}
	
	private boolean setUserNameInFireBase(String userName){
		FirebaseUser user = mAuth.getCurrentUser();
		if (user == null)
			return false;
		user.updateProfile(new UserProfileChangeRequest.Builder()
				                   .setDisplayName(userName)
				                   .build());
		return true;
	}
	
	protected boolean filledAllFields(String ...args){
		return (args[0] != null && args[1] != null && args[2] != null);
	}
	
	protected boolean validateUserData(String eMail, String password, String name){
		return filledAllFields(eMail, password, name) && userDataValidation(password, eMail);
	}
	
	/**
	 * Method for checking user password of security this password.
	 * Rules for password security:
	 *      1. Password must contains minimum 6 characters.
	 *      2. Password contains minimum:
	 *          2.1 One special character
	 *          2.2 One uppercase and lowercase character
	 *          2.3 One characters of number
	 * @param passwd - String of password for user what must be checked on security
	 * @return boolean value. True - security is OK, else non OK.
	 */
	protected boolean passwCheckerSecurity(String passwd){
		boolean security;
		String securityRegex = ".+";
		
		security = (passwd.length() >= Const.MIN_LENGTH_PASSWORD);
		security = passwd.matches(securityRegex);
		Log.d(TAG, "passwCheckerSecurity return: " + security);
		return  security;
	}
	
	protected boolean eMailCheckerCorrect(String eMail){
		boolean valid;
		final String validPattern = "\\w+@\\w+\\.[a-zA-z]{2,4}";
		
		valid = (!eMail.equals("")) && eMail.matches(validPattern);
		Log.d(TAG, "eMailCheckerCorrect return: " + valid);
		return valid;
	}
	protected boolean userDataValidation(String passwd, String eMail){
		return passwCheckerSecurity(passwd) && eMailCheckerCorrect(eMail);
	}
	
	private void signInGoogle() {
		Log.d(TAG, "signInGoogle");
		mFirebaseAuth = FirebaseAuth.getInstance();
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				                          .requestIdToken(getString(R.string.default_web_client_id))
				                          .requestEmail()
				                          .build();
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				                   .enableAutoManage(this /* FragmentActivity */,
						                   this /* OnConnectionFailedListener */)
				                   .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				                   .build();
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, Const.RC_SIGN_IN_GOOGLE);
	}
	
	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Log.d(TAG, "onConnectionFailed:" + connectionResult);
		Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
	}
	
	private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
		Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		
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
							Toast.makeText(getBaseContext(), "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Log.d(TAG, "name: " + acct.getDisplayName() + "\n".concat(acct.getEmail()));
							User.currentUser = new User(task.getResult().getUser().getDisplayName());
							startActivity(new Intent(getBaseContext(), AuthActivity.class));
							finish();
						}
					}
				});
	}
	
	@SuppressLint("ShowToast")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "onActivityResult");
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == Const.RC_SIGN_IN_GOOGLE) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				Log.e(TAG, "Result is Success");
				// Google Sign-In was successful, authenticate with Firebase
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else {
				// Google Sign-In failed
				Toast.makeText(this, "Авторизация не удалась!", Toast.LENGTH_SHORT);
				Log.e(TAG, "Google Sign-In failed.");
			}
		}
	}
	
}
