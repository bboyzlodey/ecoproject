package skarlat.dev.ecoproject.data

import kotlinx.serialization.Serializable
import skarlat.dev.ecoproject.authentication.AppAuthenticator

@Serializable
data class User(
        val name: String,
        val email: String,
        val authMethod: AppAuthenticator.AuthMethod
) {
    companion object {
        val empty = User("", "", AppAuthenticator.AuthMethod.PassLogin)
    }
}

val User.isAuthored: Boolean get() = this != User.empty