package skarlat.dev.ecoproject.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.R

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()
        findNavController(R.id.nav_host)
        launchMainCoroutine()
    }

    private fun launchMainCoroutine() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.nextScreen.collectLatest {
                    it?.let { goToNextScreen(it) }
                }
            }
        }
    }

    private fun goToNextScreen(directions: NavDirections) {
        findNavController(R.id.nav_host)
                .navigate(directions)
        finishAfterTransition()
    }
}