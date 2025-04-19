package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageViewModel
import com.example.sodv3203_final_project.Data.UserDao

class LoginPageViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel class is LoginPageViewModel
        if (modelClass.isAssignableFrom(LoginPageViewModel::class.java)) {
            // Create and return the ViewModel instance with the userDao dependency
            @Suppress("UNCHECKED_CAST")
            return LoginPageViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
