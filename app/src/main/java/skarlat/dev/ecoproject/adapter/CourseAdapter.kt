package skarlat.dev.ecoproject.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.inflate
import skarlat.dev.ecoproject.setImageFromAssets

class CourseAdapter(private val onCourseClicked: (Course) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val values = mutableListOf<Item>()

    private companion object {
        const val HEADER_VIEW_TYPE = R.layout.section_of_courses
        const val COURSE_VIEW_TYPE = R.layout.card_course
    }

    fun submitData(values: List<Item>) {
        this.values.clear()
        this.values.addAll(values)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (values[position]) {
            is Item.Header -> HEADER_VIEW_TYPE
            is Item.Card -> COURSE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflate(viewType, parent)
        return when (viewType) {
            HEADER_VIEW_TYPE -> CourseCategoryViewHolder(view)
            COURSE_VIEW_TYPE -> CourseViewHolder(view)
            else -> throw Exception("Invalid viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CourseViewHolder -> holder.bind()
            is CourseCategoryViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class CourseCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headerOfCourseSection: TextView = itemView.findViewById(R.id.section_title_text) as TextView

        fun bind() {
            val item = (values[absoluteAdapterPosition] as? Item.Header
                    ?: throw Exception("Invalid item from pos $absoluteAdapterPosition"))
            headerOfCourseSection.text = item.title
        }
    }

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView = view.findViewById<View>(R.id.current_title) as TextView
        val smallDescriptiom: TextView = view.findViewById<View>(R.id.current_small_description) as TextView
        val cardView: CardView = view.findViewById(R.id.current_course) as CardView
        val imageCouse: ImageView = view.findViewById(R.id.imgCourse) as ImageView
        val fabOpenCourse: View = view.findViewById(R.id.fabOpenCourse)

        fun bind() {
            val item = (values[absoluteAdapterPosition] as? Item.Card
                    ?: throw Exception("Invalid item from pos $absoluteAdapterPosition")).course
            header.text = item.title
            smallDescriptiom.text = item.description
            header.contentDescription = item.courseNameID
            cardView.setOnClickListener { onCourseClicked.invoke(item) }
            imageCouse.setImageFromAssets(EcoTipsApp.instance.assets, item.pathItemCardImage())
            fabOpenCourse.setOnClickListener { cardView.callOnClick() }

        }
    }

}

sealed class Item {
    class Header(val title: String) : Item()
    class Card(val course: Course) : Item()
}