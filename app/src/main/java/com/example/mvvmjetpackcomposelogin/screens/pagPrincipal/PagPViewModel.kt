package com.example.mvvmjetpackcomposelogin.screens.pagPrincipal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.CONSTANTES.ApiKey
import com.example.mvvmjetpackcomposelogin.data.MediaType
import com.example.mvvmjetpackcomposelogin.data.api.ListaGenerosMovies
import com.example.mvvmjetpackcomposelogin.data.api.ListaGenerosSeries
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PagPViewModel : ViewModel() {

    private val _moviesScreen = MutableLiveData<Boolean>()
    val moviesScreen : LiveData<Boolean> = _moviesScreen

    fun cambiarSeries(){
        _moviesScreen.value = false
    }

    fun cambiarPeliculas(){
        _moviesScreen.value = true
    }

    fun onItemSelected(navController: NavController, id: Any, mediaType: MediaType){
        navController.navigate("${NavigationScreens.InformationScreen.route}/${mediaType.nombre}/$id")
    }

    // Perfil
    private val _imgP = MutableLiveData<String>()
    val imgP : LiveData<String> = _imgP

    init {
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("usuarios")

        val query = collection.whereEqualTo("user_id", currentUserId)

        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.exists()) {
                        val nombre = document.getString("imagen")
                        _imgP.value = nombre.toString()
                    } else {
                        Log.d("errorApp", "No se encontró el documento")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("errorApp", "Error al obtener documentos: ", exception)
            }
    }

    // PELICULAS

    private val _moviesTopRated = MutableLiveData<List<MoviesModel>>()
    val moviesTopRated: LiveData<List<MoviesModel>> = _moviesTopRated

    private val _moviesPopular = MutableLiveData<List<MoviesModel>>()
    val moviesPopular: LiveData<List<MoviesModel>> = _moviesPopular

    private val _moviesGenreTerror = MutableLiveData<List<MoviesModel>>()
    val moviesGenreTerror: LiveData<List<MoviesModel>> = _moviesGenreTerror

    init {
        viewModelScope.launch {
            _moviesTopRated.value = RetrofitClient.movieService.getMoviesTopRated(ApiKey.key).results
            _moviesPopular.value = RetrofitClient.movieService.getMoviesPopular(ApiKey.key).results
            _moviesGenreTerror.value = RetrofitClient.movieService.getMoviesByGenre(apiKey = ApiKey.key, genreId = ListaGenerosMovies.Horror.id).results
        }
    }

    // SERIES

    private val _seriesTopRated = MutableLiveData<List<MoviesModel>>()
    val seriesTopRated: LiveData<List<MoviesModel>> = _seriesTopRated

    private val _seriesPopular = MutableLiveData<List<MoviesModel>>()
    val seriesPopular: LiveData<List<MoviesModel>> = _seriesPopular

    private val _seriesGenreMystery = MutableLiveData<List<MoviesModel>>()
    val seriesGenreMystery: LiveData<List<MoviesModel>> = _seriesGenreMystery
    fun loadSeries(){
        viewModelScope.launch {
            _seriesTopRated.value = RetrofitClient.movieService.getSeriesTopRated(ApiKey.key).results
            _seriesPopular.value = RetrofitClient.movieService.getSeriesPopular(ApiKey.key).results
            _seriesGenreMystery.value = RetrofitClient.movieService.getSeriesByGenre(apiKey = ApiKey.key, genreId = ListaGenerosSeries.Mystery.id).results
        }
    }

}