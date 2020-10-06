package skarlat.dev.ecoproject.activity

import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.adapter.NewEJAdapter
import skarlat.dev.ecoproject.databinding.ActivityWhyBinding
import work.upstarts.editorjskit.models.EJBlock
import work.upstarts.editorjskit.models.HeadingLevel
import work.upstarts.editorjskit.ui.EditorJsAdapter
import work.upstarts.editorjskit.ui.theme.EJStyle
import work.upstarts.gsonparser.EJDeserializer

const val DATA_JSON_PATH = "why.json"

data class EJResponse(val blocks: List<EJBlock>)
class WhyActivity : AppCompatActivity() {
    lateinit var binding : ActivityWhyBinding

    private val rvAdapter: NewEJAdapter by lazy {
        NewEJAdapter(EJStyle.builderWithDefaults(this.applicationContext)
                .paragraphTextSize(16)
                .paragraphTextColor(ContextCompat.getColor(this, R.color.paragraphTextColor))
                .paragraphMargin(100 , 0, 0, 20)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h1)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h2)
                .headingColorDetailed(ContextCompat.getColor(this, R.color.paragraphTextColor), HeadingLevel.h3)
                .headingTextSizes(floatArrayOf(20f))
                .headingTypefaceDetailed(Typeface.createFromAsset(assets, "fonts/bellota_bold.ttf"), HeadingLevel.h1)
                .paragraphTypeface(Typeface.createFromAsset(assets, "fonts/bellota_bold.ttf"))
                .imageMargin(0 , 0, 0, 20)
                .build())
    }
    val blocksType = object : TypeToken<MutableList<EJBlock>>() {}.type
    val gson = GsonBuilder()
            .registerTypeAdapter(blocksType, EJDeserializer())
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyData = readFileFromAssets(DATA_JSON_PATH, assets)
        binding.recyclerView.adapter = rvAdapter
        val ejResponse = gson.fromJson(dummyData, EJResponse::class.java)
        rvAdapter.items = ejResponse.blocks

    }

    fun readFileFromAssets(fname: String, assetsManager: AssetManager) =
            assetsManager.open(fname).readBytes().toString(Charsets.UTF_8)


}
