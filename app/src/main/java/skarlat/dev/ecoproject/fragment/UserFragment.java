package skarlat.dev.ecoproject.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.adapter.CategoryAdapter;
import skarlat.dev.ecoproject.App;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	public static UserFragment userFragment;
	public static String userPhotoUrl;
	public static String userName;
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	private List<EcoCard> cards;
	
	public UserFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment UserFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static UserFragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt("UserFragment", page);
		UserFragment fragment = new UserFragment();
		fragment.setArguments(args);
		userFragment = fragment;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}
	ImageView imageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_user, container, false);
		RecyclerView recyclerView = view.findViewById(R.id.cards_by_category);
		TextView textView = view.findViewById(R.id.user_name);
		
		imageView = view.findViewById(R.id.profile_image);
		textView.setText(User.currentUser.name);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				showUserAvatar();
			}
		};
		
		cards = App.getDatabase().cardsDao().getAll();
		
		FloatingActionButton fab = view.findViewById(R.id.pressBackFromFragment);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Objects.requireNonNull(getActivity()).onBackPressed();
			onDestroy();}
		});
		// @TODO: Заменить заполнение листа с ипользованием БД
		
		CategoryAdapter adapter = new CategoryAdapter(getContext(), cards);
		DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), RecyclerView.HORIZONTAL);
		itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_category));
		recyclerView.addItemDecoration(itemDecoration);
		recyclerView.setAdapter(adapter);
		
		return view;
	}
	
	private void showUserAvatar(){
		try {
			URL photoURL = new URL(userPhotoUrl);
			URLConnection urlConnection = photoURL.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			imageView.setImageDrawable(new BitmapDrawable(getResources(),inputStream));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
