package com.example.mvvmjetpackcomposelogin.screens.register.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

class RegisterViewModel {

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable : LiveData<Boolean> = _registerEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String){
        _email.value = email
        _password.value = password
        _registerEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    suspend fun onLoginSelected() {
        _isLoading.value = true
        delay(4000)
        _isLoading.value = false
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 6

}