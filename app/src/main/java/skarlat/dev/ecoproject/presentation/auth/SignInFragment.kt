package skarlat.dev.ecoproject.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.databinding.FragmentSignInBinding
import skarlat.dev.ecoproject.presentation.base.BaseFragment

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    override fun inflateBinding(): ViewBinding {
        return FragmentSignInBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AuthActivity).component?.authManager?.let {
            viewModel.authManager = it
        }
        binding.userPasswd.doAfterTextChanged { text -> viewModel.password = text.toString() }
        binding.userEmail.doAfterTextChanged { text -> viewModel.email = text.toString() }
        binding.signIn.setOnClickListener {
            viewModel.onSignWithEmailAndPasswordClicked()
        }
        binding.signInGoogle.setOnClickListener {
            viewModel.onSignWithGoogleClicked()
        }
        binding.register.setOnClickListener { viewModel.onRegistrationClicked() }
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
                viewModel.enableSignInButton.collect {
                    binding.signIn.isEnabled = it
                }
            }
        }
    }
}