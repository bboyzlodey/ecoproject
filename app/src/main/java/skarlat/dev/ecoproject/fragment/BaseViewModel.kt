package skarlat.dev.ecoproject.fragment

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseViewModel : ViewModel() {
    open val nextScreen = MutableSharedFlow<NavDirections>(replay = 0, extraBufferCapacity = 1)
}