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
import com.example.mvvmjetpackcomposelogin.data.MediaType
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleMovieModel
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleTvShowModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient

@Composable
fun InformationScreen(idItem: Int, mediaType: MediaType) {

    var movie by remember { mutableStateOf<SingleMovieModel?>(null) }
    var show by remember { mutableStateOf<SingleTvShowModel?>(null) }

    if (mediaType == MediaType.Movie) {
        LaunchedEffect(Unit) {
            idItem.let {idMovie ->
                val res = RetrofitClient.movieService.getMovieById(apiKey = ApiKey.key, movieId = idMovie)
                movie = res
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                ItemPoster(posterUrl = "https://image.tmdb.org/t/p/original/${movie?.posterPath}")
                Spacer(modifier = Modifier.height(16.dp))
                movie?.let { ItemTitle(title = movie!!.title, releaseDate = it.releaseDate) }
                Spacer(modifier = Modifier.height(8.dp))
                movie?.let { ItemOverview(overview = it.overview) }
                Spacer(modifier = Modifier.height(8.dp))
                ItemDetails(title = "Popularity", value = movie?.popularity.toString())
                ItemDetails(title = "Runtime", value = "${movie?.runtime} minutes")
                movie?.genres?.let {
                    ItemDetails(
                        title = "Genres",
                        value = it.joinToString(", ") { it.name })
                }
                movie?.let { ItemDetails(title = "Status", value = it.status) }
                ItemDetails(title = "Vote Average", value = movie?.voteAverage.toString())
                ItemDetails(title = "Vote Count", value = movie?.voteCount.toString())
            }
        }
    } else {
        LaunchedEffect(Unit) {
            idItem.let {
                val res = RetrofitClient.movieService.getSeriesById(apiKey = ApiKey.key, tvId = it)
                show = res
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                ItemPoster(posterUrl = "https://image.tmdb.org/t/p/original/${show?.posterPath}")
                Spacer(modifier = Modifier.height(16.dp))
                show?.let { ItemTitle(title = show!!.name, releaseDate = it.firstAirDate) }
                Spacer(modifier = Modifier.height(8.dp))
                show?.let { ItemOverview(overview = it.overview) }
                Spacer(modifier = Modifier.height(8.dp))
                ItemDetails(title = "Popularity", value = show?.popularity.toString())
                ItemDetails(title = "Runtime", value = "${show?.numberOfSeasons} episodes")
                show?.genres?.let {
                    ItemDetails(
                        title = "Genres",
                        value = it.joinToString(", ") { it.name })
                }
                show?.let { ItemDetails(title = "Status", value = it.status) }
                ItemDetails(title = "Vote Average", value = show?.voteAverage.toString())
                ItemDetails(title = "Vote Count", value = show?.voteCount.toString())
            }
        }
    }
}

@Composable
fun ItemPoster(posterUrl: String) {
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
fun ItemTitle(title: String, releaseDate: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = title, fontSize = 24.sp)
        Text(text = "Release Date: $releaseDate", fontSize = 16.sp)
    }
}

@Composable
fun ItemOverview(overview: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Overview:", fontSize = 20.sp)
        Text(text = overview, fontSize = 16.sp)
    }
}

@Composable
fun ItemDetails(title: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "$title: $value", fontSize = 16.sp)
    }
}