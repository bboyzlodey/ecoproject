package skarlat.dev.ecoproject.activity

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.fragment.BaseViewModel

class AuthViewModel : BaseViewModel() {

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