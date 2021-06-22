package skarlat.dev.ecoproject.authmanager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import timber.log.Timber

interface AuthErrorHandler {
    val errorMessage: Flow<String> get() = errorMessageHandler
    val errorMessageHandler: MutableSharedFlow<String>

    fun onError(e: Throwable) {
        Timber.d("Error ${e.javaClass.simpleName}")
    }
}

class DefaultErrorHandler : AuthErrorHandler {
    override val errorMessageHandler: MutableSharedFlow<String> = MutableSharedFlow(1, 1)

    override fun onError(e: Throwable) {
        super.onError(e)
        errorMessageHandler.tryEmit("Error")
    }
}