package skarlat.dev.ecoproject.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.authentication.AppAuthenticator
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.authentication.strategy.AuthStrategy
import skarlat.dev.ecoproject.authentication.strategy.GoogleSignInStrategy
import skarlat.dev.ecoproject.authentication.strategy.LoginPasswordStrategy
import skarlat.dev.ecoproject.core.AppCache
import skarlat.dev.ecoproject.core.ErrorBus

@Module(includes = [ActivityModule::class])
class AuthModule {

    @Provides
    @ActivityScope
    fun provideAuthManager(
        appCache: AppCache,
        activity: AppCompatActivity,
        errorBus: ErrorBus
    ): AuthManager {
        return AuthManager(AppAuthenticator(appCache, getAuthStrategies(activity, errorBus)))
    }

    @Provides
    @ActivityScope
    fun getAuthStrategies(
        activity: AppCompatActivity,
        errorBus: ErrorBus
    ): Map<AppAuthenticator.AuthMethod, AuthStrategy> {
        return mapOf(
            AppAuthenticator.AuthMethod.Google to GoogleSignInStrategy(activity, errorBus),
            AppAuthenticator.AuthMethod.PassLogin to LoginPasswordStrategy(errorBus)
        )
    }

}