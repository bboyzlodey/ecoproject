package skarlat.dev.ecoproject.di.modules

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.di.scope.ActivityScope

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun getActivity(): AppCompatActivity {
        return activity
    }
}