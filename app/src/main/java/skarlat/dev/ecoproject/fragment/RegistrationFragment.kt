package skarlat.dev.ecoproject.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {


    private val viewModel: RegistrationViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userEmail.doAfterTextChanged { text -> viewModel.email = text.toString() }
        binding.userName.doAfterTextChanged { text -> viewModel.name = text.toString() }
        binding.userPasswd.doAfterTextChanged { text -> viewModel.password = text.toString() }
        binding.submit.setOnClickListener { viewModel.onRegisterClicked() }
        binding.signIn.setOnClickListener {
            viewModel.onSignWithEmailAndPasswordClicked()
            requireActivity().onBackPressed()
        }
        binding.signInGoogle.setOnClickListener { viewModel.onSignWithGoogleClicked() }
        launchObservers()
    }

    private fun launchObservers() {
        lifecycleScope.launch {
            viewModel.enableRegisterButton.collectLatest {
                binding.signIn.isEnabled = it
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun inflateBinding(): ViewBinding {
        return FragmentRegistrationBinding.inflate(layoutInflater)
    }
}