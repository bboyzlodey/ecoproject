package skarlat.dev.ecoproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class CourseCardActivity extends AppCompatActivity {
    private ProgressBarView progressBarView;
    private TextView leftCards;
    private TextView cursTitle;
    private TextView courseDesc;
    private int progress;
    private DatabaseHelper db = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_card);

        cursTitle = (TextView) findViewById(R.id.curs_title);
        progressBarView = (ProgressBarView) findViewById(R.id.pb_horizontal);
        leftCards = (TextView) findViewById(R.id.left_cards);
        courseDesc = (TextView) findViewById(R.id.course_desc);

        Bundle curentCourse = getIntent().getExtras();
        Course course = null;

        if (curentCourse != null){
            course = (Course) curentCourse.getSerializable(Course.class.getSimpleName());
            db = (DatabaseHelper) curentCourse.getSerializable(DatabaseHelper.class.getSimpleName());

            assert db != null;
            assert course != null;
            db.initCards(course.getName());


        }else{
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }


        ArrayList<Object> ecoCard = db.getListOfCards();

        int countCards = ecoCard.size();

        TextView textView = findViewById(R.id.count_course);

        textView.setText(String.valueOf(countCards));

        assert course != null;
        cursTitle.setText(course.getTitle());
        progressBarView.setValue(db.getCursProgressBar(course.getName()));
        leftCards.setText("Осталось" + countCards +" карточек");
        courseDesc.setText(course.getDescription());

    }

    public void increase(View view) {
        progress = progress + 10;
        postProgress(progress);
    }

    public void onBackBtn(View view){

    }

    private void postProgress(int progress) {
        progressBarView.setValue(progress);
    }

    @Override
    public void onBackPressed() {

    }

}
