package skarlat.dev.ecoproject.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {


    private val viewModel: RegistrationViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userEmail.doAfterTextChanged { text -> viewModel.email = text.toString() }
        binding.userName.doAfterTextChanged { text -> viewModel.name = text.toString() }
        binding.userPasswd.doAfterTextChanged { text -> viewModel.password = text.toString() }
        binding.submit.setOnClickListener { viewModel.onRegisterClicked() }
        binding.signIn.setOnClickListener {
            navController.popBackStack()
        }
        binding.signInGoogle.setOnClickListener {
            viewModel.onSignWithGoogleClicked()
        }
    }

    private fun launchObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.enableRegisterButton.collectLatest {
                    binding.submit.isEnabled = it
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        launchObservers()
    }

    override fun inflateBinding(): ViewBinding {
        return FragmentRegistrationBinding.inflate(layoutInflater)
    }
}