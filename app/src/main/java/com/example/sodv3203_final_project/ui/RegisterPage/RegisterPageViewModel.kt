package com.example.sodv3203_final_project.ui.RegisterPage

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterPageViewModel : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    // You can use a MutableStateFlow or LiveData for tracking UI state like success or error
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    // Handle the registration process (this is where database insertion would go)
    fun registerUser() {
        // Validate input
        if (_fullName.value.isNotEmpty() && _email.value.isNotEmpty() && _password.value.isNotEmpty()) {
            // Insert into database (leave blank for now)
            // e.g., database.insertUser(_fullName.value, _email.value, _password.value)

            // Simulate success
            _registerState.value = RegisterState.Success("User registered successfully!")
        } else {
            _registerState.value = RegisterState.Error("All fields are required.")
        }
    }

    // Methods to update email, password, and full name
    fun onFullNameChanged(name: String) {
        _fullName.value = name
    }

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    // Define states for registration success, error, and idle
    sealed class RegisterState {
        object Idle : RegisterState()
        data class Success(val message: String) : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}
