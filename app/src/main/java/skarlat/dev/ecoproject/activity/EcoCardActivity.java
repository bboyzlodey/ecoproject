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
		initiList();
	}
		
	
	 private void log(String message){
		Log.d(TAG, message);
	} // Для дебагинга и логинга
	
	protected void initiList(){
		ecoSoviets = db.getAllByCardName(ecoCard.getName());
	}
	
	
	public void onBackBtn(View view){
		onBackPressed();
	}


	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}
}
