package skarlat.dev.ecoproject.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.core.SettingsManager
import skarlat.dev.ecoproject.databinding.ActivityHomeBinding
import skarlat.dev.ecoproject.includes.database.DataBaseCopy
import skarlat.dev.ecoproject.section.CourseSection
import java.io.IOException

class HomeActivity : FragmentActivity() {
    private var binding: ActivityHomeBinding? = null
    private val TAG = "HomeActivity"
    private val fragmentManager: FragmentManager? = null
    private val fragment: Fragment? = null
    private var settingsManager: SettingsManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        settingsManager = SettingsManager(getSharedPreferences(Const.ECO_TIPS_PREFERENCES, MODE_PRIVATE))
        binding!!.profileImage.setOnClickListener { openUserFragment() }
        val dataBaseCopy = DataBaseCopy(this)
        try {
            dataBaseCopy.createDataBase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initUI() {
        val sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allIsActive, resources.getString(R.string.current_courses)))
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allNonActive, resources.getString(R.string.aviable_courses)))
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allFinished, resources.getString(R.string.finished_courses)))
        binding!!.recycleCourses.adapter = sectionedRecyclerViewAdapter
    }

    fun openCourse(v: View) {
        val open = Intent(this, CourseActivity::class.java)
        val charSequence = v.contentDescription
        if (charSequence != null) {
            open.putExtra("OPEN_COURSE", charSequence.toString())
            startActivity(open)
        } else {
            val textView = v.findViewById<TextView>(R.id.current_title)
            val str = textView.contentDescription
            if (str != null) {
                open.putExtra("OPEN_COURSE", str.toString())
                startActivity(open)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            EcoTipsApp.appComponent.getAppCache().userFlow.collectLatest {
                updateUser(it)
            }
        }
        initUI()
    }

    private fun updateUser(user: User) {
        binding!!.helloUser.text = getString(R.string.hello_user_mask, user.name)
    }

    private fun openUserFragment() {
        binding!!.linearLayout.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (fragmentManager == null) {
            super.onBackPressed()
            return
        }
        val fragmentList = fragmentManager.fragments
        if (fragmentList.size > 0) {
            binding!!.linearLayout.visibility = View.VISIBLE
            fragmentManager.beginTransaction().detach(fragment!!).commit()
        } else super.onBackPressed()
    }
}