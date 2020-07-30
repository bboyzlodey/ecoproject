package skarlat.dev.ecoproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import skarlat.dev.ecoproject.R;

/**
 * RegistrationActivity - активность для регистрации нового юзера через FireBase
 */
public class RegistrationActivity extends AppCompatActivity {
	final String TAG = "RegistrationActivity";
	private TextInputEditText nameEditText;
	private TextInputEditText emailEditText;
	private TextInputEditText passwdEditText;
	private FirebaseAuth mAuth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		nameEditText = (TextInputEditText) findViewById(R.id.name);
		emailEditText = (TextInputEditText) findViewById(R.id.user_email);
		passwdEditText = (TextInputEditText) findViewById(R.id.user_passwd);
	}
	
	/**
	 * @TODO: Write code for:
	 *          1. sign_in_google
	 *          2. sign_in - writed
	 *          3. submit
	 */
	public void onClick(View v){
		int id = v.getId();
		switch (id){
			case R.id.sign_in_google:
				break;
			case R.id.sign_in:
				onBackPressed();
				break;
			case R.id.submit:
				if (filledAllFields()){
					createNewUser();
					Toast.makeText(RegistrationActivity.this, "Проверьте почту!",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
				}
				else{
					Toast.makeText(RegistrationActivity.this, "Registration failed.",
							Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
	
	protected boolean createNewUser(){
		String userEmail, userpasswd;
		final boolean[] result = new boolean[1];
		
		mAuth = FirebaseAuth.getInstance();
		userEmail = emailEditText.getText().toString();
		userpasswd = passwdEditText.getText().toString();
		mAuth.createUserWithEmailAndPassword(userEmail, userpasswd)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if ((result[0] = task.isSuccessful())){
					Log.i(TAG, "createUserWithEmail: success");
				}
				else{
					Log.e(TAG, "createUserWithEmail: failed");
				}
				
			}
		});
		if (result[0]){
			FirebaseUser user = mAuth.getCurrentUser();
			if (user != null){
				user.updateProfile(new UserProfileChangeRequest.Builder()
						                   .setDisplayName(nameEditText.getText().toString()).build());
			}
			return (user == null);
		}
		return result[0];
	}
	protected boolean filledAllFields(){
		
		return (nameEditText.getText().toString() != null && emailEditText.getText().toString() != null
		&& passwdEditText.getText().toString() != null);
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
		
		security = passwd.length() >= 6;
		security = passwd.matches(securityRegex);
		return  security;
	}
	
	protected boolean eMailCheckerCorrect(String eMail){
		boolean valid;
		final String validPattern = "\\w+@\\w+\\.[a-zA-z]{2,4}";
		
		valid = (eMail != null) && eMail.matches(validPattern);
		return valid;
	}
	protected boolean userDataValidation(String passwd, String eMail){
		return passwCheckerSecurity(passwd) && eMailCheckerCorrect(eMail);
	}
}
