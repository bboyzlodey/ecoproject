package skarlat.dev.ecoproject.section

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.Course
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.App
import java.io.InputStream


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
        val imgDrawable : Drawable = getImageDrawableForCourse(getPathToImage(position))
        courseHolder.imageCouse.setImageDrawable(imgDrawable)
    }

    private fun getPathToImage(position: Int) : String{
        return Const.IMAGES_ROOT_FOLDER + listCourses[position].courseNameID + "/" + listCourses[position].courseNameID + ".png"
    }

    private fun getImageDrawableForCourse(path: String) : Drawable{
        val assetManager = App.instance.resources.assets
        var drawable: Drawable

        try {
            val steam: InputStream = assetManager.open(path)
            drawable = BitmapDrawable(steam)
//            drawable = Drawable.createFromPath(path)!!
        } catch (p: Exception){
            drawable = App.instance.getDrawable(R.drawable.lvl_1_1)!!
        }
        return drawable
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
        val imageCouse : ImageView

        init {
            header = view.findViewById<View>(R.id.current_title) as TextView
            smallDescriptiom = view.findViewById<View>(R.id.current_small_description) as TextView
            cardView = view.findViewById(R.id.current_course) as CardView
            imageCouse = view.findViewById(R.id.imgCourse) as ImageView
        }
    }
}