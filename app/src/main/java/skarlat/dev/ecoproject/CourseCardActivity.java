package skarlat.dev.ecoproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CourseCardActivity extends AppCompatActivity {
    private int progress = 0;
    private ProgressBar pbHorizontal;
    private TextView tvProgressHorizontal;
    Course course;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_card);

        course = new Course("firstStep", "new Course", true);

        pbHorizontal = (ProgressBar) findViewById(R.id.pb_horizontal);
        tvProgressHorizontal = (TextView) findViewById(R.id.tv_progress_horizontal);

        progress =  course.getProgressBar();

        postProgress(progress);
    }

    public void increase(View view) {
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

    public void saveStatusBar(){
        course.upDate(progress);
        finish();
    }
}
