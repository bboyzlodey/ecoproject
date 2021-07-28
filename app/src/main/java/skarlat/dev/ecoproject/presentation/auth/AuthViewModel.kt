package skarlat.dev.ecoproject.presentation.auth

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.authentication.AuthErrorHandler
import skarlat.dev.ecoproject.authentication.DefaultErrorHandler
import skarlat.dev.ecoproject.presentation.base.BaseViewModel
import skarlat.dev.ecoproject.presentation.home.HomeActivityDirections

class AuthViewModel : BaseViewModel(), AuthErrorHandler by DefaultErrorHandler() {

    private val appCache = EcoTipsApp.appComponent.getAppCache()

    init {
        viewModelScope.launch {
            handleNextScreenDirection()
        }
    }

    private suspend fun handleNextScreenDirection() {
        appCache.userFlow.collectLatest {
            if (it.name.isNotBlank()) nextScreen.tryEmit(HomeActivityDirections.globalActionToHome())
        }
    }
}