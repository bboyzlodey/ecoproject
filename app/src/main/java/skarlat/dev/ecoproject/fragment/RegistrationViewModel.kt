package skarlat.dev.ecoproject.fragment

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.fragment.SplashFragmentDirections

class RegistrationViewModel(): BaseViewModel() {

    val enableRegisterButton = MutableStateFlow<Boolean>(false)

    private val authManager = EcoTipsApp.appComponent.getAuthManager()
    private val isAllFieldsValid : Boolean
        get() = email.matches(Patterns.EMAIL_ADDRESS.toRegex()) && password.length >= Const.MIN_LENGTH_PASSWORD && name.isNotBlank()

    var name = ""
        set(value) {
            field = value
            checkEnablingSignInButton()
        }

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

    }


    fun onSignWithEmailAndPasswordClicked() {
        // TODO
    }

    private fun checkEnablingSignInButton() {
        enableRegisterButton.tryEmit(isAllFieldsValid)
    }

    fun onSignWithGoogleClicked() {
        viewModelScope.launch {
            authManager.authenticateWithGoogleAccount()
        }
    }

    fun onRegisterClicked() {
        viewModelScope.launch {
            authManager.registerUser(name, email, password)
        }
    }
}