package skarlat.dev.ecoproject.di.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.di.ActivityComponent
import skarlat.dev.ecoproject.di.ActivityModule
import skarlat.dev.ecoproject.di.DaggerActivityComponent
import timber.log.Timber
import kotlin.reflect.KProperty


fun AppCompatActivity.provideComponent(): ActivityComponentHandler {
    return ActivityComponentHandler(this)
}

class ActivityComponentHandler(activity: AppCompatActivity) : LifecycleObserver {
    private var component: ActivityComponent? = DaggerActivityComponent.builder().activityModule(ActivityModule(activity)).appComponent(EcoTipsApp.appComponent).build()

    init {
        activity.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyActivity() {
        Timber.d("${this::class.simpleName} ${Lifecycle.Event.ON_DESTROY}")
        component = null
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): ActivityComponent? {
        return component
    }
}
