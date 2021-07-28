package skarlat.dev.ecoproject.presentation.home

import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.presentation.base.BaseViewModel

class HomeViewModel: BaseViewModel() {

    fun onProfileClicked() {
        nextScreen.tryEmit(HomeFragmentDirections.actionHomeFragmentToUserFragment())
    }

    private val appCache = EcoTipsApp.appComponent.getAppCache()
    val user get() = appCache.userFlow
}
