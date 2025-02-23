package com.example.mvvmjetpackcomposelogin.screens.informacion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.CONSTANTES.ApiKey
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.data.api.model.Movie
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleTvShowModel
import com.example.mvvmjetpackcomposelogin.data.api.retrofit.RetrofitClient
import com.example.mvvmjetpackcomposelogin.data.model.MediaType
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPagPrincipal

@Composable
fun InformationScreen(idItem: Int, mediaType: MediaType, navController: NavController) {
    Column(Modifier.fillMaxSize()) {
        if (mediaType == MediaType.Movie) {
            MovieInformationScreen(idItem = idItem, navController = navController)
        } else {
            TvShowInformationScreen(idItem = idItem, navController = navController)
        }
    }

}

@Composable
fun TopApp(navController: NavController) {
    Row(
        modifier = Modifier
            .background(bgPagPrincipal)
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.amazon_prime_video_logo),
            contentDescription = null,
            modifier = Modifier
                .clickable { navController.navigate(NavigationScreens.PagPScreen.route) }
                .height(24.dp),
            tint = Color.White
        )
    }
}

@Composable
fun MovieInformationScreen(idItem: Int, navController: NavController) {
    var movie by remember { mutableStateOf<Movie?>(null) }
    val uriHandler = LocalUriHandler.current

    Column(Modifier.fillMaxSize()) {
        TopApp(navController = navController)
        LaunchedEffect(Unit) {
            idItem.let { idMovie ->
                val res = RetrofitClient.movieService.getMovieByIdWithVideos(
                    apiKey = ApiKey.key,
                    movieId = idMovie
                )
                movie = res
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(bgPagPrincipal)
        ) {
            item {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original/${movie?.backdropPath}"),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            val videos = movie?.videos?.results?.filter { video ->
                                video.site == "YouTube" && video.type == "Trailer"
                            }
                            if (!videos.isNullOrEmpty()) {
                                val videoKey = videos.random().key
                                val youtubeUrl = "https://www.youtube.com/watch?v=$videoKey"
                                uriHandler.openUri(youtubeUrl)
                            }
                        }
                        .fillMaxWidth()
                        .height(320.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                movie?.let { ItemTitle(title = movie!!.title, releaseDate = it.releaseDate) }
                Spacer(modifier = Modifier.height(8.dp))
                movie?.let { it.overview?.let { it1 -> ItemOverview(overview = it1) } }
                Spacer(modifier = Modifier.height(8.dp))
                ItemDetails(title = "Popularity", value = movie?.popularity.toString())
                ItemDetails(title = "Runtime", value = "${movie?.runtime} minutes")
                movie?.genres?.let {
                    ItemDetails(
                        title = "Genres",
                        value = it.joinToString(", ") { it.name })
                }
                movie?.let { ItemDetails(title = "Status", value = it.status) }
            }
        }
    }
}

@Composable
fun TvShowInformationScreen(idItem: Int, navController: NavController) {
    var show by remember { mutableStateOf<SingleTvShowModel?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopApp(navController)

        LaunchedEffect(Unit) {
            idItem.let {
                val res = RetrofitClient.movieService.getSeriesById(
                    apiKey = ApiKey.key,
                    tvId = it
                )
                show = res
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(bgPagPrincipal)
        ) {
            item {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original/${show?.backdropPath}"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    contentScale = ContentScale.Crop
                )
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
fun ItemTitle(title: String, releaseDate: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = title, fontSize = 24.sp, color = Color.White, textDecoration = TextDecoration.Underline, maxLines = 2)
        Text(text = "Release Date: $releaseDate", fontSize = 16.sp, color = Color.White)
    }
}

@Composable
fun ItemOverview(overview: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Overview:", fontSize = 20.sp, color = Color.White)
        Text(text = overview, fontSize = 16.sp, color = Color.White)
    }
}

@Composable
fun ItemDetails(title: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "$title: $value", fontSize = 16.sp, color = Color.White)
    }
}