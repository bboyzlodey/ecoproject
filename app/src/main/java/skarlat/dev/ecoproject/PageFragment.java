package skarlat.dev.ecoproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import skarlat.dev.ecoproject.customView.ProgressBarView;
import skarlat.dev.ecoproject.includes.DatabaseHelper;

import static skarlat.dev.ecoproject.Course.Status.CLOSED;

public class PageFragment extends Fragment {
	Bundle savedInstanceState;
	private List<Course> courses;
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
		
		View view = inflater.inflate(R.layout.education_tab, container, false);
		this.view = view;

		TextView currentCourseLvlView  = view.findViewById(R.id.current_small_description);
		TextView currentCourseTitleView  = view.findViewById(R.id.current_course_title);
		TextView currentCourseTextView = view.findViewById(R.id.current_course_text_view);
		TextView countLeftCardView = view.findViewById(R.id.count_left_cards);
		ProgressBarView progressBarView = view.findViewById(R.id.pb_horizontal);
		CardView currentCardView = view.findViewById(R.id.current_course);

		Course currentCourse = db.getCurrentCourse();
//		ArrayMap<String, Object> courses = db.getAllCourses();
		if (currentCourse == null){
			currentCourseTextView.setVisibility(View.GONE);
			currentCardView.setVisibility(View.GONE);
			progressBarView.setVisibility(View.GONE);
			countLeftCardView.setVisibility(View.GONE);
		}else {
			String leftCards = db.getLeftCards(currentCourse.getName());
			int progressBar = db.getCursProgressBar(currentCourse.getName());
			currentCourseTitleView.setText(currentCourse.getTitle());
			currentCourseLvlView.setText(currentCourse.getLvl());
			countLeftCardView.setText(leftCards);
			progressBarView.setValue(progressBar);
			currentCardView.setTag(currentCourse);
		}


		initiList(); // создаем лист и заполняем его
		/**
		 *
		 *      Заполнение RecyclerView;
		 */
		
		
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_courses);
		CourseAdapter courseAdapter = new CourseAdapter(getContext(), courses);
		if(recyclerView != null)
			recyclerView.setAdapter(courseAdapter);
		/**
		 *      Проблема в том, что запускается фрагмент, а там скролл не в начале
		 *      Для этого нужен новый поток, что бы проскроллить в начало.
		 *      Но он скролит грубо.
		 *      Пусть будет пока что.
		 */
		final ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollEducation);
		scrollView.post(new Runnable() {
			@Override
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_UP);
			}
		});
		return view;
	}
	
	
	protected void initiList(){
		courses = new ArrayList<>();
		ArrayMap<String, Object> list = db.getAllCourses();
		for (int i = 0; i < list.size(); i++) {
			String key = list.keyAt(i);
			Course course = (Course) list.get(key);
			if (course.getStatus() == CLOSED){
				courses.add(course);
			}
		}
	}
	
}
