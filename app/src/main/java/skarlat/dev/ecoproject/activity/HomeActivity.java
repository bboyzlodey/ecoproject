package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.adapter.CourseAdapter;
import skarlat.dev.ecoproject.includes.database.DataBaseCopy;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;


public class HomeActivity extends AppCompatActivity {
	private DatabaseHelper db;
	private List<Course> courses;
	private List<Course> coursesDone;
	private TextView nameAccount;
	private RelativeLayout backgroundView;

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
		db = new DatabaseHelper();
		db.updateDatabase();


		backgroundView = findViewById(R.id.relative);
		TextView currentCourseLvlView  = findViewById(R.id.current_small_description);
		TextView currentCourseTitleView  = findViewById(R.id.current_course_title);
		TextView currentCourseTextView = findViewById(R.id.current_course_text_view);
		CardView currentCardView = findViewById(R.id.current_course);
		nameAccount = findViewById(R.id.account_welcome);

		setAccountName();

		Course currentCourse = db.getCurrentCourse();
		if (currentCourse == null){
			currentCourseTextView.setVisibility(View.GONE);
			currentCardView.setVisibility(View.GONE);
		}else {
			backgroundView.setBackgroundResource(R.drawable.lvl_1);
			currentCourseTitleView.setText(currentCourse.getTitle());
			currentCourseLvlView.setText(currentCourse.getDescription());
			currentCardView.setTag(currentCourse);
		}


		initiList();

		TextView textViewDone = findViewById(R.id.course_done);
		if (coursesDone == null || coursesDone.size() == 0){
			textViewDone.setVisibility(View.GONE);
		}else
			textViewDone.setVisibility(View.VISIBLE);

		RecyclerView recyclerView = findViewById(R.id.recycle_courses);

		RecyclerView recyclerViewDone = findViewById(R.id.recycle_courses_done);

		CourseAdapter courseAdapter = new CourseAdapter(this, courses);
		CourseAdapter courseAdapterDone = new CourseAdapter(this, coursesDone);
		if(recyclerView != null) {
			recyclerView.setFocusable(false);
			recyclerView.setAdapter(courseAdapter);
		}
		if (recyclerViewDone != null) {
			recyclerViewDone.setFocusable(false);
			recyclerViewDone.setAdapter(courseAdapterDone);
		}
	}

	protected void initiList(){
		courses = new ArrayList<>();
		coursesDone = new ArrayList<>();
		List<Course> list = db.getAllCourses();
		for (int i = 0; i < list.size(); i++) {
			Course course = list.get(i);
			if (course.getStatus() == Course.Status.CLOSED){
				courses.add(course);
			}else if (course.getStatus() == Course.Status.FINISHED)
				coursesDone.add(course);
		}
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			open.putExtra("background", backgroundView.getSourceLayoutResId());
		}
		startActivity(open);
	}

	private void setAccountName(){
		FirebaseAuth mAuth = FirebaseAuth.getInstance();
		FirebaseUser user = mAuth.getCurrentUser();
		String userName = user.getDisplayName().split(" ")[0];
		nameAccount.setText("Привет, " + userName + "!");
	}

	public void openProfile(View view){
		Intent intent = new Intent(this, AuthActivity.class);

		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
