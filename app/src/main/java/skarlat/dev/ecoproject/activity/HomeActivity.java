package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.adapter.SampleFragmentPagerAdapter;
import skarlat.dev.ecoproject.databinding.ActivityHomeBinding;
import skarlat.dev.ecoproject.includes.database.DataBaseCopy;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;
import skarlat.dev.ecoproject.section.CourseSection;


public class HomeActivity extends AppCompatActivity {
	private TabLayout tabLayout;
	private DatabaseHelper db;
	private FirebaseAuth auth;
	private ActivityHomeBinding binding;
	private String TAG = "HomeActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//	    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	    if (User.currentUser != null){
	        Log.d(TAG, "The current user name is: " + User.currentUser.name);
	        binding.helloUser.setText("Привет, " + User.currentUser.name);
	    }
		/**
		 * Копирование базы данных из папки assets
		 */
		DataBaseCopy dataBaseCopy = new DataBaseCopy(this);
		try {
			dataBaseCopy.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		db = new DatabaseHelper();
		db.updateDatabase();
	    SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
	    List<Course> listCourses = new ArrayList<Course>();
	    Course course = new Course();
	    course.description = "Для начинаюзих";
	    course.title = "Первые шаги!";
	    Course courseq = new Course();
	    courseq.description = "Для начинаюзих";
	    courseq.title = "Первые шаги!";
	    listCourses.add(course);
	    Section section = new CourseSection(listCourses, "Текущий курс");
	    sectionedRecyclerViewAdapter.addSection(section);
	    sectionedRecyclerViewAdapter.addSection(new CourseSection(listCourses, "Пройденные курсы"));
	    binding.recycleCourses.setAdapter(sectionedRecyclerViewAdapter);
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
		startActivity(open);
	}
}
