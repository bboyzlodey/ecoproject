package skarlat.dev.ecoproject.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.adapter.TipsAdapter;
import skarlat.dev.ecoproject.databinding.ActivityEcoCardsMineBinding;
import skarlat.dev.ecoproject.includes.dataclass.EcoCard;
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet;
import skarlat.dev.ecoproject.includes.database.DatabaseHelper;


public class CardActivityMine extends AppCompatActivity  implements TipsAdapter.OnAdviceListener {
    public static String TAG = "EcoCardActivity";

    private EcoCard ecoCard;
    private DatabaseHelper db;
    List<EcoSoviet> soviets = new ArrayList<>();
    List<EcoCard> card = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private @NonNull
    ActivityEcoCardsMineBinding binding;
    TipsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEcoCardsMineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DatabaseHelper();
        Bundle bundle = getIntent().getExtras();
        ecoCard = (EcoCard) bundle.get(EcoCard.class.getSimpleName());
        card.add(ecoCard);
        try {
            binding.cardPicture.setImageDrawable(ecoCard.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        binding.whyItPossible.setOnClickListener((View.OnClickListener) view -> {
//            Intent whyIntent = new Intent(CardActivityMine.this, WhyActivity.class);
//            whyIntent.putExtra(Const.ARTICLE_JSON_PATH, ecoCard.courseNameID + "/" + ecoCard.cardNameID + "/" + ecoCard.cardNameID + ".json");
//            startActivity(whyIntent);
//        });

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new TipsAdapter(this,this::onClickAdvice);

        initiList();
        binding.recycleEcoTips.setLayoutManager(linearLayoutManager);
        binding.recycleEcoTips.setAdapter(adapter);


    }


    private void log(String message) {
        Log.d(TAG, message);
    } // Для дебагинга и логинга

    protected void initiList() {

        soviets = db.getAllByCardName(ecoCard.getName());
        adapter.submitList(card);
        adapter.submitList(soviets);


    }


    public void onBackBtn(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        setResult(Const.CARD_ACTIVITY_OK);
        super.onBackPressed();
    }


    @Override
    public void onClickAdvice(int position) {
        Intent whyIntent = new Intent(CardActivityMine.this, WhyActivity.class);
        whyIntent.putExtra(Const.ARTICLE_JSON_PATH, ecoCard.courseNameID + "/" + ecoCard.cardNameID + "/" + ecoCard.cardNameID + ".json");
        startActivity(whyIntent);
    }
}