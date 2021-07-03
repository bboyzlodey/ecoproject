package skarlat.dev.ecoproject.authentication

import android.os.Bundle
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.authentication.strategy.AuthStrategy
import javax.inject.Inject

class AuthManager @Inject constructor(private val authenticator: Authenticator, private val authStrategies: Map<AppAuthenticator.AuthMethod, AuthStrategy>) {

    suspend fun authenticateWithGoogleAccount() {
        authenticator.authStrategy = authStrategies[AppAuthenticator.AuthMethod.Google]
        authenticator.authenticate(Bundle().apply { putSerializable(Const.AUTH_METHOD, AppAuthenticator.AuthMethod.Google) })
    }

    suspend fun authenticateWithEmailAndPassword(password: String, email: String) {
        authenticator.authStrategy = authStrategies[AppAuthenticator.AuthMethod.PassLogin]
        authenticator.authenticate(Bundle().apply {
            putSerializable(Const.AUTH_METHOD, AppAuthenticator.AuthMethod.PassLogin)
            putSerializable(Const.AUTH_PASS, password)
            putSerializable(Const.AUTH_LOGIN, email)
        })

    }

    fun logout() {
        authenticator.authStrategy = authStrategies[AppAuthenticator.AuthMethod.Google]
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