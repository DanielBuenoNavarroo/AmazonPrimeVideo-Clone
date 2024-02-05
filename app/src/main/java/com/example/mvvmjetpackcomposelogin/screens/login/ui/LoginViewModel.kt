package com.example.mvvmjetpackcomposelogin.screens.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _error = MutableLiveData<String>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private fun clearError() {
        _error.value = ""
    }

    private fun signInEmailPassword(home: () -> Unit) = viewModelScope.launch {
        val email = _email.value.toString()
        val password = _password.value.toString()

        Log.i("aplicacion", "$email, $password")
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("aplicacion", "logeado correctamente")
                home()
            } else {
                Log.i("aplicacion", "logeado incorrectamente: ${it.exception}")
                _errorMessage.value = "Mail o contraseña incorrectos"
                clearError()
            }
        }
    }

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    suspend fun onLoginSelected(navController: NavController) {
        _isLoading.value = true
        delay(2000)
        _isLoading.value = false
        signInEmailPassword { navController.navigate(NavigationScreens.HomeScreen.route) }
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean =
        password.length in 6..50

}