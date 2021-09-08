package skarlat.dev.ecoproject.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import skarlat.dev.ecoproject.activity.AuthActivity
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.core.ErrorBus

@Component(modules = [ActivityModule::class, AuthModule::class], dependencies = [AppComponent::class])
@ActivityScope
interface ActivityComponent {
    val authManager: AuthManager
    val errorBus: ErrorBus
    fun inject(activity: AuthActivity)
    fun inject(activity: AppCompatActivity)
}