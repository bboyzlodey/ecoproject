package skarlat.dev.ecoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {
	Bundle savedInstanceState;
	
	public static final String ARG_PAGE = "ARG_PAGE";
	
	private int mPage;
	
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
//		TextView textView = (TextView) view;
//		textView.setText("Fragment #" + mPage);
		return view;
	}
	
}
