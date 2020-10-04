package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.databinding.ActivityHomeBinding;
import skarlat.dev.ecoproject.fragment.UserFragment;
import skarlat.dev.ecoproject.App;
import skarlat.dev.ecoproject.includes.database.DataBaseCopy;
import skarlat.dev.ecoproject.section.CourseSection;


public class HomeActivity extends FragmentActivity {
	private ActivityHomeBinding binding;
	private String TAG = "HomeActivity";
	private FragmentManager fragmentManager;
	private Fragment fragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
				openUserFragment();
				Log.d(TAG, "profileImageClicked");
	        }
        });
        if (User.currentUser == null) {
        	User.currentUser = new User("Roza");
        }
	    if (User.currentUser != null){
	        Log.d(TAG, "The current user name is: " + User.currentUser.name);
	        binding.helloUser.setText("Привет, " + User.currentUser.name + "!");
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
	    SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
	    sectionedRecyclerViewAdapter.addSection(new CourseSection(App.getDatabase().courseDao().getAllIsActive(), getResources().getString(R.string.current_courses)));
	    sectionedRecyclerViewAdapter.addSection(new CourseSection(App.getDatabase().courseDao().getAllNonActive(), getResources().getString(R.string.aviable_courses)));
	    sectionedRecyclerViewAdapter.addSection(new CourseSection(App.getDatabase().courseDao().getAllFinished(), getResources().getString(R.string.finished_courses)));
	    binding.recycleCourses.setAdapter(sectionedRecyclerViewAdapter);
    }
    
	public void openCourse(View v){
		Intent open = new Intent(this, CourseCardActivity.class);
		CharSequence charSequence = v.getContentDescription();
		if (charSequence != null){
			open.putExtra("OPEN_COURSE", charSequence.toString());
			startActivity(open);
		}
		else {
			TextView textView = v.findViewById(R.id.current_title);
			CharSequence str = textView.getContentDescription();
			if (str != null)
			{
				open.putExtra("OPEN_COURSE", str.toString());
				startActivity(open);
			}
		}
		
	}
	
	private void openUserFragment(){
		fragment = UserFragment.newInstance(0);
		fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().add(R.id.home_layout, fragment).commit();
		binding.linearLayout.setVisibility(View.GONE);
	}
	
	@Override
	public void onBackPressed() {
    	if (fragmentManager.getFragments().size() > 0){
    		binding.linearLayout.setVisibility(View.VISIBLE);
			fragmentManager.beginTransaction().detach(fragment).commit();
    	}
    	else
			super.onBackPressed();
	}
}
