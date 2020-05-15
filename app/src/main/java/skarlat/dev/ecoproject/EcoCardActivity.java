package skarlat.dev.ecoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.jackandphantom.customtogglebutton.CustomToggle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skarlat.dev.ecoproject.includes.DatabaseHelper;
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
//		ecoSoviets.add(new EcoSoviet("Выключать воду",
//				"Выключать воду, когда чистишь зубы, намыливаюсь в душе или мою посуду.", true));
//		ecoSoviets.add(new EcoSoviet("Максимально загружать машинку",
//				"Загружать посудомоечную и стиральную машины больше чем на 80%.", false));
//		ecoSoviets.add(new EcoSoviet("Принять душ, а не ванну",
//				"В душе мы в среднем расходуем 100 литров, а при приеме ванны - около 200 литров.", false));
//		ecoSoviets.add(new EcoSoviet("Бдить и чинить!",
//				"Отслеживать состояние кранов, труб, бака унитаза и не допускать протечек.", false));
//		ecoSoviets.add(new EcoSoviet("Купить и успокоиться",
//				"Поставить на кран водо-сберегающую насадку-аэратор. " +
//						"Она стоит от 100 рублей в магазинах сантехники.", false));
	}

	private void setFulDesc(){
		LinearLayout layout = findViewById(R.id.full_description);
//		String[] full_desc = getResources().getStringArray(R.array.full_desc);
		Resources res = getResources();
		String[] text = res.getStringArray(R.array.full_desc);
		TypedArray icons = res.obtainTypedArray(R.array.img);



//		String icons.getText(0);
		TextView textView = new TextView(this);
		textView.setText(text[0]);

		Drawable drawable = icons.getDrawable(0);
		ImageView imageView = new ImageView(this);
		imageView.setBackground(drawable);
		layout.addView(textView);
		layout.addView(imageView);

//		String text = ecoCard.getFullDescription();
//		ArrayList<String> img = new ArrayList<>();
//		ArrayList<String> list = new ArrayList<>();
//		String examp = "<img name=";
//		String name;
//
//		Pattern pattern = Pattern.compile(examp + ".+?>");
//		Matcher matcher = pattern.matcher(text);
//
//		while (matcher.find()) {
//			name = text.substring(matcher.start(), matcher.end());
//			list.add(text.split(name,2)[0]);
//			img.add(name);
//		}
//		fullDescView.setText(list.get(0));
//		List list = (List) getResources().getDrawable(R.drawable.values);

//		String str = (String) list.get(0);
//		fullDescView.;
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

}
