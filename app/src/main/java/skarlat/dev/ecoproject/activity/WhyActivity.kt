package skarlat.dev.ecoproject.activity

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_why.*
import skarlat.dev.ecoproject.Const.ARTICLE_JSON_PATH
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.adapter.ArticleAdapter
import skarlat.dev.ecoproject.dpToPx
import work.upstarts.editorjskit.models.EJBlock
import work.upstarts.editorjskit.models.HeadingLevel
import work.upstarts.editorjskit.ui.theme.EJStyle
import work.upstarts.gsonparser.EJDeserializer

data class EJResponse(val blocks: List<EJBlock>)
class WhyActivity : AppCompatActivity() {

    private val rvAdapter: ArticleAdapter by lazy {
        val contentStartEndMargin = resources.getDimensionPixelSize(R.dimen.standard_margin)
        ArticleAdapter(EJStyle.builderWithDefaults(this.applicationContext)
                .paragraphTextColor(ContextCompat.getColor(this, R.color.paragraphTextColor))
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h1)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h2)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h3)
                .headingMargin(contentStartEndMargin, resources.getDimensionPixelSize(R.dimen.h1_top_margin), contentStartEndMargin, resources.getDimensionPixelSize(R.dimen.h1_bottom_margin), HeadingLevel.h1)
                .headingMargin(contentStartEndMargin, resources.getDimensionPixelSize(R.dimen.h2_top_margin), contentStartEndMargin, 0, HeadingLevel.h2)
                .headingMargin(contentStartEndMargin, resources.getDimensionPixelSize(R.dimen.h2_top_margin), contentStartEndMargin, 0, HeadingLevel.h3)
                .paragraphMargin(contentStartEndMargin, resources.getDimensionPixelSize(R.dimen.article_paragraph_top_margin), contentStartEndMargin, 0)
                .build())
    }
    val blocksType = object : TypeToken<MutableList<EJBlock>>() {}.type
    val gson = GsonBuilder()
            .registerTypeAdapter(blocksType, EJDeserializer())
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_why)

        backButton.setOnClickListener { onBackPressed() }
        val JsonDataPath = intent.extras?.getString(ARTICLE_JSON_PATH)
        val dummyData = JsonDataPath?.let { readFileFromAssets(it, assets) }
        recyclerView.adapter = rvAdapter
        val ejResponse = gson.fromJson(dummyData, EJResponse::class.java)
        rvAdapter.items = ejResponse.blocks
    }

    fun readFileFromAssets(fname: String, assetsManager: AssetManager): String {
        return assetsManager.open(fname).readBytes().toString(Charsets.UTF_8)
    }


}
