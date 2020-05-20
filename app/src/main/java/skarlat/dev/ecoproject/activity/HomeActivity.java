package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.adapter.SampleFragmentPagerAdapter;
import skarlat.dev.ecoproject.includes.database.DataBaseCopy;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;

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

		/**
		 * Копирование базы данных из папки assets
		 */
		DataBaseCopy dataBaseCopy = new DataBaseCopy(this);
		try {
			dataBaseCopy.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tabLayout = (TabLayout) findViewById(R.id.home_tab);
        tabView(); // Иницилизация TabView
	    setIconsInTab();

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
	
	/**
	 *
	 * @param v - это объект самой кнопки
	 *          из нее мы можем вытянуть свойство tag и по этому ключу запустить другое активити
	 *          (открыть курс)
	 */
	public void openCourse(View v){
		Intent open = new Intent(this, CourseCardActivity.class);
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
