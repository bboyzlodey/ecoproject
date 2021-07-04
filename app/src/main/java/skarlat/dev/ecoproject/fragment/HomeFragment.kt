package skarlat.dev.ecoproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import skarlat.dev.ecoproject.adapter.CourseAdapter
import skarlat.dev.ecoproject.adapter.Item
import skarlat.dev.ecoproject.databinding.FragmentHomeBinding
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.section.CourseSection

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun inflateBinding(): ViewBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImage.setOnClickListener { viewModel.onProfileClicked() }
        launchCoroutines()
    }

    private fun launchCoroutines() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.nextScreen.collectLatest {
                    goToNextScreen(it)
                }
            }
            launch {
                viewModel.user.collectLatest {
                    updateUser(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initUI()
    }

    private fun updateUser(user: User) {
        binding.helloUser.text = getString(R.string.hello_user_mask, user.name)
    }

    private fun initUI() {
        val sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allIsActive, resources.getString(R.string.current_courses), ::onCourseClicked))
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allNonActive, resources.getString(R.string.aviable_courses), ::onCourseClicked))
        sectionedRecyclerViewAdapter.addSection(CourseSection(EcoTipsApp.getDatabase().courseDao().allFinished, resources.getString(R.string.finished_courses), ::onCourseClicked))
        val courseAdapter = CourseAdapter(::onCourseClicked)
        courseAdapter.submitData(generateItems())
        binding.recycleCourses.adapter = courseAdapter
    }


    private fun generateItems() : List<Item> {
        val items = mutableListOf<Item>()
        EcoTipsApp.getDatabase().courseDao().allIsActive.run {
            if (isNotEmpty()) {
                items.add(Item.Header(resources.getString(R.string.current_courses)))
                items.addAll(map { Item.Card(it) })
            }
        }
        EcoTipsApp.getDatabase().courseDao().allNonActive.run {
            if (isNotEmpty()) {
                items.add(Item.Header(resources.getString(R.string.aviable_courses)))
                items.addAll(map { Item.Card(it) })
            }
        }
        EcoTipsApp.getDatabase().courseDao().allFinished.run {
            if (isNotEmpty()) {
                items.add(Item.Header(resources.getString(R.string.finished_courses)))
                items.addAll(map { Item.Card(it) })
            }
        }
        return items
    }
    private fun onCourseClicked(course: Course) {
        val open = Intent(requireContext(), CourseActivity::class.java)
        open.putExtra("OPEN_COURSE", course.courseNameID)
        startActivity(open)
    }
}