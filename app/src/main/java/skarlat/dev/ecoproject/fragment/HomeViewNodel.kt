package skarlat.dev.ecoproject.fragment

import skarlat.dev.ecoproject.EcoTipsApp

class HomeViewModel: BaseViewModel() {

    fun onProfileClicked() {
        nextScreen.tryEmit(HomeFragmentDirections.actionHomeFragmentToUserFragment())
    }

    private val appCache = EcoTipsApp.appComponent.getAppCache()
    val user get() = appCache.userFlow
}
