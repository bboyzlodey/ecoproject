package skarlat.dev.ecoproject.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.adapter.CourseAdapter;
import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;



public class PageFragment extends Fragment {
	Bundle savedInstanceState;
	private List<Course> courses;
	private List<Course> coursesDone;
	private DatabaseHelper db = new DatabaseHelper();

	public static final String ARG_PAGE = "ARG_PAGE";
	
	private int mPage;
	private View view;
	
	public static PageFragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		PageFragment fragment = new PageFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mPage = getArguments().getInt(ARG_PAGE);
		}
		this.savedInstanceState = savedInstanceState;
		
	}
	
	@Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                                   Bundle savedInstanceState) {
		
		final View view = inflater.inflate(R.layout.education_tab, container, false);
		this.view = view;

		TextView currentCourseLvlView  = view.findViewById(R.id.current_small_description);
		TextView currentCourseTitleView  = view.findViewById(R.id.current_course_title);
		TextView currentCourseTextView = view.findViewById(R.id.current_course_text_view);
		TextView countLeftCardView = view.findViewById(R.id.count_left_cards);
		ProgressBarView progressBarView = view.findViewById(R.id.pb_horizontal);
		CardView currentCardView = view.findViewById(R.id.current_course);

		Course currentCourse = db.getCurrentCourse();
		if (currentCourse == null){
			currentCourseTextView.setVisibility(View.GONE);
			currentCardView.setVisibility(View.GONE);
			progressBarView.setVisibility(View.GONE);
			countLeftCardView.setVisibility(View.GONE);
		}else {
			String leftCards = db.getLeftCards(currentCourse.getName());
			int progressBar = currentCourse.getProgressBar();
			currentCourseTitleView.setText(currentCourse.getTitle());
			currentCourseLvlView.setText(currentCourse.getDescription());
			countLeftCardView.setText(leftCards);
			progressBarView.setValue(progressBar);
			currentCardView.setTag(currentCourse);
		}


		initiList(); // создаем лист и заполняем его

		TextView textViewDone = view.findViewById(R.id.course_done);
		if (coursesDone == null || coursesDone.size() == 0){
			textViewDone.setVisibility(View.GONE);
		}else
			textViewDone.setVisibility(View.VISIBLE);
		/**
		 *
		 *      Заполнение RecyclerView;
		 */
		
		
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_courses);

		RecyclerView recyclerViewDone = (RecyclerView) view.findViewById(R.id.recycle_courses_done);

		CourseAdapter courseAdapter = new CourseAdapter(getContext(), courses);
		CourseAdapter courseAdapterDone = new CourseAdapter(getContext(), coursesDone);
		if(recyclerView != null) {
//			recyclerView.setNestedScrollingEnabled(false);
//			recyclerView.setFocusable(false);
//			recyclerView.setHasFixedSize(true);

			recyclerView.setAdapter(courseAdapter);
		}
		if (recyclerViewDone != null) {
			recyclerViewDone.setNestedScrollingEnabled(false);
			recyclerViewDone.setFocusable(false);
			recyclerViewDone.setAdapter(courseAdapterDone);
		}
		return view;
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
}
