package skarlat.dev.ecoproject.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.activity.AuthActivity
import skarlat.dev.ecoproject.authentication.strategy.LoginPasswordStrategy
import skarlat.dev.ecoproject.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    override fun inflateBinding(): ViewBinding {
        return FragmentSignInBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userEmail.doAfterTextChanged { text -> viewModel.email = text.toString() }
        binding.userPasswd.doAfterTextChanged { text -> viewModel.password = text.toString() }
        binding.signIn.setOnClickListener {
            viewModel.setAuthStrategy(LoginPasswordStrategy())
            viewModel.onSignWithEmailAndPasswordClicked()
        }
        binding.signInGoogle.setOnClickListener {
            viewModel.setAuthStrategy((requireActivity() as AuthActivity).googleSignInStrategy)
            viewModel.onSignWithGoogleClicked()
        }
        binding.register.setOnClickListener { viewModel.onRegistrationClicked() }
    }

    override fun onStart() {
        super.onStart()
        launchMainCoroutine()
    }

    private fun launchMainCoroutine() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.nextScreen.collectLatest {
                    goToNextScreen(it)
                }
            }
            launch {
                viewModel.enableSignInButton.collectLatest {
                    binding.signIn.isEnabled = it
                }
            }
        }
    }
}