package skarlat.dev.ecoproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import skarlat.dev.ecoproject.activity.HomeActivity;
import skarlat.dev.ecoproject.includes.CSVFile;
import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

	private TextView count;
	static final private int PROGRESS_BAR = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}

	public void next(View view) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		
		/**
		 *  Временно закоментировал. Вылетает эксепшн.
		 */
//		Course currentCourse = (Course) db.getListOfCourses().get("firstStep");
//		intent.putExtra(Course.class.getSimpleName(), currentCourse);
//		startActivityForResult(intent, PROGRESS_BAR); // или так можно
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
