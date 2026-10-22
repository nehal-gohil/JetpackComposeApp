package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.presentation.login.LoginScreen
import com.example.myapplication.presentation.login.LoginViewModel
import com.example.myapplication.data.login.LoginAttemptRepository
import com.example.myapplication.domain.login.RecordLoginAttemptUseCase
import com.example.myapplication.domain.login.ValidateLoginUseCase
import com.example.myapplication.ui.theme.MyApplicationTheme

/** Android entry point that connects the app theme, dependency wiring, and login UI. */
class MainActivity : ComponentActivity() {
    /** Sets up edge-to-edge rendering and installs the Compose login screen. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(viewModel = viewModel(factory = loginViewModelFactory()))
                }
            }
        }
    }

    /** Creates the login ViewModel and provides its use cases and in-memory repository. */
    private fun loginViewModelFactory() = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        /** Builds the requested ViewModel with the app's concrete domain dependencies. */
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(LoginViewModel::class.java))
            val counter = LoginAttemptRepository()
            return LoginViewModel(
                validateLogin = ValidateLoginUseCase(),
                recordLoginAttempt = RecordLoginAttemptUseCase(counter)
            ) as T
        }
    }
}
