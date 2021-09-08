package skarlat.dev.ecoproject.core

sealed interface Error {
    sealed interface AuthError : Error {
        object Registration : AuthError
        class SignIn(val exception: Throwable?) : AuthError
        class SignInWithGoogle(val exception: Throwable?) : AuthError
    }
}