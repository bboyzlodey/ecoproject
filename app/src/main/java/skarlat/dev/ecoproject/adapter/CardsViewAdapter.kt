package skarlat.dev.ecoproject.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.*
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.includes.dataclass.EcoCard


class CardsViewAdapter(private val cardClickListener: (View) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cards = arrayListOf<Any>()

    companion object {
        const val COURSE_DESCRIPTION_VIEW_TYPE = R.layout.item_course_info
        const val COURSE_CARD_VIEW_TYPE = R.layout.recycler_item_card
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflate(viewType, parent)
        return when (viewType) {
            COURSE_DESCRIPTION_VIEW_TYPE -> CourseDescriptionViewHolder(view)
            else -> CourseCardViewHolder(view)
        }
    }

    fun submitList(card: List<Any>) {
        cards.clear()
        cards.addAll(card)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            COURSE_DESCRIPTION_VIEW_TYPE -> onBindCourseDescription(holder as CourseDescriptionViewHolder, position)
            else -> onBindCourseCard((holder as CourseCardViewHolder), position)
        }
    }

    private fun onBindCourseCard(holderCourseCard: CourseCardViewHolder, position: Int) {
        val ecoCard = cards[position] as EcoCard
        val status = ecoCard.status as EcoCard.Status
        val finished = status == EcoCard.Status.WATCHED
        holderCourseCard.description.show(finished)
        holderCourseCard.title.show(finished)
        holderCourseCard.statusIcon.show(!finished)
        holderCourseCard.numberIcon.visible(finished)
        holderCourseCard.state = status
        if (finished) {
            holderCourseCard.title.text = ecoCard.title
            holderCourseCard.description.text = ecoCard.description
        } else {
            val cardBackgroundId = when (status) {
                EcoCard.Status.CLOSED -> R.color.colorGray
                else -> R.color.cardOpenedBG
            }
            holderCourseCard.cardView.setCardBackgroundColor(holderCourseCard.getColor(cardBackgroundId))
            holderCourseCard.statusIcon.setImageResource(if (status == EcoCard.Status.OPENED) R.drawable.ic_play else R.drawable.ic_lock)
        }
        holderCourseCard.cardView.tag = ecoCard

        val numberDrawableId = holderCourseCard.cardView?.context?.resources?.getIdentifier("ic_card_number_${position}", "drawable", "skarlat.dev.ecoproject")
                ?: R.drawable.ic_card_number
        val numberDrawable = holderCourseCard.cardView?.context?.getDrawable(numberDrawableId)
        numberDrawable?.let { holderCourseCard.numberIcon.setImageDrawable(it) }
        if (finished || status == EcoCard.Status.OPENED) {
            holderCourseCard.cardView.setOnClickListener {
                cardClickListener(it)
            }
        }
    }

    private fun onBindCourseDescription(holder: CourseDescriptionViewHolder, position: Int) {
        val courseDescription = cards[position] as Course
        holder.courseTitle.text = courseDescription.title
        holder.courseDescription.text = courseDescription.fullDescription
        holder.leftCards.text = holder.itemView.context.getQuantityStringZero(R.plurals.left_card_plurals, R.string.no_left_cards, courseDescription.leftCards)
        holder.progressOfCourse.max = courseDescription.countCards
        holder.progressOfCourse.progress = courseDescription.countCards - courseDescription.leftCards
    }

    override fun getItemCount(): Int {
        return cards.size
    }


    inner class CourseCardViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
        val numberIcon: ImageView = view.findViewById(R.id.number)
        val statusIcon: ImageView = view.findViewById(R.id.statusIcon)
        val cardView: CardView = view.findViewById(R.id.cardView)
        var state: EcoCard.Status? = null
    }

    inner class CourseDescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseTitle: TextView = view.findViewById(R.id.curs_title)
        val progressOfCourse: ProgressBar = view.findViewById(R.id.progressOfCourse)
        val leftCards: TextView = view.findViewById(R.id.left_cards)
        val courseDescription: TextView = view.findViewById(R.id.course_desc)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            cards[position] is EcoCard -> COURSE_CARD_VIEW_TYPE
            else -> COURSE_DESCRIPTION_VIEW_TYPE
        }
    }
}