package com.example.myapplication.presentation.login

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.login.LoginForm
import com.example.myapplication.domain.login.LoginValidation
import com.example.myapplication.domain.login.RecordLoginAttemptUseCase
import com.example.myapplication.domain.login.ValidateLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Immutable values required to render the login screen at a given moment. */
data class LoginUiState(
    val form: LoginForm = LoginForm(),
    val validation: LoginValidation = LoginValidation(),
    val loginCount: Int = 0,
    val showValidation: Boolean = false,
    val loginSuccess: Boolean = false
)

/** Coordinates form edits, domain validation, and login-attempt counting for the UI. */
class LoginViewModel(
    private val validateLogin: ValidateLoginUseCase,
    private val recordLoginAttempt: RecordLoginAttemptUseCase
) : ViewModel() {
    /** Mutable state owned by this ViewModel and updated in response to UI actions. */
    private val _uiState = MutableStateFlow(LoginUiState())
    /** Read-only stream consumed by Compose to render the current screen. */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /** Updates the mobile number in the form state. */
    fun onMobileChanged(value: String) = updateForm { copy(mobileNumber = value) }
    /** Updates the email address in the form state. */
    fun onEmailChanged(value: String) = updateForm { copy(email = value) }
    /** Updates the password in the form state. */
    fun onPasswordChanged(value: String) = updateForm { copy(password = value) }

    /** Validates all fields, records this button tap, and publishes the resulting UI state. */
    fun onLoginClicked() {
        val current = _uiState.value
        val validation = validateLogin(current.form)
        val loginCount = recordLoginAttempt()
        // Every tap counts, including taps that reveal validation errors.
        _uiState.update {
            it.copy(
                validation = validation,
                showValidation = true,
                loginCount = loginCount,
                loginSuccess = validation.isValid
            )
        }
    }

    /** Applies one form-field change and clears the prior success indicator. */
    private inline fun updateForm(crossinline transform: LoginForm.() -> LoginForm) {
        _uiState.update { state ->
            state.copy(form = state.form.transform(), loginSuccess = false)
        }
    }
}
