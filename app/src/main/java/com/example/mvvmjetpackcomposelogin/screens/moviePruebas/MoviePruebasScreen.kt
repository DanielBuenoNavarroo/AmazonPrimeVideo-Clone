package com.example.mvvmjetpackcomposelogin.screens.moviePruebas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.CONSTANTES.ApiKey
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens

@Composable
fun MoviePruebasScreen(navController: NavController) {
    var moviesTopRated by remember { mutableStateOf<List<MoviesModel>?>(null) }

    LaunchedEffect(Unit) {
        val fetchedMovies = RetrofitClient.movieService.getMoviesTopRated(
            apiKey = ApiKey.key
        ).results
        moviesTopRated = fetchedMovies
    }

    moviesTopRated?.let { movieList ->
        HorizontalCarousel(movieList = movieList, navController)
    }
}

@Composable
fun HorizontalCarousel(movieList: List<MoviesModel>, navController: NavController) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        items(movieList) { movie ->
            MovieItem(movie = movie, navController)
        }
    }
}

@Composable
fun MovieItem(movie: MoviesModel, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/original/${movie.backdropPath}",
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    navController.navigate("${NavigationScreens.InformationScreen.route}/${movie.id}")
                }
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