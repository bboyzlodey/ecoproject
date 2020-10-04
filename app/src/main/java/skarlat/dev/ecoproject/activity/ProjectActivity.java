package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import skarlat.dev.ecoproject.R;


/*
**  Project Activity
*
*
 */
public class ProjectActivity extends AppCompatActivity {
	private int progress = 0;
	private ProgressBar pbHorizontal;
	private TextView tvProgressHorizontal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_project);

//		pbHorizontal = (ProgressBar) findViewById(R.id.pb_horizontal);
//		tvProgressHorizontal = (TextView) findViewById(R.id.tv_progress_horizontal);

//		progress =  getIntent().getExtras().getInt("progressBar");

//		postProgress(10);
	}

	public void onClick(View v) {
		progress = progress + 10;
		postProgress(progress);
	}

	private void postProgress(int progress) {
		String strProgress = String.valueOf(progress) + " %";
		pbHorizontal.setProgress(progress);

		if (progress == 0) {
			pbHorizontal.setSecondaryProgress(0);
		} else {
			pbHorizontal.setSecondaryProgress(progress + 5);
		}
		tvProgressHorizontal.setText(strProgress);
	}

	@Override
	public void onBackPressed() {
		saveStatusBar();
	}
	
	public void openCard(View view) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
	public void saveStatusBar(){
		Intent intent = new Intent();
		intent.putExtra("progressBar", progress);
		setResult(RESULT_OK, intent);
		finish();
	}
}
