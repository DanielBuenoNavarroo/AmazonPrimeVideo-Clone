package com.example.mvvmjetpackcomposelogin.screens.moviePruebas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.CONSTANTES.ApiKey
import com.example.mvvmjetpackcomposelogin.data.api.ListaGeneros
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import kotlinx.coroutines.launch

class PagPViewModel : ViewModel() {
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
            _moviesGenreTerror.value = RetrofitClient.movieService.getMoviesByGenre(apiKey = ApiKey.key, genreId = ListaGeneros.Horror.id).results
        }
    }

    fun onItemSelected(navController: NavController, id: Any){
        navController.navigate("${NavigationScreens.InformationScreen.route}/$id")
    }

}