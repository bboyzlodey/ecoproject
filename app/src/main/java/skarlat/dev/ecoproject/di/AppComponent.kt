package skarlat.dev.ecoproject.di

import dagger.Component
import skarlat.dev.ecoproject.data.AppCache
import skarlat.dev.ecoproject.di.modules.AppCacheModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppCacheModule::class])
interface AppComponent {
    fun getAppCache() : AppCache
}