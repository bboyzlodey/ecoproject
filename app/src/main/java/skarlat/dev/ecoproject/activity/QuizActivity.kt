package skarlat.dev.ecoproject.activity

import QuizAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_test.*
import skarlat.dev.ecoproject.R


class QuizActivity : AppCompatActivity() {

    private var testArray = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8")
//    private var QuizPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//        override fun onPageSelected(position: Int) {
//            Toast.makeText(this@QuizActivity, "Selected position: ${position}",
//                    Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val viewPager: ViewPager2 = findViewById(R.id.quizViewPager)
        val pagerAdapter = QuizAdapter(this, testArray.size)
        viewPager.adapter = pagerAdapter





/*
Method for concatenation  ViewPager and tabLayout
 */
        TabLayoutMediator(tabLayout, quizViewPager) { tab, position ->
            tab.text=testArray[position]

        }.attach()


        quizViewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_LTR
        tabLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR

        //Make  tabIndicator invisible
        tabLayout.setSelectedTabIndicator(null)





/*
Method for driving TabLayout
 */

//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//
//        })






    }


}