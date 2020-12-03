package skarlat.dev.ecoproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.show
import skarlat.dev.ecoproject.visible


class CardsViewAdapter(private val context: Context?, private val cardClickListener: (View) -> Void) : RecyclerView.Adapter<CardsViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cards = arrayListOf<EcoCard>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recycler_item_card, parent, false)
        return ViewHolder(view)
    }

    fun submitList(card: List<EcoCard>) {
        cards.clear()
        cards.addAll(card)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ecoCard = cards[position]
        val status = ecoCard.status as EcoCard.Status
        val finished = status == EcoCard.Status.WATCHED
        holder.description.show(finished)
        holder.title.show(finished)
        holder.statusIcon.show(!finished)
        holder.numberIcon.visible(finished)
        if (finished) {
            holder.title.text = ecoCard.title
            holder.description.text = ecoCard.description
            // TODO Add numberIcons
        } else {
            holder.cardView.setCardBackgroundColor(when (status) {
                EcoCard.Status.CLOSED -> context?.let { ContextCompat.getColor(it, R.color.colorGray) }!!
                else -> context?.let { ContextCompat.getColor(it, R.color.cardOpenedBG) }!!
            })
            holder.statusIcon.setImageResource(if (status == EcoCard.Status.OPENED) R.drawable.ic_play else R.drawable.ic_lock)
        }
        holder.cardView.tag = ecoCard

        Log.d("CardsViewAdapter", "onBindViewHolder position ${position + 1}")
        val numberDrawableId = context?.applicationContext?.resources?.getIdentifier("ic_card_number_${position + 1}", "drawable", "skarlat.dev.ecoproject")
                ?: R.drawable.ic_card_number
        val numberDrawable = context?.getDrawable(numberDrawableId)
        numberDrawable?.let { holder.numberIcon.setImageDrawable(it) }
        if (finished || status == EcoCard.Status.OPENED) {
            holder.cardView.setOnClickListener {
                cardClickListener(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
        val numberIcon: ImageView = view.findViewById(R.id.number)
        val statusIcon: ImageView = view.findViewById(R.id.statusIcon)
        val cardView: CardView = view.findViewById(R.id.cardView)

    }
}