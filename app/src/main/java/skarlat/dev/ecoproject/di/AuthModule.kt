package skarlat.dev.ecoproject.di

import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.authmanager.AuthManager
import skarlat.dev.ecoproject.core.AppCache
import skarlat.dev.ecoproject.network.AppAuthenticator

@Module(includes = [AppCacheModule::class])
class AuthModule {

    @Provides
    fun provideAuthManager(appCache: AppCache): AuthManager {
        return AuthManager(AppAuthenticator(appCache))
    }
}