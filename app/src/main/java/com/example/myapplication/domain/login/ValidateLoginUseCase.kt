package com.example.myapplication.domain.login

/** Applies the login form's required-field and format rules. */
class ValidateLoginUseCase {
    /** Returns an error for each invalid field, or a valid result when all fields pass. */
    operator fun invoke(form: LoginForm): LoginValidation = LoginValidation(
        mobileError = validateMobile(form.mobileNumber),
        emailError = validateEmail(form.email),
        passwordError = validatePassword(form.password)
    )

    /** Accepts 10–15 digits, optionally prefixed with a plus sign. */
    private fun validateMobile(value: String): String? = when {
        value.isBlank() -> "Mobile number is required"
        !value.matches(Regex("^[+]?[0-9]{10,15}$")) -> "Enter a valid mobile number"
        else -> null
    }

    /** Checks for a basic email address structure with a domain suffix. */
    private fun validateEmail(value: String): String? = when {
        value.isBlank() -> "Email is required"
        !value.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) -> "Enter a valid email address"
        else -> null
    }

    /** Requires a non-blank password of at least eight characters. */
    private fun validatePassword(value: String): String? = when {
        value.isBlank() -> "Password is required"
        value.length < 8 -> "Password must be at least 8 characters"
        else -> null
    }
}
