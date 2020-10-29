package skarlat.dev.ecoproject.activity

import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_why.*
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.adapter.NewEJAdapter
import work.upstarts.editorjskit.models.EJBlock
import work.upstarts.editorjskit.models.HeadingLevel
import work.upstarts.editorjskit.ui.EditorJsAdapter
import work.upstarts.editorjskit.ui.theme.EJStyle
import work.upstarts.gsonparser.EJDeserializer

const val DATA_JSON_PATH = "why.json"

data class EJResponse(val blocks: List<EJBlock>)
class WhyActivity : AppCompatActivity() {

    private val rvAdapter: NewEJAdapter by lazy {
        NewEJAdapter(EJStyle.builderWithDefaults(this.applicationContext)
//                .paragraphTextSize(16)
//                .paragraphTextColor(ContextCompat.getColor(this, R.color.paragraphTextColor))
//                .paragraphMargin( , 0, 0, 20)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h1)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h2)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h3)
                .headingTextSizes(floatArrayOf(20f))
//                .headingTypefaceDetailed(Typeface.createFromAsset(assets, "fonts/bellota_bold.ttf"), HeadingLevel.h1)
//                .headingTypefaceDetailed(Typeface.createFromAsset(assets, "fonts/bellota_bold.ttf"), HeadingLevel.h2)
//                .paragraphTypeface(Typeface.createFromAsset(assets, "fonts/bellota_bold.ttf"))
                .imageMargin(0 , 0, 0, 20)
                .build())
    }
    val blocksType = object : TypeToken<MutableList<EJBlock>>() {}.type
    val gson = GsonBuilder()
            .registerTypeAdapter(blocksType, EJDeserializer())
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_why)

        val dummyData = readFileFromAssets(DATA_JSON_PATH, assets)
        recyclerView.adapter = rvAdapter
        val ejResponse = gson.fromJson(dummyData, EJResponse::class.java)
        rvAdapter.items = ejResponse.blocks
    }

    fun readFileFromAssets(fname: String, assetsManager: AssetManager) =
            assetsManager.open(fname).readBytes().toString(Charsets.UTF_8)


}
