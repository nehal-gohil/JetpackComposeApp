package com.example.myapplication.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/** Renders the login form and reflects the latest state published by [viewModel].
 *
 * Field edits and login taps are forwarded to the ViewModel; validation and count updates
 * arrive back through its observable UI state.
 */
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    /** Current form, validation, and counter state observed from the ViewModel. */
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Login button taps: ${state.loginCount}", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = state.form.mobileNumber,
            onValueChange = viewModel::onMobileChanged,
            label = { Text("Mobile number") },
            placeholder = { Text("e.g. +919876543210") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = state.showValidation && state.validation.mobileError != null,
            supportingText = state.validation.mobileError?.takeIf { state.showValidation }?.let { error ->
                { Text(error) }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.form.email,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.showValidation && state.validation.emailError != null,
            supportingText = state.validation.emailError?.takeIf { state.showValidation }?.let { error ->
                { Text(error) }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.form.password,
            onValueChange = viewModel::onPasswordChanged,
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (state.form.password.isEmpty()) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = state.showValidation && state.validation.passwordError != null,
            supportingText = state.validation.passwordError?.takeIf { state.showValidation }?.let { error ->
                { Text(error) }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        Button(onClick = viewModel::onLoginClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
        if (state.loginSuccess) {
            Spacer(Modifier.height(12.dp))
            Text("All fields are valid.", color = MaterialTheme.colorScheme.primary)
        }
    }
}
