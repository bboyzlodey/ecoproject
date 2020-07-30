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
					startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
				}
				else{
					Toast.makeText(RegistrationActivity.this, "Registration failed.",
							Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
	
	protected void createNewUser(){
		mAuth = FirebaseAuth.getInstance();
		mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwdEditText.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()){
					Log.d(TAG, "createUserWithEmail:success");
				}
				else{
					Log.d(TAG, "createUserWithEmail:failed");
				}
			}
		});
		FirebaseUser user = mAuth.getCurrentUser();
		if (user != null){
			user.updateProfile(new UserProfileChangeRequest.Builder()
					                   .setDisplayName(nameEditText.getText().toString()).build());
		}
	}
	protected boolean filledAllFields(){
		return (nameEditText.getText().toString() != null && emailEditText.getText().toString() != null
		&& passwdEditText.getText().toString() != null);
	}
}
