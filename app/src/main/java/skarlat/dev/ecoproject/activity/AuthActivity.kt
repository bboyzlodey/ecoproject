package skarlat.dev.ecoproject.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.authentication.strategy.GoogleSignInStrategy
import skarlat.dev.ecoproject.databinding.ActivitySignInBinding
import skarlat.dev.ecoproject.showSnackBar

class AuthActivity : AppCompatActivity() {

    val googleSignInStrategy = GoogleSignInStrategy(this)
    private val viewModel: AuthViewModel by viewModels()

    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.nextScreen.collectLatest {
                    findNavController(R.id.nav_host).navigate(it)
                    finishAfterTransition()
                }
            }
            launch {
                viewModel.errorMessage.collectLatest {
                    binding.showSnackBar(it)
                }
            }
        }
    }
}