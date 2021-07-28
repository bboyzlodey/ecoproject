package skarlat.dev.ecoproject.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import skarlat.dev.ecoproject.presentation.auth.AuthActivity
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.di.modules.ActivityModule
import skarlat.dev.ecoproject.di.modules.AuthModule
import skarlat.dev.ecoproject.di.scope.ActivityScope

@Component(modules = [ActivityModule::class, AuthModule::class], dependencies = [AppComponent::class])
@ActivityScope
interface ActivityComponent {
    val authManager: AuthManager
    fun inject(activity: AuthActivity)
    fun inject(activity: AppCompatActivity)
}