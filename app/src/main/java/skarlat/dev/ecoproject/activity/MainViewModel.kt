package skarlat.dev.ecoproject.activity

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.fragment.BaseViewModel
import skarlat.dev.ecoproject.fragment.SplashFragmentDirections

class MainViewModel: BaseViewModel() {

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