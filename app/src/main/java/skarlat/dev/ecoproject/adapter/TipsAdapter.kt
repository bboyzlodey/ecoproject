package skarlat.dev.ecoproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet

class TipsAdapter (context: Context? , onAdviceListener: OnAdviceListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cards = arrayListOf<Any>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private  val onAdviceListener = onAdviceListener

    companion object {
        const val ADVICE_VIEW = 1
        const val CARD_SOVIET = 2
    }

    fun submitList(card: List<Any>) {
        cards.addAll(card)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ADVICE_VIEW -> AdviceViewHolder(inflater.inflate(R.layout.advice_view, parent, false),onAdviceListener)
            else -> CardSovietViewHolder(inflater.inflate(R.layout.card_soviet, parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ADVICE_VIEW -> onBindAdvice(holder as AdviceViewHolder, position)
            else -> onBindCardSoviet(holder as CardSovietViewHolder, position)
        }

    }

    private fun onBindAdvice(holder: AdviceViewHolder, position: Int) {

        val advice = cards[position] as EcoCard
        holder.card_title.text = advice.title
        holder.card_category.text = advice.description
        holder.description.text = advice.fullDescription

    }

    private fun onBindCardSoviet(holder: CardSovietViewHolder, position: Int) {
        val cardDescription = cards[position] as EcoSoviet
        holder.header_card.text = cardDescription.title
        holder.descr_card.text = cardDescription.description

    }

    override fun getItemCount(): Int {
        return cards.size
    }

    private inner class AdviceViewHolder internal constructor(itemView: View, onAdviceListener: OnAdviceListener) : RecyclerView.ViewHolder(itemView) , View.OnClickListener  {
        val description: TextView = itemView.findViewById(R.id.card_why_shortDescription)
        val card_title: TextView = itemView.findViewById(R.id.card_title)
        val card_category: TextView = itemView.findViewById(R.id.card_category)
         val clickListener = itemView.setOnClickListener(this )
         val onAdviceListener = onAdviceListener

        override fun onClick(v: View?) {
            onAdviceListener.onClickAdvice(adapterPosition)

        }

    }

    private inner class CardSovietViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header_card: TextView = itemView.findViewById(R.id.header_card)
        val descr_card: TextView = itemView.findViewById(R.id.descr_card)

    }


    override fun getItemViewType(position: Int): Int {
        return when {
            cards[position] is EcoSoviet -> CARD_SOVIET
            else -> ADVICE_VIEW
        }
    }
         interface OnAdviceListener {
        fun onClickAdvice(position: Int)
    }


}