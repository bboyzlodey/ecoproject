package skarlat.dev.ecoproject.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun getActivity(): AppCompatActivity {
        return activity
    }
}