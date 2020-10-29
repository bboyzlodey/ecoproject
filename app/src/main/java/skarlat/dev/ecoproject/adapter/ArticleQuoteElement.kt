package skarlat.dev.ecoproject.adapter

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.quote_element.view.*
import skarlat.dev.ecoproject.ArticleEcoTipsBlocks
import skarlat.dev.ecoproject.ArticleQuoteData
import skarlat.dev.ecoproject.R
import work.upstarts.editorjskit.environment.inflate
import work.upstarts.editorjskit.models.EJCustomBlock
import work.upstarts.editorjskit.ui.theme.EJStyle

class ArticleQuoteElement(private val theme: EJStyle? = null): AdapterDelegate<MutableList<Any>>() {

    var items: MutableList<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.quote_element)
        return ViewHolder(view)
    }

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return items[position] is EJCustomBlock && (items[position] as EJCustomBlock).type == ArticleEcoTipsBlocks.QUOTE
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
        private lateinit var quoteBlock: EJCustomBlock

        fun bind(quoteBlock: EJCustomBlock) {
            this.quoteBlock = quoteBlock

            with(itemView) {
                quoteText.text = (quoteBlock.data as ArticleQuoteData).text
            }
        }
    }
}