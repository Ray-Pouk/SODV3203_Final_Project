package com.example.sodv3203_final_project.ui.LoginPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.User
import com.example.sodv3203_final_project.Data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginPageViewModel(private val userDao: UserDao) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> get() = _loginSuccess

    private val _loginMessage = MutableStateFlow("")
    val loginMessage: StateFlow<String> = _loginMessage

    private val _currentUserId = MutableStateFlow<Int?>(null)
    val currentUserId: StateFlow<Int?> get() = _currentUserId

    init {
        viewModelScope.launch(Dispatchers.IO) {
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
            val user = getUserByEmail(_email.value)

            if (user != null && user.password == _password.value) {
                _loginSuccess.value = true
                _loginMessage.value = ""
                _currentUserId.value = user.userId
            } else {
                Log.d("LoginViewModel", "Invalid email or password")
                _loginSuccess.value = false
                _loginMessage.value = "Incorrect email or password."
            }
        }
    }

    private suspend fun getUserByEmail(email: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email)
        }
    }
}
