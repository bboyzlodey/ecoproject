package skarlat.dev.ecoproject.fragment

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.EcoTipsApp

class SignInViewModel : BaseViewModel() {

    val enableSignInButton = MutableStateFlow<Boolean>(false)

    private val authManager = EcoTipsApp.appComponent.getAuthManager()
    private val isAllFieldsValid: Boolean
        get() = email.matches(Patterns.EMAIL_ADDRESS.toRegex()) && password.length >= Const.MIN_LENGTH_PASSWORD

    var email = ""
        set(value) {
            field = value
            checkEnablingSignInButton()
        }

    var password = ""
        set(value) {
            field = value
            checkEnablingSignInButton()
        }

    init {
        checkEnablingSignInButton()
    }


    fun onSignWithEmailAndPasswordClicked() {
        signInWithEmailAndPassword()
    }

    private fun checkEnablingSignInButton() {
        enableSignInButton.tryEmit(isAllFieldsValid)
    }

    private fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            authManager.authenticateWithEmailAndPassword(password, email)
        }
    }

    fun onSignWithGoogleClicked() {
        viewModelScope.launch {
            authManager.authenticateWithGoogleAccount()
        }
    }

    fun onRegistrationClicked() {
        nextScreen.tryEmit(SignInFragmentDirections.actionSignInFragmentToRegistrationFragment())
    }
}