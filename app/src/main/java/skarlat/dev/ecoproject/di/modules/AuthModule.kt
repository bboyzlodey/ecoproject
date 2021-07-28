package skarlat.dev.ecoproject.di.modules

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.authentication.AppAuthenticator
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.authentication.strategy.AuthStrategy
import skarlat.dev.ecoproject.authentication.strategy.GoogleSignInStrategy
import skarlat.dev.ecoproject.authentication.strategy.LoginPasswordStrategy
import skarlat.dev.ecoproject.data.AppCache
import skarlat.dev.ecoproject.di.scope.ActivityScope

@Module(includes = [ActivityModule::class])
class AuthModule {

    @Provides
    @ActivityScope
    fun provideAuthManager(appCache: AppCache, activity: AppCompatActivity): AuthManager {
        return AuthManager(AppAuthenticator(appCache, getAuthStrategies(activity)))
    }

    @Provides
    @ActivityScope
    fun getAuthStrategies(activity: AppCompatActivity) : Map<AppAuthenticator.AuthMethod, AuthStrategy> {
        return mapOf(
                AppAuthenticator.AuthMethod.Google to GoogleSignInStrategy(activity),
                AppAuthenticator.AuthMethod.PassLogin to LoginPasswordStrategy()
        )
    }

}