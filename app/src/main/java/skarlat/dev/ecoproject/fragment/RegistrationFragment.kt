package skarlat.dev.ecoproject.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import skarlat.dev.ecoproject.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {


    private val viewModel: RegistrationViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun inflateBinding(): ViewBinding {
        return FragmentRegistrationBinding.inflate(layoutInflater)
    }
}