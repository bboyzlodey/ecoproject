package skarlat.dev.ecoproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.GoogleAuthProvider;

import skarlat.dev.ecoproject.R;

public class SignInActivity extends AppCompatActivity implements
		GoogleApiClient.OnConnectionFailedListener{
	private final String TAG = "SignInActivity";
	private final int RC_SIGN_IN = 10;
	private GoogleApiClient mGoogleApiClient;
	// Firebase instance variables
	private FirebaseAuth mFirebaseAuth;
	private TextInputEditText emailEditText;
	private TextInputEditText passwdEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		emailEditText = (TextInputEditText) findViewById(R.id.user_email);
		passwdEditText = (TextInputEditText) findViewById(R.id.user_passwd);
		
		// Initialize FirebaseAuth
		mFirebaseAuth = FirebaseAuth.getInstance();
		// Configure Google Sign In
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				                          .requestIdToken(getString(R.string.default_web_client_id))
				                          .requestEmail()
				                          .build();
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				                   .enableAutoManage(this /* FragmentActivity */,
						                   this /* OnConnectionFailedListener */)
				                   .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				                   .build();
	}
	
	public void remindPassword(View v) {
	
	}
	public void onClick(View v) {
		int pressed = v.getId();
		switch (pressed){
			case R.id.sign_in_google:
				signInGoogle();
				break;
			case R.id.sign_in:
				/**
				 * @TODO: Логика для входа через наш профиль
				 */
				break;
			case R.id.remind_passwd:
				/**
				 * @TODO: Логика для напоминания пароля
				 */
				break;
			case R.id.register:
				/**
				 * @TODO: Логика для регистрации
				 */
				break;
		}
		signInGoogle();
	}
	private void signInGoogle() {
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				// Google Sign-In was successful, authenticate with Firebase
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else {
				// Google Sign-In failed
				Log.e(TAG, "Google Sign-In failed.");
			}
		}
	}
	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
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
							Toast.makeText(SignInActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						} else {
							startActivity(new Intent(SignInActivity.this, AuthActivity.class));
							finish();
						}
					}
				});
	}
	
	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		
		// An unresolvable error has occurred and Google APIs (including Sign-In) will not
		// be available.
		Log.d(TAG, "onConnectionFailed:" + connectionResult);
		Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
	
	}
}
