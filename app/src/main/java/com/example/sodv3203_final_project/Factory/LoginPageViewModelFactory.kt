package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.ui.LoginPage.LoginPageViewModel
import com.example.sodv3203_final_project.Data.UserDao

class LoginPageViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginPageViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
