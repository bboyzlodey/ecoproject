package skarlat.dev.ecoproject.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.fragment.SplashFragmentDirections

class MainViewModel(): ViewModel() {

    val nextScreen = MutableStateFlow<NavDirections?>(null)

    private val authManager = EcoTipsApp.appComponent.getAuthManager()

    init {
        viewModelScope.launch {
            handleNextScreenDirection()
        }
    }

    private suspend fun handleNextScreenDirection() {
        val nextDirections = if (authManager.isUserAuthored()) {
            SplashFragmentDirections.actionSplashToHomeActivity()
        } else {
            SplashFragmentDirections.actionSplashToSignInActivity()
        }
        nextScreen.emit(nextDirections)
    }
}