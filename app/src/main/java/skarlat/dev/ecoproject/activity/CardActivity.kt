package skarlat.dev.ecoproject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.adapter.TipsAdapter
import skarlat.dev.ecoproject.databinding.ActivityEcoCardsBinding
import skarlat.dev.ecoproject.includes.database.DatabaseHelper
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet
import java.io.IOException

var TAG = "EcoCardActivity"
lateinit var ecoCard: EcoCard
lateinit var db: DatabaseHelper
lateinit var soviets: List<EcoSoviet>
lateinit var card: List<EcoCard>
lateinit var linearLayoutManager: LinearLayoutManager
lateinit var binding: ActivityEcoCardsBinding
var adapter: TipsAdapter = TipsAdapter()

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEcoCardsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        db = DatabaseHelper()
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recycleEcoTips.layoutManager = linearLayoutManager
        binding.recycleEcoTips.adapter = adapter
        val bundle = intent.extras
        ecoCard = (bundle?.get(EcoCard::class.java.simpleName) as EcoCard)

        try {
            binding.cardPicture.setImageDrawable(ecoCard.image)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        initiList()
        setupRecyclerAdapter()

    }

    // Для дебагинга и логинга
    private fun log(message: String) {
        Log.d(TAG, message)
    }


    private fun initiList() {
        adapter.clearList()
        card += ecoCard
        soviets += db.getAllByCardName(ecoCard.name)
        adapter.submitList(card)
        adapter.submitList(soviets)
    }

    fun onBackBtn(view: View?) {
        onBackPressed()
    }

    override fun onBackPressed() {
        setResult(Const.CARD_ACTIVITY_OK)
        super.onBackPressed()
    }

    private fun setupRecyclerAdapter() {
        adapter.onItemClick = { ecoCard ->
            val whyIntent = Intent(this, WhyActivity::class.java).apply {
                putExtra(Const.ARTICLE_JSON_PATH, ecoCard.courseNameID + "/" + ecoCard.cardNameID + "/" + ecoCard.cardNameID + ".json")

            }
            startActivity(whyIntent)
        }


    }

}