package skarlat.dev.ecoproject.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.core.ErrorBus

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun getActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    @ActivityScope
    fun getErrorBus() : ErrorBus {
        return ErrorBus()
    }
}