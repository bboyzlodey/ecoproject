package skarlat.dev.ecoproject.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import skarlat.dev.ecoproject.authentication.AppAuthenticator

@Keep
@Serializable
data class User(
        val name: String,
        val email: String,
        val authMethod: AppAuthenticator.AuthMethod
) : java.io.Serializable {
    companion object {
        val empty = User("", "", AppAuthenticator.AuthMethod.PassLogin)
    }
}

val User.isAuthored: Boolean get() = this != User.empty