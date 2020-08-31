package skarlat.dev.ecoproject.section

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder
import skarlat.dev.ecoproject.Course
import skarlat.dev.ecoproject.R


class CourseSection (val listCourses: List<Course>, val sectionName: String) : Section(SectionParameters.builder()
        .itemResourceId(R.layout.card_course)
        .headerResourceId(R.layout.section_of_courses)
        .build()) {

    override fun getContentItemsTotal(): Int {
        return listCourses.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val courseHolder = holder as CourseViewHolder
        courseHolder.header.text = listCourses[position].title
        courseHolder.smallDescriptiom.text = listCourses[position].description
        courseHolder.header.contentDescription = listCourses[position].courseNameID
//        courseHolder.cardView.setBackgroundDrawable(listCourses[position].image)
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
        var headerOfCourseSection: TextView

        init {
            headerOfCourseSection = itemView.findViewById(R.id.section_title_text) as TextView
        }
    }

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView
        val smallDescriptiom: TextView
        val cardView: CardView

        init {
            header = view.findViewById<View>(R.id.current_title) as TextView
            smallDescriptiom = view.findViewById<View>(R.id.current_small_description) as TextView
            cardView = view.findViewById(R.id.current_course) as CardView
        }
    }
}