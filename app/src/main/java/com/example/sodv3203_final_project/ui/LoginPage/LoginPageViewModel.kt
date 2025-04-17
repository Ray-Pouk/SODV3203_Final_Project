package com.example.sodv3203_final_project.ui.LoginPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.User
import com.example.sodv3203_final_project.Data.UserDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginPageViewModel(private val userDao: UserDao) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _loginMessage = MutableStateFlow("")
    val loginMessage: StateFlow<String> = _loginMessage

    init {
        viewModelScope.launch {
            userDao.insertDummyUser()
        }
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick() {

        viewModelScope.launch {
            val user = userDao.getUserByEmail(_email.value)

            if (user != null && user.password == _password.value) {
                // If the user is found and the passwords match, the login is successful
                _loginSuccess.value = true
                _loginMessage.value = "Login successful!"
            } else {
                // If no matching user is found or the passwords don't match, show an error message
                Log.d("LoginViewModel", "Invalid email or password")
                _loginSuccess.value = false
                _loginMessage.value = "Invalid email or password."
            }
        }
    }
}
