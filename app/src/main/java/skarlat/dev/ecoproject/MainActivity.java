package skarlat.dev.ecoproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


//
public class MainActivity extends AppCompatActivity {

	private TextView count;
	static final private int PROGRESS_BAR = 0;
	public DatabaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		count = findViewById(R.id.countBtn);

		db = new DatabaseHelper();

		int val = db.getCursProgressBar("firstStep");

		count.setText(String.valueOf(val));

	}

	public void next(View view) {
		Intent intent = new Intent(this, ProjectActivity.class);

		intent.putExtra("progressBar", 0);

		startActivityForResult(intent, PROGRESS_BAR); // или так можно
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PROGRESS_BAR){
			if (resultCode == RESULT_OK){
				int countBar = data.getIntExtra("progressBar", 0);
				count.setText(String.valueOf(countBar));

				db.upDateCurs("firstStep", countBar, null);
			}
		}
	}
}
