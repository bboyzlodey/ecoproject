package skarlat.dev.ecoproject.network

import android.os.Bundle
import skarlat.dev.ecoproject.authmanager.strategy.AuthStrategy

abstract class Authenticator {
    var authStrategy: AuthStrategy? = null
    abstract fun authenticate(bundle: Bundle)
    abstract fun logout()
    abstract suspend fun register(bundle: Bundle)
}