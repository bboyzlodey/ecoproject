package skarlat.dev.ecoproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.activity.CourseActivity
import skarlat.dev.ecoproject.databinding.FragmentHomeBinding
import skarlat.dev.ecoproject.section.CourseSection

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val viewModel : HomeViewModel by viewModels()

    override fun inflateBinding(): ViewBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.nextScreen.collectLatest {
                    goToNextScreen(it)
                }
            }
            launch {
                EcoTipsApp.appComponent.getAppCache().userFlow.collectLatest {
                    updateUser(it)
                }
            }
        }
        initUI()
    }

    private fun updateUser(user: User) {
        binding.helloUser.text = getString(R.string.hello_user_mask, user.name)
    }

    private fun initUI() {
        val sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allIsActive, resources.getString(R.string.current_courses)))
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allNonActive, resources.getString(R.string.aviable_courses)))
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allFinished, resources.getString(R.string.finished_courses)))
        binding.recycleCourses.adapter = sectionedRecyclerViewAdapter
    }

    fun openCourse(v: View) {
        val open = Intent(requireContext(), CourseActivity::class.java)
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

}