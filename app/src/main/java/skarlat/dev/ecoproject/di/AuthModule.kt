package skarlat.dev.ecoproject.di

import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.authmanager.AuthManager
import skarlat.dev.ecoproject.core.AppCache
import skarlat.dev.ecoproject.network.FireBaseAuthenticator

@Module(includes = [AppCacheModule::class])
class AuthModule(/*val appCache: AppCache*/) {

    @Provides
    fun provideAuthManager(appCache: AppCache) : AuthManager {
        return AuthManager(FireBaseAuthenticator(appCache))
    }
}