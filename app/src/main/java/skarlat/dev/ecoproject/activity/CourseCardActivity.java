package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.EcoCardActivity;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.adapter.CardsViewAdapter;
import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.CardsDB;
import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class CourseCardActivity extends AppCompatActivity {
    private ProgressBarView progressBarView;
    private TextView leftCards;
    private TextView cursTitle;
    private TextView courseDesc;
    private TextView courseFullDesc;
    private RecyclerView recyclerView;
    private int progress;
    private Button startCourse;
    private String courseName;
    private DatabaseHelper db = new DatabaseHelper();
    private List<EcoCard> ecoCard;
    boolean debug;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_card);

        Bundle tagView = getIntent().getExtras();

        Course course = (Course) tagView.get("tag");
        courseName = (String) course.getName();

        db.initCards(courseName);


        cursTitle = (TextView) findViewById(R.id.curs_title);
        progressBarView = (ProgressBarView) findViewById(R.id.pb_horizontal);
        leftCards = (TextView) findViewById(R.id.left_cards);
        courseDesc = (TextView) findViewById(R.id.course_desc);
        startCourse = findViewById(R.id.start_course);


        ecoCard = db.getListOfCards();

        cursTitle.setText(course.getTitle());
        progress = db.getCursProgressBar(courseName);
        progressBarView.setValue(progress);
        leftCards.setText(db.getLeftCards(courseName));
        courseDesc.setText(course.getDescription());

        if ( progress > 0 )
            startCourse.setText("Продолжить обучение");
        else
            startCourse.setText("Начать обучение");


        /**
         *
         *      Добавляем лист объектов в recycleView;
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycle_cards);
        CardsViewAdapter adapter = new CardsViewAdapter(CourseCardActivity.this, ecoCard);
        recyclerView.setAdapter(adapter);

        db.upDateCurrentCourse(courseName);
        db.upDateCourse(courseName, progress, 1);

    }

    public void increase(View view) {
//        progress = progress + 10;
//        postProgress(progress);
    }

    public void onBackBtn(View view){
        onBackPressed();
    }

    private void postProgress(int progress) {
//        progressBarView.setValue(progress);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openCard(View view) {
        EcoCard currentCard = (EcoCard) view.getTag();

        Intent intent = new Intent(this, EcoCardActivity.class);

        if (currentCard.getStatus() == EcoCard.Status.WATCHED)
            startActivity(intent);

        for (int i = 0; i < ecoCard.size(); i++) {
            if ( ecoCard.get(i).getName() == currentCard.getName() && (i + 1) < ecoCard.size()){
                db.upDateCard(ecoCard.get(i + 1).getName(), 1);
                break;
            }
        }

        db.upDateCard(currentCard.getName(), 2);
        upDateCurrentCourse();

        intent.putExtra(currentCard.getClass().getSimpleName(),currentCard);

        startActivity(intent);
    }

    private void upDateCurrentCourse(){
        double res =  100.00 / (double) ecoCard.size();
        res = Math.ceil(res);

        progress += res;
        if (progress >= 100)
            db.upDateCourse(courseName, progress, 2);
        else
            db.upDateCourse(courseName, progress, 1);
    }

    private void upDateCurrentCourse(int position){
        double res =  100.00 / (double) ecoCard.size();
        res = Math.ceil(res);

        progress += res;
        if (progress >= 100)
            db.upDateCourse(courseName, progress, 2);
        else
            db.upDateCourse(courseName, progress, 1);

        String current = ecoCard.get(position).getName();
        db.upDateCard(current,2);

        String next;
        if (position != ecoCard.size() - 1) {
            next = ecoCard.get(position + 1).getName();
            db.upDateCard(next,1);
        }
    }



    public void startBtn(View view){
        for (int i = 0; i < ecoCard.size(); i++) {
            Enum status = ecoCard.get(i).getStatus();
            if (status == EcoCard.Status.OPENED){
                Intent intent = new Intent(this, EcoCardActivity.class);

                intent.putExtra(ecoCard.get(i).getClass().getSimpleName(),ecoCard.get(i));

                upDateCurrentCourse(i);

                startActivity(intent);
            }
        }
    }
}
