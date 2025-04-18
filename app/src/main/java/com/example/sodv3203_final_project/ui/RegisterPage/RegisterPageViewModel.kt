package com.example.sodv3203_final_project.ui.RegisterPage

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.AppDatabase
import com.example.sodv3203_final_project.Data.UserRepository.UserRepository
import com.example.sodv3203_final_project.Data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterPageViewModel(application: Application) : AndroidViewModel(application) {

    val fullName = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    // Use MutableStateFlow and StateFlow for registerState
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    // Get UserDao from the database
    private val userDao: UserDao = AppDatabase.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    fun onFullNameChanged(newFullName: String) {
        fullName.value = newFullName
    }

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    fun registerUser() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.registerUser(fullName.value, email.value, password.value)
                }
                // Use Main context to update UI
                withContext(Dispatchers.Main) {
                    if (result) {
                        _registerState.value = RegisterState.Success("Registration Successful!")
                    } else {
                        _registerState.value = RegisterState.Error("Registration Failed!")
                    }
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("An error occurred: ${e.message}")
            }
        }
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        data class Success(val message: String) : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}
