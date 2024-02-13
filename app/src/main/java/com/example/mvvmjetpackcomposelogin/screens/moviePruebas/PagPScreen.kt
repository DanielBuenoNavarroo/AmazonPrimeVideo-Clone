package com.example.mvvmjetpackcomposelogin.screens.moviePruebas

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPagPrincipal

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PagPScreen(viewModel: PagPViewModel, navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(bgPagPrincipal)
    ) {
        TopApp()
        MoviePruebasScreen(viewModel, navController)
    }
}

@Composable
fun TopApp() {
    Row(modifier = Modifier.background(bgPagPrincipal)
        .fillMaxWidth()
        .height(64.dp).padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.amazon_prime_video_logo),
            contentDescription = null,
            modifier = Modifier.height(32.dp),
            tint = Color.White
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_account_circle_24),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
    }
}

@Composable
fun MoviePruebasScreen(viewModel: PagPViewModel, navController: NavController) {

    val moviesTopRated by viewModel.moviesTopRated.observeAsState()
    val moviesPopular by viewModel.moviesPopular.observeAsState()
    val moviesGenreTerror by viewModel.moviesGenreTerror.observeAsState()

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            moviesTopRated?.let { movieList ->
                HorizontalImageSlider(movieList = movieList) { movieId ->
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalImageSlider(movieList: List<MoviesModel>, onItemSelected: (Int) -> Unit) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { movieList.size }
    )

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val movie = movieList[page]
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}",
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onItemSelected(movie.id) }
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun HorizontalCarousel(movieList: List<MoviesModel>, onItemSelected: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
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
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w342/${movie.posterPath}",
                builder = {
                    crossfade(false)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(width = 160.dp, height = 240.dp)
                .fillMaxWidth()
                .aspectRatio(0.67f),
            contentScale = ContentScale.Crop
        )
    }
}