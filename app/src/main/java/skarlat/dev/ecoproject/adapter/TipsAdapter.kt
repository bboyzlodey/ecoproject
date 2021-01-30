package skarlat.dev.ecoproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.customView.AdviceView
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet

class TipsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cards = mutableListOf<Any>()

    var onItemClick: ((EcoCard) -> Unit)? = null

    companion object {
        const val ADVICE_VIEW = 1
        const val CARD_SOVIET = 2
    }

    fun submitList(card: List<Any>) {
        cards.addAll(card)
        notifyDataSetChanged()
    }

     fun clearList () {
      cards.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ADVICE_VIEW -> AdviceViewHolder(layoutInflater.inflate(R.layout.item_advice, parent, false))
            else -> CardSovietViewHolder(layoutInflater.inflate(R.layout.card_soviet, parent, false))
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
        holder.cardTitle.text = advice.title
        holder.cardCategory.text = advice.description
        holder.description.text = "Test text Test text !!!!!!"

    }

    private fun onBindCardSoviet(holder: CardSovietViewHolder, position: Int) {
        val cardDescription = cards[position] as EcoSoviet
        holder.headerCard.text = cardDescription.title
        holder.descrCard.text = cardDescription.description

    }

    override fun getItemCount(): Int {
        return cards.size
    }

    private inner class AdviceViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val description: TextView = itemView.findViewById(R.id.card_why_shortDescription)
        val cardTitle: TextView = itemView.findViewById(R.id.card_title)
        val cardCategory: TextView = itemView.findViewById(R.id.card_category)
        val adviceView: AdviceView = itemView.findViewById(R.id.whyItPossible)

        init {
            adviceView.setOnClickListener {
                onItemClick?.invoke(cards[adapterPosition] as EcoCard)
            }
        }


    }

    private inner class CardSovietViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerCard: TextView = itemView.findViewById(R.id.header_card)
        val descrCard: TextView = itemView.findViewById(R.id.descr_card)

    }


    override fun getItemViewType(position: Int): Int {
        return when {
            cards[position] is EcoSoviet -> CARD_SOVIET
            else -> ADVICE_VIEW
        }
    }


}