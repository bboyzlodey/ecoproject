package skarlat.dev.ecoproject.activity

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.fragment.BaseViewModel
import skarlat.dev.ecoproject.fragment.SplashFragmentDirections
import skarlat.dev.ecoproject.isAuthored

class MainViewModel : BaseViewModel() {

    private val appCache = EcoTipsApp.appComponent.getAppCache()
    override val nextScreen = MutableSharedFlow<NavDirections>(replay = 1, extraBufferCapacity = 1)

    init {
        viewModelScope.launch {
            handleNextScreenDirection()
        }
    }

    private suspend fun handleNextScreenDirection() {
        viewModelScope.launch {
            appCache.userFlow.collectLatest {
                nextScreen.emit(if (it.isAuthored) {
                    SplashFragmentDirections.actionSplashToHomeActivity()
                } else {
                    SplashFragmentDirections.actionSplashToSignInActivity()
                })

            }
        }
    }
}