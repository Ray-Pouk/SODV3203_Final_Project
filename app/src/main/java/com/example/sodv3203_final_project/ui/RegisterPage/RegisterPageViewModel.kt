package com.example.sodv3203_final_project.ui.RegisterPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class RegisterPageViewModel(application: Application) : AndroidViewModel(application)
 {

     private val userDao = AppDatabase.getDatabase(application).userDao()

     private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun registerUser() {
        viewModelScope.launch {
            if (_fullName.value.isNotEmpty() && _email.value.isNotEmpty() && _password.value.isNotEmpty()) {
                try {
                    val existingUser = userDao.getUserByEmail(_email.value)
                    if (existingUser != null) {
                        _registerState.value = RegisterState.Error("Email is already in use.")
                        return@launch
                    }

                    val user = User(
                        fullName = _fullName.value,
                        email = _email.value,
                        password = _password.value,
                        createdAt = System.currentTimeMillis()
                    )

                    userDao.insertUser(user)
                    _registerState.value = RegisterState.Success("User registered successfully!")
                } catch (e: Exception) {
                    _registerState.value = RegisterState.Error("Registration failed: ${e.message}")
                }
            } else {
                _registerState.value = RegisterState.Error("All fields are required.")
            }
        }
    }

    fun onFullNameChanged(name: String) {
        _fullName.value = name
    }

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        data class Success(val message: String) : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}
