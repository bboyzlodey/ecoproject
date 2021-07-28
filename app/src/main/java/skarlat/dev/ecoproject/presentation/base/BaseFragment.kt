package skarlat.dev.ecoproject.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>: Fragment() {

    private var _binding: ViewBinding? = null
    protected open val binding: T get() = _binding!! as T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun inflateBinding() : ViewBinding

    protected fun goToNextScreen(directions: NavDirections?) {
        directions?.let { findNavController().navigate(it) }
    }
}