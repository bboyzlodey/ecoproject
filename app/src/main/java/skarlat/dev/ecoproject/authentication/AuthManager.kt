package skarlat.dev.ecoproject.authentication

import android.os.Bundle
import skarlat.dev.ecoproject.Const
import javax.inject.Inject

class AuthManager @Inject constructor(val authenticator: Authenticator) {

    suspend fun authenticateWithGoogleAccount() {
        authenticator.authenticate(Bundle().apply { putSerializable(Const.AUTH_METHOD, AppAuthenticator.AuthMethod.Google) })
    }

    suspend fun authenticateWithEmailAndPassword(password: String, email: String) {
        authenticator.authenticate(Bundle().apply {
            putSerializable(Const.AUTH_METHOD, AppAuthenticator.AuthMethod.PassLogin)
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