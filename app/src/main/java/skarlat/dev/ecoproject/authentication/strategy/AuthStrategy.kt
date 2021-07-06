package skarlat.dev.ecoproject.authentication.strategy

import android.os.Bundle
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.authentication.AppAuthenticator
import skarlat.dev.ecoproject.authentication.AuthErrorHandler

abstract class AuthStrategy {
    protected val appCache = EcoTipsApp.appComponent.getAppCache()
    var errorHandler: AuthErrorHandler? = null
    abstract val authType: AppAuthenticator.AuthMethod
    abstract fun authenticate(context: Bundle)
    open fun logout() {
        appCache.clear()
    }
}