package skarlat.dev.ecoproject.di

import android.content.Context
import dagger.Component
import skarlat.dev.ecoproject.authmanager.AuthManager
import skarlat.dev.ecoproject.core.AppCache
import javax.inject.Singleton

@Singleton
@Component(modules = [AppCacheModule::class, AuthModule::class])
interface AppComponent {
    fun getAppCache() : AppCache
    fun getAuthManager() : AuthManager
}