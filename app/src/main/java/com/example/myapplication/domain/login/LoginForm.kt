package com.example.myapplication.domain.login

/** User-entered values collected by the login form. */
data class LoginForm(
    /** Phone number entered by the user. */
    val mobileNumber: String = "",
    /** Email address entered by the user. */
    val email: String = "",
    /** Password entered by the user. */
    val password: String = ""
)

/** Per-field validation errors; a null value means that field passed validation. */
data class LoginValidation(
    /** Error for the mobile number, if invalid. */
    val mobileError: String? = null,
    /** Error for the email address, if invalid. */
    val emailError: String? = null,
    /** Error for the password, if invalid. */
    val passwordError: String? = null
) {
    /** True only when no field has a validation error. */
    val isValid: Boolean
        get() = mobileError == null && emailError == null && passwordError == null
}
