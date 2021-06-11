package skarlat.dev.ecoproject.fragment

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.di.DaggerAppComponent

class HomeViewModel: BaseViewModel() {

    private val appCache = EcoTipsApp.appComponent.getAppCache()
    val user get() = appCache.userFlow
}
