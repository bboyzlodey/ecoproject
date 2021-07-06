package skarlat.dev.ecoproject.authentication

sealed class AuthError : Throwable(){
    class Default : AuthError()
}