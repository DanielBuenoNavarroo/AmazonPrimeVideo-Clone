package com.example.mvvmjetpackcomposelogin.screens.profile.ui

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

class ProfileViewModel : ViewModel() {

    // Screen
    private val _imageSelection = MutableLiveData<Boolean>()
    val imageSelection : LiveData<Boolean> = _imageSelection

    fun changeToImageSelection(){
        _imageSelection.value = _imageSelection.value?.not() ?: false
    }

    fun setCurrentImage(imageUrl: String) {
        _currentImage.value = imageUrl
        changeToImageSelection()
    }


    // Datos
    private val _images = MutableLiveData<List<PersonModel>>()
    val images : LiveData<List<PersonModel>> = _images

    private val _imagenInicial = MutableLiveData<String?>()
    val imagenInicial : MutableLiveData<String?> = _imagenInicial

    private val _currentImage = MutableLiveData<String?>()
    val currentImage : MutableLiveData<String?> = _currentImage

    private val _mail = MutableLiveData<String?>()
    val mail : LiveData<String?> = _mail

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val currentUserId = auth.currentUser?.uid
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("usuarios")

    private val query = collection.whereEqualTo("user_id", currentUserId)

    fun guardarDatos(imagen: String, nombre : String, principal : () -> Unit){
        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.update("imagen", imagen)
                    document.reference.update("nombre", nombre)
                }
                principal()
            }
            .addOnFailureListener { exception ->
                Log.d("errorApp", "Error al obtener documentos: ", exception)
            }
    }

    // Nombre
    private val _name = MutableLiveData<String?>()
    val name: MutableLiveData<String?> = _name

    private val _currenName = MutableLiveData<String?>()
    val currentName: MutableLiveData<String?> = _currenName
    fun onNameChanged(name: String){
        _name.value = name
    }

    init {
        viewModelScope.launch {
            _images.value = RetrofitClient.movieService.getPersonsPopular(apiKey = ApiKey.key).results
        }

        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.exists()) {
                        val imagen = document.getString("imagen")
                        _imagenInicial.value = imagen
                        val nombre = document.getString("nombre")
                        _name.value = nombre
                        val mail = document.getString("mail")
                        _mail.value = mail
                    } else {
                        Log.d("errorApp", "No se encontró el documento")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("errorApp", "Error al obtener documentos: ", exception)
            }
    }

}