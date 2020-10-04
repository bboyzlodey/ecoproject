package skarlat.dev.ecoproject.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import work.upstarts.editorjskit.EJKit
import work.upstarts.editorjskit.models.EJBlock
import work.upstarts.editorjskit.ui.EditorJsAdapter
import work.upstarts.editorjskit.ui.adapterdelegates.*
import work.upstarts.editorjskit.ui.theme.EJStyle

class NewEJAdapter(style: EJStyle? = EJKit.ejStyle,
                   diffCallback: DiffUtil.ItemCallback<Any>? = null) : AsyncListDifferDelegationAdapter<Any>(diffCallback
        ?: DIFF_CALLBACK) {
    init {
        initDelegates(style)
    }

    fun initDelegates(style: EJStyle?) {
        delegatesManager
                .addDelegate(HeaderAdapterDelegate(style))
                .addDelegate(ParagraphAdapterDelegate(style))
                .addDelegate(DividerAdapterDelegate(style))
                .addDelegate(ImageAdapterDelegate(style))
                .addDelegate(ListAdapterDelegate(style))
                .addDelegate(TableAdapterDelegate(style))
                .addDelegate(RawHtmlAdapterDelegate(style))
                .addDelegate(ArticleQuoteElement(style))

    }

}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem === newItem) return true
        return if (newItem is EJBlock && oldItem is EJBlock) {
            oldItem.data == newItem.data
        } else false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
}