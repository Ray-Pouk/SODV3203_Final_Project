package com.example.sodv3203_final_project.ui.RegisterPage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterPageViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterPageViewModel::class.java)) {
            return RegisterPageViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
