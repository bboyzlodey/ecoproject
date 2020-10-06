package skarlat.dev.ecoproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import skarlat.dev.ecoproject.R;

public class RemindActivity extends AppCompatActivity {
	private Button remindButton;
	private TextInputEditText email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind);
		email = findViewById(R.id.user_email);
		remindButton = findViewById(R.id.remind);
	}
	public void onClick(View v){
		int pressed = v.getId();
		if (pressed == R.id.remind){
			Snackbar.make(v, "На почту отправлена иснтрукция по восстановлению пароля :-)",
					Snackbar.LENGTH_LONG)
					.show();
			onBackPressed();
		}
	}
	
}
