package skarlat.dev.ecoproject.fragment

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    val nextScreen = MutableStateFlow<NavDirections?>(null)
}