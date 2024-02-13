package com.example.mvvmjetpackcomposelogin.screens.moviePruebas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel

@Composable
fun MoviePruebasScreen(viewModel: PagPViewModel, navController: NavController) {

    val moviesTopRated by viewModel.moviesTopRated.observeAsState()
    val moviesPopular by viewModel.moviesPopular.observeAsState()
    val moviesGenreTerror by viewModel.moviesGenreTerror.observeAsState()

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            moviesTopRated?.let { movieList ->
                HorizontalCarousel(movieList = movieList) { movieId ->
                    viewModel.onItemSelected(navController, movieId)
                }
            }
        }
        item {
            moviesPopular?.let { movieList ->
                HorizontalCarousel(movieList = movieList) { movieId ->
                    viewModel.onItemSelected(navController, movieId)
                }
            }
        }
        item {
            moviesGenreTerror?.let { movieList ->
                HorizontalCarousel(movieList = movieList) { movieId ->
                    viewModel.onItemSelected(navController, movieId)
                }
            }
        }
    }
}

@Composable
fun HorizontalCarousel(movieList: List<MoviesModel>, onItemSelected: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(movieList) { movie ->
            MovieItem(movie = movie) {
                onItemSelected(movie.id)
            }
        }
    }
}

@Composable
fun MovieItem(movie: MoviesModel, onItemSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onItemSelected)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}",
                builder = {
                    crossfade(false)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp, 240.dp)
                .fillMaxWidth()
                .aspectRatio(0.67f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            fontSize = 14.sp,
            maxLines = 2,
            modifier = Modifier
                .widthIn(max = 160.dp)
        )
    }
}