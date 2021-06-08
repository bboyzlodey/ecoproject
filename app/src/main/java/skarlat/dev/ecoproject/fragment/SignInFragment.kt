package skarlat.dev.ecoproject.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val viewModel : SignInViewModel by viewModels()

    override fun inflateBinding(): ViewBinding {
        return FragmentSignInBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userEmail.doAfterTextChanged {text ->  viewModel.email = text.toString() }
        binding.userPasswd.doAfterTextChanged {text ->  viewModel.password = text.toString() }
        binding.signIn.setOnClickListener { viewModel.onSignWithEmailAndPasswordClicked() }
        binding.signInGoogle.setOnClickListener { viewModel.onSignWithGoogleClicked() }
    }

    override fun onStart() {
        super.onStart()
        launchMainCoroutine()
    }

    private fun launchMainCoroutine() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.nextScreen.collectLatest {
                    it?.let { goToNextScreen(it) }
                }
            }
            launch {
                viewModel.enableSignInButton.collectLatest {
                    binding.signIn.isEnabled = it
                }
            }
        }
    }

    private fun goToNextScreen(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}