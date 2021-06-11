package skarlat.dev.ecoproject.section

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.setImageFromAssets


class CourseSection(private val listCourses: List<Course>, private val sectionName: String, private val onCourseClicked: (Course) -> Unit) : Section(SectionParameters.builder()
        .itemResourceId(R.layout.card_course)
        .headerResourceId(R.layout.section_of_courses)
        .build()) {

    override fun getContentItemsTotal(): Int {
        return listCourses.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val courseHolder = holder as CourseViewHolder
        val item = listCourses[position]
        courseHolder.header.text = item.title
        courseHolder.smallDescriptiom.text = item.description
        courseHolder.header.contentDescription = item.courseNameID
        courseHolder.cardView.setOnClickListener { onCourseClicked.invoke(item) }
        courseHolder.imageCouse.setImageFromAssets(EcoTipsApp.instance.assets, item.pathItemCardImage())
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return CourseViewHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        if (listCourses.isNullOrEmpty())
            return EmptyViewHolder(view)
        return CourseCategoryViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        if (listCourses.isNullOrEmpty())
            return
        val courseSectionHeaderHolder = holder as CourseCategoryViewHolder
        courseSectionHeaderHolder.headerOfCourseSection.text = sectionName
        super.onBindHeaderViewHolder(holder)
    }

    inner class CourseCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headerOfCourseSection: TextView = itemView.findViewById(R.id.section_title_text) as TextView

    }

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView
        val smallDescriptiom: TextView
        val cardView: CardView
        val imageCouse: ImageView

        init {
            header = view.findViewById<View>(R.id.current_title) as TextView
            smallDescriptiom = view.findViewById<View>(R.id.current_small_description) as TextView
            cardView = view.findViewById(R.id.current_course) as CardView
            imageCouse = view.findViewById(R.id.imgCourse) as ImageView
        }
    }
}