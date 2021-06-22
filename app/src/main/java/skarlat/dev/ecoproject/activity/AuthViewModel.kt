package skarlat.dev.ecoproject.activity

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.authmanager.AuthErrorHandler
import skarlat.dev.ecoproject.authmanager.DefaultErrorHandler
import skarlat.dev.ecoproject.fragment.BaseViewModel

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