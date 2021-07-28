package skarlat.dev.ecoproject.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.data.AppCache
import javax.inject.Singleton

@Module
class AppCacheModule(val context: Context) {

    @Singleton
    @Provides
    fun provideAppCache() : AppCache {
        return AppCache(context)
    }
}