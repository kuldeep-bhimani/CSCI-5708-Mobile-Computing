package com.macs.revitalize.presentation.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _navigateToMainComplete = MutableLiveData<Boolean>()
    val navigateToMainComplete: LiveData<Boolean>
        get() = _navigateToMainComplete

    fun onNavigateToMainComplete() {
        _navigateToMainComplete.value = false
    }
}