package com.example.sodv3203_final_project.ui.LoadingPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoadingPageViewModel : ViewModel() {
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    init {
        viewModelScope.launch {
            repeat(100) {
                delay(30)
                _progress.value += 0.01f
            }
        }
    }
}
