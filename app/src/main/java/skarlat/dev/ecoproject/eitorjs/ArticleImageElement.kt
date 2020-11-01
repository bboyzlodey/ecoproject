package skarlat.dev.ecoproject.eitorjs


import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.article_image.view.*
import kotlinx.android.synthetic.main.article_quote.view.*
import skarlat.dev.ecoproject.App
import skarlat.dev.ecoproject.R
import work.upstarts.editorjskit.environment.inflate
import work.upstarts.editorjskit.models.EJCustomBlock
import work.upstarts.editorjskit.models.data.EJData
import work.upstarts.editorjskit.ui.theme.EJStyle
import java.io.InputStream

class ArticleImageElement(private val theme: EJStyle? = null) : AdapterDelegate<MutableList<Any>>() {

    var items: MutableList<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.article_image)
        return ViewHolder(view)
    }

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return items[position] is EJCustomBlock && (items[position] as EJCustomBlock).type == ArticleEcoTipsBlocks.ARTICLE_IMAGE
    }

    override fun onBindViewHolder(items: MutableList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as EJCustomBlock)
    }

    fun applyHeaderTheme(view: View, headerInitializer: (EJStyle, Paint) -> Unit) {
        theme?.let {
            view.quoteText.apply {
                headerInitializer(it, paint)
            }
        }
    }

    private inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var data: EJData

        fun bind(quoteBlock: EJCustomBlock) {
            this.data = quoteBlock.data

            with(itemView) {
                if (data is ArticleImageData) {
                    caption.text = (data as ArticleImageData).caption
                    imageView.setImageDrawable(getImageDrawable((data as ArticleImageData).path))
                }
            }
        }

        private fun getImageDrawable(path: String): Drawable {
            val assetManager = App.instance.resources.assets
            var drawable: Drawable

            try {
                val steam: InputStream = assetManager.open(path)
                drawable = BitmapDrawable(steam)
//            drawable = Drawable.createFromPath(path)!!
            } catch (p: Exception) {
                drawable = App.instance.getDrawable(R.drawable.lvl_1_1)!!
            }
            return drawable
        }
    }
}