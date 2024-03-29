package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.core.SettingsManager;
import skarlat.dev.ecoproject.includes.dataclass.EcoCard;
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.databinding.ActivityEcoCardsBinding;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;


public class CardActivity extends AppCompatActivity {
    public static String TAG = "EcoCardActivity";
    private EcoCard ecoCard;
    private DatabaseHelper db;
    List<EcoSoviet> ecoSoviets = new ArrayList<>();
    LinearLayout linearLayout;
    private ActivityEcoCardsBinding binding;
    private SettingsManager settingsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEcoCardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingsManager = new SettingsManager(getSharedPreferences(Const.ECO_TIPS_PREFERENCES, MODE_PRIVATE));
        db = new DatabaseHelper();
        db.calculateUserProgress(settingsManager);
        Bundle bundle = getIntent().getExtras();
        ecoCard = (EcoCard) bundle.get(EcoCard.class.getSimpleName());
        try {
            binding.cardPicture.setImageDrawable(ecoCard.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        binding.whyItPossible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whyIntent = new Intent(CardActivity.this, WhyActivity.class);
                whyIntent.putExtra(Const.ARTICLE_JSON_PATH, ecoCard.courseNameID + "/" + ecoCard.cardNameID + "/" + ecoCard.cardNameID + ".json");
                startActivity(whyIntent);
            }
        });
        TextView textView = findViewById(R.id.card_title);
        textView.setText(capitalize(ecoCard.title));
        textView = findViewById(R.id.card_category);
        textView.setText(ecoCard.description);
        binding.cardWhyShortDescription.setText(ecoCard.fullDescription);
        binding.cardWhyShortDescription.setSelected(true);
        initiList();
        linearLayout = findViewById(R.id.card_linear_layout);
        Iterator iterator = ecoSoviets.iterator();
        if (!ecoSoviets.isEmpty()) {
            do {
                inflateLayout((EcoSoviet) iterator.next());
            } while (iterator.hasNext());
        }

    }

    private String capitalize(String cap) {
        return cap.substring(0, 1).concat(cap.substring(1).toLowerCase());
    }

    private void inflateLayout(EcoSoviet ecoSoviet) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.card_soviet, null, false);
        ((TextView) view.findViewById(R.id.header_card)).setText(ecoSoviet.title);
        ((TextView) view.findViewById(R.id.descr_card)).setText(ecoSoviet.description);
        linearLayout.addView(view);
    }

    private void log(String message) {
        Log.i(TAG, message);
    } // Для дебагинга и логинга

    protected void initiList() {
        ecoSoviets = db.getAllCards(ecoCard.getName());
    }


    public void onBackBtn(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        setResult(Const.CARD_ACTIVITY_OK);
        super.onBackPressed();
    }
}
