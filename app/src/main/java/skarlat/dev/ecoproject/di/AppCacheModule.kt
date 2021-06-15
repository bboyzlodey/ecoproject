package skarlat.dev.ecoproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import skarlat.dev.ecoproject.core.AppCache
import javax.inject.Singleton

@Module
class AppCacheModule(val context: Context) {

    @Singleton
    @Provides
    fun provideAppCache() : AppCache {
        return AppCache(context)
    }
}