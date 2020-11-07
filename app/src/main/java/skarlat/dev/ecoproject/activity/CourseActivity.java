package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import skarlat.dev.ecoproject.ConnectionDecorator;
import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.ExtendedFunctionsKt;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.databinding.ActivityCourseCardBinding;
import skarlat.dev.ecoproject.includes.dataclass.Course;
import skarlat.dev.ecoproject.includes.dataclass.EcoCard;
import skarlat.dev.ecoproject.adapter.CardsViewAdapter;
import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;

public class CourseActivity extends AppCompatActivity {
    private ProgressBarView progressBarView;
    private TextView leftCards;
    private TextView cursTitle;
    private TextView courseDesc;
    private RecyclerView recyclerView;
    private int progress;
    private Button startCourse;
    private String courseName;
    private DatabaseHelper db = new DatabaseHelper();
    private List<EcoCard> ecoCard;
    private Course currentCourse;
    private final int REQUST = 1;
    private ActivityCourseCardBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateData();
        ExtendedFunctionsKt.setImageFromAssets(binding.courseAvatar, getAssets(), currentCourse.pathBarImage());

//        if ( progress > 0  && progress < 100)
//            startCourse.setText("Продолжить обучение");
//        else if ( progress >= 100){
//            startCourse.setVisibility(View.GONE);
//        }
//        else
//            startCourse.setText("Начать обучение");

    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // TODO("LOGIC FOR CLOSE CARD")
        if (requestCode == Const.CARD_OPENED) {
            if (resultCode == Const.CARD_ACTIVITY_OK) {
                updateData();
            }
        }
//        progress = currentCourse.getProgressBar();
//        progressBarView.setValue(progress);
//        leftCards.setText(db.getLeftCards(courseName));
//
//        CardsViewAdapter adapter = new CardsViewAdapter(CourseCardActivity.this, ecoCard);
//        recyclerView.setAdapter(adapter);
//
//        if ( progress > 0  && progress < 100)
//            startCourse.setText("Продолжить обучение");
//        else if ( progress >= 100){
//            startCourse.setVisibility(View.GONE);
//        }
//        else
//            startCourse.setText("Начать обучение");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackBtn(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * View карточки обновляет базу данных при переходе
     *
     * @param view
     */
    public Void openCard(View view) {
        EcoCard currentCard = (EcoCard) view.getTag();

        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra(currentCard.getClass().getSimpleName(), currentCard);
        if (currentCard.getStatus() == EcoCard.Status.WATCHED) {
            startActivityForResult(intent, REQUST);
        } else {

            for (int i = 0; i < ecoCard.size(); i++) {
                if (ecoCard.get(i).getName() == currentCard.getName() && (i + 1) < ecoCard.size()) {
                    ecoCard.get(i + 1).upDate(EcoCard.Status.OPENED);
                    db.updateFirebaseProgress("Cards", ecoCard.get(i + 1).getName(), "status", 1);
                    break;
                }
            }

            currentCard.upDate(EcoCard.Status.WATCHED);
            db.updateFirebaseProgress("Cards", currentCard.getName(), "status", 2);
            upDateCurrentCourse();

            startActivityForResult(intent, REQUST);
        }
        return null;
    }

    /**
     * Progress бар для базы данных
     */
    private void upDateCurrentCourse() {

        double res = 100.00 / (double) ecoCard.size();
        res = Math.ceil(res);

        progress += res;
        if (progress >= 100) {
            currentCourse.upDate(progress, Course.Status.FINISHED);
        } else {
            currentCourse.upDate(progress, Course.Status.CURRENT);
        }
        db.updateFirebaseProgress("Courses", currentCourse.getName(), "progress", progress);
    }

    /**
     * Кнопка продолжить обучение
     * Обновляет базу данных при нажатии
     *
     * @param view
     */
    public void startBtn(View view) {
        for (int i = 0; i < ecoCard.size(); i++) {
            EcoCard card = ecoCard.get(i);
            Enum status = card.getStatus();
            if (status == EcoCard.Status.OPENED) {
                Intent intent = new Intent(this, CardActivity.class);

                intent.putExtra(card.getClass().getSimpleName(), card);

                upDateCurrentCourse();

                if (i != ecoCard.size() - 1) {
                    ecoCard.get(i + 1).upDate(EcoCard.Status.OPENED);
                    db.updateFirebaseProgress("Cards", ecoCard.get(i + 1).getName(), "status", 1);
                }
                card.upDate(EcoCard.Status.WATCHED);
                db.updateFirebaseProgress("Cards", card.getName(), "status", 2);
                startActivityForResult(intent, REQUST);
                break;
            }
        }
    }

    public void updateData() {
        Bundle tagView = getIntent().getExtras();

        currentCourse = db.getCourseByName(tagView.get("OPEN_COURSE").toString());
        courseName = currentCourse.getName();
        cursTitle = binding.cursTitle;
        progressBarView = binding.pbHorizontal;
        leftCards = binding.leftCards;
        courseDesc = binding.courseDesc;
        ecoCard = db.getAllCardsByCourseNameID(courseName);
        cursTitle.setText(currentCourse.getTitle());
        progress = currentCourse.getProgressBar();
        progress = db.getCourseByName(courseName).getProgressBar();
        progressBarView.setValue(progress);
        leftCards.setText(db.getLeftCards(courseName));
        courseDesc.setText(currentCourse.getFullDescription());
        CardsViewAdapter adapter = new CardsViewAdapter(CourseActivity.this, ecoCard, this::openCard);
        binding.recycleCards.setAdapter(adapter);
        binding.recycleCards.addItemDecoration(new ConnectionDecorator(0, 0, R.color.colorAccent, getResources().getDrawable(R.drawable.card_divider)));
        db.upDateIsCurrentCourse(courseName);
    }
}
