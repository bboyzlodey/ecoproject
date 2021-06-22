package skarlat.dev.ecoproject.di

import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.authentication.AppAuthenticator
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.core.AppCache

@Module(includes = [AppCacheModule::class])
class AuthModule {

    @Provides
    fun provideAuthManager(appCache: AppCache): AuthManager {
        return AuthManager(AppAuthenticator(appCache))
    }
}