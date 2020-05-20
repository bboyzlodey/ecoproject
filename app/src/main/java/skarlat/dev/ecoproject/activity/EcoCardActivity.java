package skarlat.dev.ecoproject.activity;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.EcoSoviet;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.adapter.DataAdapter;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;

//import skarlat.dev.ecoproject.activity.adapters;
//import androidx.appcompat.widget.Toolbar;

public class EcoCardActivity extends AppCompatActivity {
	public static String TAG = "EcoCardActivity";
	private RecyclerView recyclerView;
	private ScrollView scrollView;
	private EcoCard ecoCard;
	private DatabaseHelper db;
	private View fullDescView;
	List<EcoSoviet> ecoSoviets = new ArrayList<>();
	Drawable whyBackground;
	Drawable howBackground;
	ColorStateList whyColor;
	ColorStateList howColor;
	Toolbar myToolbar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eco_cards);

		db = new DatabaseHelper();

		Bundle bundle = getIntent().getExtras();

		ecoCard = (EcoCard) bundle.get(EcoCard.class.getSimpleName());

		fullDescView = findViewById(R.id.full_description);
		final TextView how = (TextView) findViewById(R.id.how_text);
		final TextView why = (TextView) findViewById(R.id.why_text);
		
		whyColor = why.getTextColors();
		howColor = how.getTextColors();
		
		whyBackground = why.getBackground();
		howBackground = how.getBackground();
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleBtn);
		toggleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable tmp = whyBackground;
				whyBackground = howBackground;
				howBackground = tmp;
				
				ColorStateList tmpColor = whyColor;
				whyColor = howColor;
				howColor = tmpColor;
				
				how.setTextColor(howColor);
				how.setBackground(howBackground);
				why.setTextColor(whyColor);
				why.setBackground(whyBackground);
				changeVisibility();
			}
		});
		
		initiList();
		setFulDesc();
		myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		initActionBar();
		scrollView = (ScrollView) findViewById(R.id.scroll_description);
		/**
		 *
		 *      Добавляем лист объектов в recycleView;
		 */
		recyclerView = (RecyclerView) findViewById(R.id.recycle_soviets);
		DataAdapter dataAdapter = new DataAdapter(EcoCardActivity.this, ecoSoviets);
		recyclerView.setAdapter(dataAdapter);

	}
	
	private void initActionBar(){
		setActionBar(myToolbar);
		myToolbar.setTitle(ecoCard.getTitle());
		myToolbar.setSubtitle(ecoCard.getDescription());
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(getDrawable(R.drawable.original));
		actionBar.setTitle(ecoCard.getTitle());
		actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back2);
	}
	
	
	 private void log(String message){
		Log.d(TAG, message);
	} // Для дебагинга и логинга
	
	protected void initiList(){
		ecoSoviets = db.getAllByCardName(ecoCard.getName());
	}

	private void setFulDesc(){
		LinearLayout layout = findViewById(R.id.full_description);
		Resources res = getResources();
		String[] text = res.getStringArray(R.array.full_desc);
		TypedArray icons = res.obtainTypedArray(R.array.img);


		TextView textView = new TextView(this);
		textView.setText(text[0]);

		Drawable drawable = icons.getDrawable(0);
		ImageView imageView = new ImageView(this);
		imageView.setBackground(drawable);
		layout.addView(textView);
		layout.addView(imageView);

	}
	
	protected  void changeVisibility(){
		if (recyclerView.getVisibility() == View.VISIBLE){
			recyclerView.setVisibility(View.GONE);
			scrollView.setVisibility(View.VISIBLE);
		}else {
			scrollView.setVisibility(View.GONE);
			recyclerView.setVisibility(View.VISIBLE);
		}
	}

	public void onBackBtn(View view){
		onBackPressed();
	}


	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}

	public void likeBtn(View view){
		EcoSoviet tip = (EcoSoviet) view.getTag();
		if (tip.getStatus() == EcoSoviet.Status.UNLIKED)
			tip.upDate(EcoSoviet.Status.LIKED);
		else
			tip.upDate(EcoSoviet.Status.UNLIKED);
	}

}
