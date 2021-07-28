package skarlat.dev.ecoproject.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.presentation.course.CourseActivity
import skarlat.dev.ecoproject.adapter.CourseAdapter
import skarlat.dev.ecoproject.adapter.Item
import skarlat.dev.ecoproject.data.User
import skarlat.dev.ecoproject.databinding.FragmentHomeBinding
import skarlat.dev.ecoproject.presentation.base.BaseFragment
import skarlat.dev.ecoproject.includes.dataclass.Course

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
        val courseAdapter = CourseAdapter(::onCourseClicked)
        courseAdapter.submitData(generateItems())
        binding.recycleCourses.adapter = courseAdapter
    }


    private fun generateItems() : List<Item> {
        val items = mutableListOf<Item>()
        EcoTipsApp.getDatabase().courseDao().allIsActive.run {
            if (isNotEmpty()) {
                if (size > 1) {
                    items.add(Item.Header(resources.getString(R.string.current_courses)))
                } else {
                    items.add(Item.Header(resources.getString(R.string.current_course)))
                }
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