package skarlat.dev.ecoproject.core

import kotlinx.coroutines.flow.MutableSharedFlow
import skarlat.dev.ecoproject.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class ErrorBus @Inject constructor() {
    val error = MutableSharedFlow<Error>(extraBufferCapacity = 1)
}