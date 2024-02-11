package com.example.mvvmjetpackcomposelogin.screens.informacion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.CONSTANTES.ApiKey
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleMovieModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient

@Composable
fun InformationScreen(idMovie : Int) {

    var movie by remember { mutableStateOf<SingleMovieModel?>(null) }

    LaunchedEffect(Unit) {
        idMovie.let {
            val pelicula = RetrofitClient.movieService.getMovieById(apiKey = ApiKey.key, movieId = it)
            movie = pelicula
        }
    }


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            MoviePoster(posterUrl = "https://image.tmdb.org/t/p/original/${movie?.posterPath}")
            Spacer(modifier = Modifier.height(16.dp))
            movie?.let { MovieTitle(title = movie!!.title, releaseDate = it.releaseDate) }
            Spacer(modifier = Modifier.height(8.dp))
            movie?.let { MovieOverview(overview = it.overview) }
            Spacer(modifier = Modifier.height(8.dp))
            MovieDetails(title = "Popularity", value = movie?.popularity.toString())
            MovieDetails(title = "Runtime", value = "${movie?.runtime} minutes")
            movie?.genres?.let {  MovieDetails(title = "Genres", value = it.joinToString(", ") { it.name }) }
            movie?.let { MovieDetails(title = "Status", value = it.status) }
            MovieDetails(title = "Vote Average", value = movie?.voteAverage.toString())
            MovieDetails(title = "Vote Count", value = movie?.voteCount.toString())
        }
    }
}

@Composable
fun MoviePoster(posterUrl: String) {
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(320.dp)
    Image(
        painter = rememberImagePainter(data = posterUrl),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieTitle(title: String, releaseDate: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = title, fontSize = 24.sp)
        Text(text = "Release Date: $releaseDate", fontSize = 16.sp)
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Overview:", fontSize = 20.sp)
        Text(text = overview, fontSize = 16.sp)
    }
}

@Composable
fun MovieDetails(title: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "$title: $value", fontSize = 16.sp)
    }
}