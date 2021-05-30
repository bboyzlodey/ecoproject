package skarlat.dev.ecoproject.authmanager

import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.network.Authenticator
import javax.inject.Inject

class AuthManager @Inject constructor(private val authenticator: Authenticator) {

    suspend fun isUserAuthored() : Boolean {
        return authenticator.currentUser == null
    }
}