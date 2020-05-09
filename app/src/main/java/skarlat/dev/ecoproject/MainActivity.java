package skarlat.dev.ecoproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

	private TextView count;
	static final private int PROGRESS_BAR = 0;
	public DatabaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DatabaseHelper();

	}

	public void next(View view) {
		Intent intent = new Intent(this, CourseCardActivity.class);

		Course currentCourse = (Course) db.getListOfCourses().get("firstStep");

		intent.putExtra(Course.class.getSimpleName(), currentCourse);
		intent.putExtra(DatabaseHelper.class.getSimpleName(), db);

		startActivityForResult(intent, PROGRESS_BAR);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PROGRESS_BAR){
			if (resultCode == RESULT_OK){
//				int countBar = data.getIntExtra("progressBar", 0);
//				count.setText(String.valueOf(countBar));

//				db.upDateCourse("firstStep", countBar);
			}
		}
	}
}
