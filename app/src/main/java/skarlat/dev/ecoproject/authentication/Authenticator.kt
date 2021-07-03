package skarlat.dev.ecoproject.authentication

import android.os.Bundle
import skarlat.dev.ecoproject.authentication.strategy.AuthStrategy

abstract class Authenticator {
    var authStrategy: AuthStrategy? = null
    abstract fun authenticate(bundle: Bundle)
    abstract suspend fun logout()
    abstract suspend fun register(bundle: Bundle)
}