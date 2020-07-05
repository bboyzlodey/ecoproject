package skarlat.dev.ecoproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import skarlat.dev.ecoproject.R;

public class RegistrationActivity extends AppCompatActivity {
	private TextInputEditText nameEditText;
	private TextInputEditText emailEditText;
	private TextInputEditText passwdEditText;
	
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
				Intent intent = new Intent(this, SignInActivity.class);
				startActivity(intent);
				break;
			case R.id.submit:
				break;
		}
	}
}
