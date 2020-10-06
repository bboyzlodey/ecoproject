package skarlat.dev.ecoproject.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.element_advice_link.view.*
import skarlat.dev.ecoproject.EJAdditionalBlocks
import skarlat.dev.ecoproject.EJAdviceLinkData
import skarlat.dev.ecoproject.R
import work.upstarts.editorjskit.environment.inflate
import work.upstarts.editorjskit.models.EJCustomBlock
import work.upstarts.editorjskit.ui.theme.EJStyle

class AdviceLinkElement(private val theme: EJStyle? = null): AdapterDelegate<MutableList<Any>>() {

    var items: MutableList<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.element_advice_link)
        return ViewHolder(view)
    }

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return items[position] is EJCustomBlock && (items[position] as EJCustomBlock).type == EJAdditionalBlocks.ADVICE_LINK
    }

    override fun onBindViewHolder(items: MutableList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as ViewHolder).bind(items[position] as EJCustomBlock)
    }

    private class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        private lateinit var adviceLinkBlock: EJCustomBlock

        fun bind(adviceLinkBlock: EJCustomBlock) {
            this.adviceLinkBlock = adviceLinkBlock

            with(itemView) {
                descriptionText.text = (adviceLinkBlock as EJAdviceLinkData).text
                setOnClickListener {val openLinkIntent = Intent()
                    openLinkIntent.action = Intent.ACTION_VIEW
                    openLinkIntent.putExtra(Intent.EXTRA_ORIGINATING_URI, adviceLinkBlock.url)
                    context.startActivity(openLinkIntent)}
            }
        }
    }
}