package skarlat.dev.ecoproject.authmanager

import android.os.Bundle
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.network.Authenticator
import skarlat.dev.ecoproject.network.FireBaseAuthenticator
import javax.inject.Inject

class AuthManager @Inject constructor(private val authenticator: Authenticator) {

    suspend fun isUserAuthored(): Boolean {
        return authenticator.currentUser != null
    }

    suspend fun authenticateWithGoogleAccount() {
        authenticator.authenticate(Bundle().apply { putSerializable(Const.AUTH_METHOD, FireBaseAuthenticator.AuthMethod.Google) })
    }

    suspend fun authenticateWithEmailAndPassword(password: String, email: String) {
        authenticator.authenticate(Bundle().apply {
            putSerializable(Const.AUTH_METHOD, FireBaseAuthenticator.AuthMethod.PassLogin)
            putSerializable(Const.AUTH_PASS, password)
            putSerializable(Const.AUTH_LOGIN, email)
        })

    }

    suspend fun logout() {
        authenticator.logout()
    }

    suspend fun registerUser(name: String, email: String, password: String) {
        authenticator.register(Bundle().apply {
            putSerializable(Const.REGISTER_NAME, name)
            putSerializable(Const.AUTH_PASS, password)
            putSerializable(Const.AUTH_LOGIN, email)
        })
    }
}