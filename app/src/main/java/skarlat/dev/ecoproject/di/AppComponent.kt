package skarlat.dev.ecoproject.di

import dagger.Component
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.core.AppCache
import javax.inject.Singleton

@Singleton
@Component(modules = [AppCacheModule::class])
interface AppComponent {
    fun getAppCache() : AppCache
}