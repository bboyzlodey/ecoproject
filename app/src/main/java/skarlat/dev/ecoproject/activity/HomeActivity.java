package skarlat.dev.ecoproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCardActivity;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.SampleFragmentPagerAdapter;
import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.DatabaseHelper;
import skarlat.dev.ecoproject.includes.СoursesDB;

public class HomeActivity extends AppCompatActivity {
	private List<Course> courses;
	private TabLayout tabLayout;
	private DatabaseHelper db;
//	private TextView currentCourseTitleView,currentCourseDescView, countLeftCardView;
//	private ProgressBarView progressBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout = (TabLayout) findViewById(R.id.home_tab);
//        currentCourseDescView = findViewById(R.id.current_small_description);
//        currentCourseTitleView = findViewById(R.id.curs_title);
//        countLeftCardView = findViewById(R.id.count_left_cards);
//        progressBarView = findViewById(R.id.pb_horizontal);
        tabView(); // Иницилизация TabView
		initDataBase(); // Иницилизация базы данных
	    setIconsInTab();

//	    Course currentCourse = db.getCurrentCourse();
////		ArrayMap<String, Object> courses = db.getAllCourses();
//	    if (currentCourse == null){
//	    	TextView currentCourseTextView = findViewById(R.id.current_course_text_view);
//			CardView currentCardView = findViewById(R.id.current_course);
//			currentCourseTextView.setVisibility(View.GONE);
//			currentCardView.setVisibility(View.GONE);
//		}else {
//			String leftCards = db.getLeftCards(currentCourse.getName());
//			int progressBar = db.getCursProgressBar(currentCourse.getName());
//			currentCourseTitleView.setText(currentCourse.getTitle());
//			currentCourseDescView.setText(currentCourse.getDescription());
//			countLeftCardView.setText(leftCards);
//			progressBarView.setValue(progressBar);
//		}

    }
	
	/**
	 *      Эта функция устанавливает иконки для таб бара
	 */
	protected void setIconsInTab(){
	    int[] imageResId = {
	    		R.drawable.event,
			    R.drawable.timeline,
			    R.drawable.person};
	
	    for (int i = 0; i < imageResId.length; i++) {
		    tabLayout.getTabAt(i).setIcon(imageResId[i]);
		    tabLayout.getTabAt(i).getIcon().setTint(getResources().getColor(R.color.colorGray));
		    tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.colorWhite));
		    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			    @Override
			    public void onTabSelected(TabLayout.Tab tab) {
					tab.getIcon().setTint(getResources().getColor(R.color.colorWhite));
			    }

			    @Override
			    public void onTabUnselected(TabLayout.Tab tab) {
				    tab.getIcon().setTint(getResources().getColor(R.color.colorGray));
			    }

			    @Override
			    public void onTabReselected(TabLayout.Tab tab) {

			    }
		    });
	    }
    }
    
    protected void tabView(){
	    // Получаем ViewPager и устанавливаем в него адаптер
	    ViewPager viewPager = findViewById(R.id.viewpager);
	    viewPager.setAdapter(
			    new SampleFragmentPagerAdapter(getSupportFragmentManager(), HomeActivity.this));
	
	    // Передаём ViewPager в TabLayout
	    tabLayout.setupWithViewPager(viewPager);
	    
	    //  Анимация для сглаживания программного скрола в PageFragment
	    viewPager.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void initDataBase(){
		try {

			InputStream inputStream = getResources().getAssets().open("soviets.csv"); //считывание списка советов из файла

			db = new DatabaseHelper(inputStream); // Инициализация Базы данных

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * @param v - это объект самой кнопки
	 *          из нее мы можем вытянуть свойство tag и по этому ключу запустить другое активити
	 *          (открыть курс)
	 */
	public void openCourse(View v){
		Intent open;  // интент, который сделает переключение в Активити с карточками
//		open = new Intent(this, EcoCardActivity.class);
		open = new Intent(this, CourseCardActivity.class);
		Course tag = (Course) v.getTag();

		open.putExtra("tag", tag);
		/**
		 * Допустим, у нас есть объект Education (глобальная переменная) с полями:
		 *      String title;
		 *      String description;
		 *      Map<String, CursCard> curses;
		 *
		 *   То мы сделаем так, что бы отобразить нужный курс:
		 *      static String KEY_GET = "education";
		 *      String keyForPut = v.getContentDescription().toString();
		 *      Education education = savedInstanceState.get(KEY_GET);
		 *      CursCard willOpen = education.curses.get(keyForPut);
		 *      savedInstanceState.put("curs", willOpen);
		 *      Intent intent = new Intent(this, CursCard.class);
		 *      startActivity(intent, savedInstanceState);
		 */
		startActivity(open);
	}
}
