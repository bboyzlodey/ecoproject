package skarlat.dev.ecoproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class CourseCardActivity extends AppCompatActivity {
    private ProgressBarView progressBarView;
    private int progress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_card);

//        progressBarView = (ProgressBarView) findViewById(R.id.pb_horizontal);

//        Course course = new Course("firstStep", "some descrip", true);

//        DatabaseHelper databaseHelper = new DatabaseHelper();

//        progress = databaseHelper.getCursProgressBar(course.getTitle());

//        postProgress(10);

//        View progressTextView = (View) findViewById(R.id.progressTextView);
//        progressTextView.setValue(49); // устанавливаем нужное значение

    }

    public void increase(View view) {
        progress = progress + 10;
        postProgress(progress);
    }

    private void postProgress(int progress) {
        progressBarView.setValue(progress);
    }
//
//    @Override
//    public void onBackPressed() {
//
//    }

}
