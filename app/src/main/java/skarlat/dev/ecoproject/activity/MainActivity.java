package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import skarlat.dev.ecoproject.R;

public class MainActivity extends AppCompatActivity {

	private TextView count;
	static final private int PROGRESS_BAR = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}

	public void next(View view) {
			Intent intent = new Intent(this, AuthActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PROGRESS_BAR){
			if (resultCode == RESULT_OK){

			}
		}
	}
}
