package com.example.mvvmjetpackcomposelogin.screens.register.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

class RegisterViewModel {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    private val _error = MutableLiveData<String>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private fun clearError() {
        _error.value = ""
    }

    fun createUserEmailPassword(login: () -> Unit) {

        val email = _email.value.toString()
        val password = _password.value.toString()

        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("aplicacion", "logeado correctamente")
                    login()
                } else {
                    Log.i("aplicacion", "registrado incorrectamente: ${it.exception}")
                    _errorMessage.value = "Error al registrarse"
                    clearError()
                }
            }
        }
    }

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable: LiveData<Boolean> = _registerEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onRegisterChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _registerEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    suspend fun onRegisterSelected(navController: NavController) {
        _isLoading.value = true
        delay(2000)
        _isLoading.value = false
        createUserEmailPassword { navController.navigate(NavigationScreens.LoginScreen.route) }
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length >= 6

}