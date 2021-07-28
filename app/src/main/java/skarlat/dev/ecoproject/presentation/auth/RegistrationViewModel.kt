package skarlat.dev.ecoproject.presentation.auth

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.authentication.AuthManager
import skarlat.dev.ecoproject.presentation.base.BaseViewModel

class RegistrationViewModel /*@Inject constructor(private val authManager: AuthManager)*/ : BaseViewModel() {

    lateinit var authManager: AuthManager

    val enableRegisterButton = MutableStateFlow<Boolean>(false)

    private val isAllFieldsValid: Boolean
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