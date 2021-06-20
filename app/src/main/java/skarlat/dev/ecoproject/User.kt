package skarlat.dev.ecoproject

import kotlinx.serialization.Serializable

@Serializable
data class User(
        val name: String,
        val email: String
) {
    companion object {
        val empty = User("", "")
    }
}

val User.isAuthored: Boolean get() = this != User.empty