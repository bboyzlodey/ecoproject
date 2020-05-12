package skarlat.dev.ecoproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import skarlat.dev.ecoproject.adapter.CardsViewAdapter;
import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class CourseCardActivity extends AppCompatActivity {
    private ProgressBarView progressBarView;
    private TextView leftCards;
    private TextView cursTitle;
    private TextView courseDesc;
    private RecyclerView recyclerView;
    private int progress;
    private String courseName;
    private DatabaseHelper db = new DatabaseHelper();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_card);

        cursTitle = (TextView) findViewById(R.id.curs_title);
        progressBarView = (ProgressBarView) findViewById(R.id.pb_horizontal);
        leftCards = (TextView) findViewById(R.id.left_cards);
        courseDesc = (TextView) findViewById(R.id.course_desc);



        Bundle curentCourse = getIntent().getExtras();
        
//        Course course = null;
//
//
//        List<Object> ecoCard = db.getListOfCards();
//
//        assert course != null;
//        cursTitle.setText(course.getTitle());
//        progress = db.getCursProgressBar(courseName);
//        progressBarView.setValue(progress);
//        leftCards.setText(db.getLeftCards(courseName));
//        courseDesc.setText(course.getDescription());
    
        List<Course> ecoCard =  new ArrayList<>();
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 1));
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 0));
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 1));
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 1));
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 1));
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 1));
        ecoCard.add(new Course("hehe", "fdsf", "ppp", "poi", 2));
    
    
        /**
         *
         *      Добавляем лист объектов в recycleView;
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycle_cards);
        CardsViewAdapter adapter = new CardsViewAdapter(CourseCardActivity.this, ecoCard);
        recyclerView.setAdapter(adapter);

//        db.upDateCourse(courseName, db.getCursProgressBar(courseName), 2);

    }

    public void increase(View view) {
        progress = progress + 10;
        postProgress(progress);
    }

    public void onBackBtn(View view){
        onBackPressed();
    }

    private void postProgress(int progress) {
        progressBarView.setValue(progress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
    public void openCard(View view) {
        Intent intent = new Intent(this, EcoCardActivity.class);
        startActivity(intent);
    }

}
