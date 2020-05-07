package skarlat.dev.ecoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.jackandphantom.customtogglebutton.CustomToggle;

import java.util.ArrayList;
import java.util.List;
//import androidx.appcompat.widget.Toolbar;

public class EcoCardActivity extends AppCompatActivity {
	public static String TAG = "EcoCardActivity";
	private RecyclerView recyclerView;
	List<EcoSoviet> ecoSoviets = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eco_cards);
		TextView textView = new TextView(this);
		textView.setText("Hello");
//		;
		TextDrawable textDrawable = new TextDrawable(getApplicationContext());
		textDrawable.setText("Как?");
		textDrawable.setTextAlign(Layout.Alignment.ALIGN_CENTER);
		
		CustomToggle customToggle = (CustomToggle) findViewById(R.id.custom);
//		Drawable drawable = new BitmapDrawable(textView.getDrawingCache());
		customToggle.addFirstIcon(textDrawable);
		customToggle.addSecondIcon(textDrawable);
		
		initiList();
		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//		setSupportActionBar(myToolbar);
		setActionBar(myToolbar);
		myToolbar.setTitle("Экономим водные ресурсы");
		myToolbar.setSubtitle("Ресурсосбережение");
//		myToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlack));
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(getDrawable(R.drawable.original));
		actionBar.setTitle("Экономим водные ресурсы");
		
		
		
		recyclerView = (RecyclerView) findViewById(R.id.recycle_soviets);
		DataAdapter dataAdapter = new DataAdapter(EcoCardActivity.this, ecoSoviets);
		recyclerView.setAdapter(dataAdapter);
		
		
//		if (actionBar != null) {
//			log("Action bar is: " + actionBar.toString());
//			actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
//			ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 400);
//			actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM);
//		}
//		else{
//			Toolbar toolbar = new Toolbar(EcoCardActivity.this);
//			toolbar.setTitle("hello");
//			toolbar.setSubtitle("subtitle");
//
////			this.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//			toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//			toolbar.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,400 ));
//			setSupportActionBar(null);
//			setSupportActionBar(toolbar);
//			toolbar.setVisibility(View.VISIBLE);
//			log("Created toolbar");
//			log("Toolbar is visible: " + toolbar.isDrawingCacheEnabled());
////			this.addContentView(toolbar, new ViewGroup.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,400 ));
////			log("Toolbar is visible: " + toolbar.isDrawingCacheEnabled());
//		}
	}
	 private void log(String message){
		Log.d(TAG, message);
	}
	protected void initiList(){
		ecoSoviets.add(new EcoSoviet("Выключать воду",
				"Выключать воду, когда чистишь зубы, намыливаюсь в душе или мою посуду.", true));
		ecoSoviets.add(new EcoSoviet("Максимально загружать машинку",
				"Загружать посудомоечную и стиральную машины больше чем на 80%.", false));
		ecoSoviets.add(new EcoSoviet("Принять душ, а не ванну",
				"В душе мы в среднем расходуем 100 литров, а при приеме ванны - около 200 литров.", false));
		ecoSoviets.add(new EcoSoviet("Бдить и чинить!",
				"Отслеживать состояние кранов, труб, бака унитаза и не допускать протечек.", false));
		ecoSoviets.add(new EcoSoviet("Купить и успокоиться",
				"Поставить на кран водо-сберегающую насадку-аэратор. " +
						"Она стоит от 100 рублей в магазинах сантехники.", false));
	}
}
