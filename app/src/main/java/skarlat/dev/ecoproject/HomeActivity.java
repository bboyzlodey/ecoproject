package skarlat.dev.ecoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
	private List<Course> courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabView(); // Иницилизация TabView
    }
    
    protected void tabView(){
	    // Получаем ViewPager и устанавливаем в него адаптер
	    ViewPager viewPager = findViewById(R.id.viewpager);
	    viewPager.setAdapter(
			    new SampleFragmentPagerAdapter(getSupportFragmentManager(), HomeActivity.this));
	
	    // Передаём ViewPager в TabLayout
	    TabLayout tabLayout = findViewById(R.id.sliding_tabs);
	    tabLayout.setupWithViewPager(viewPager);
	    
    }
	
	/**
	 *
	 * @param v - это объект самой кнопки
	 *          из нее мы можем вытянуть свойство tag и по этому ключу запустить другое активити
	 *          (открыть курс)
	 */
	public void openCourse(View v){
		Intent open;  // интент, который сделает переключение в Активити с карточками
		open = new Intent(this, EcoCardActivity.class);
		
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
