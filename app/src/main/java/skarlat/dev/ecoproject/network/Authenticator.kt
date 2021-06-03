package skarlat.dev.ecoproject.network

import android.os.Bundle
import skarlat.dev.ecoproject.User

abstract class Authenticator {
    abstract val currentUser: User?
    abstract fun authenticate(bundle: Bundle)
    abstract fun logout()
}