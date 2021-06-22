package skarlat.dev.ecoproject.authmanager

sealed class AuthError : Throwable(){
    class Default : AuthError()
}