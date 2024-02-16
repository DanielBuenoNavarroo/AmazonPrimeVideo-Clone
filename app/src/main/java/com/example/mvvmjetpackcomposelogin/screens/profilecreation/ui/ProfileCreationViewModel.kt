package com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmjetpackcomposelogin.CONSTANTES.ApiKey
import com.example.mvvmjetpackcomposelogin.data.api.model.PersonModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ProfileCreationViewModel : ViewModel() {

    // Screen
    private val _imageSelection = MutableLiveData<Boolean>()
    val imageSelection : LiveData<Boolean> = _imageSelection

    fun changeToImageSelection(){
        _imageSelection.value = _imageSelection.value?.not() ?: false
    }


    // Imagenes
    private val _images = MutableLiveData<List<PersonModel>>()
    val images : LiveData<List<PersonModel>> = _images

    private val _currentImage = MutableLiveData<Int>()
    val currentImage : LiveData<Int>  = _currentImage

    init {
        viewModelScope.launch {
            _images.value = RetrofitClient.movieService.getPersonsPopular(apiKey = ApiKey.key).results
        }
    }

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val currentUserId = auth.currentUser?.uid
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("usuarios")

    private val query = collection.whereEqualTo("user_id", currentUserId)

    fun obtenerDatos() {
        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.exists()) {
                        val nombre = document.getString("nombre")
                        val imagen = document.getString("imagen")
                    } else {
                        Log.d("errorApp", "No se encontró el documento")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("errorApp", "Error al obtener documentos: ", exception)
            }
    }

    // Nombre
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun onNameChanged(name: String){
        _name.value = name
    }

}