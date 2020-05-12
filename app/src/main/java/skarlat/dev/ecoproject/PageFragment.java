package skarlat.dev.ecoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {
	Bundle savedInstanceState;
	private List<Course> courses;
	
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
//		courses.add(new Course("","Продвинутый",
//				"Сложнее", true));
//		courses.add(new Course("Профи",
//				"Очень сложно", false));
//		courses.add(new Course("Гипер сложно",
//				"ну попробуй", false));
//		courses.add(new Course("Ты не справишься!",
//				"Спорим?", false));
//		courses.add(new Course("Успокойся парень",
//				"Почитать полезное", false));
	}
	
}
