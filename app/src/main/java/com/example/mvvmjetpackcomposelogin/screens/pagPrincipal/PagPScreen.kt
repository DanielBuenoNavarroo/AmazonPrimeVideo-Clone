package com.example.mvvmjetpackcomposelogin.screens.pagPrincipal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.model.MediaType
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPagPrincipal

@Composable
fun PagPScreen(viewModel: PagPViewModel, navController: NavController) {

    val showMoviesScreen by viewModel.moviesScreen.observeAsState(true)

    Column(
        Modifier
            .fillMaxSize()
            .background(bgPagPrincipal)
    ) {
        TopApp(viewModel, navController)
        SelectBar(viewModel, showMoviesScreen)
        Spacer(modifier = Modifier.height(6.dp))
        MoviePruebasScreen(viewModel, navController, showMoviesScreen)
    }
}

@Composable
fun TopApp(viewModel: PagPViewModel, navController: NavController) {
    val img by viewModel.imgP.observeAsState(initial = "")
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
            modifier = Modifier.height(24.dp),
            tint = Color.White
        )
        if (img == ""){
            Icon(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = null,
                modifier = Modifier.size(32.dp).clickable { navController.navigate(NavigationScreens.ProfileScreen.route) },
                tint = Color.White
            )
        } else {
            Image(
                painter = rememberImagePainter(
                    data = img,
                    builder = {
                        crossfade(false)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .clickable { navController.navigate(NavigationScreens.ProfileScreen.route) }
                    .size(32.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SelectBar(viewModel: PagPViewModel, showMoviesScreen: Boolean) {
    Row(
        modifier = Modifier
            .background(bgPagPrincipal)
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "Movies",
            modifier = if (showMoviesScreen) Modifier
                .clickable { viewModel.cambiarPeliculas() }
                .clip(shape = CircleShape)
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 6.dp) else Modifier
                .clickable { viewModel.cambiarPeliculas() }
                .clip(shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            fontWeight = FontWeight.Bold,
            color = if (showMoviesScreen) Color.Black else Color.White
        )
        Text(
            text = "Series",
            modifier = if (!showMoviesScreen) Modifier
                .clickable { viewModel.cambiarSeries() }
                .clip(shape = CircleShape)
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 6.dp) else Modifier
                .clickable { viewModel.cambiarSeries() }
                .clip(shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            fontWeight = FontWeight.Bold,
            color = if (!showMoviesScreen) Color.Black else Color.White
        )
    }
}

@Composable
fun MoviePruebasScreen(
    viewModel: PagPViewModel,
    navController: NavController,
    showMoviesScreen: Boolean
) {

    val moviesTopRated by viewModel.moviesTopRated.observeAsState()
    val moviesPopular by viewModel.moviesPopular.observeAsState()
    val moviesGenreTerror by viewModel.moviesGenreTerror.observeAsState()

    val seriesTopRated by viewModel.seriesTopRated.observeAsState()
    val seriesPopular by viewModel.seriesPopular.observeAsState()
    val seriesGenreTerror by viewModel.seriesGenreMystery.observeAsState()

    var mediaType: MediaType

    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        if (showMoviesScreen) {
            mediaType = MediaType.Movie
            item {
                moviesTopRated?.let { movieList ->
                    HorizontalImageSlider(movieList = movieList) { movieId ->
                        viewModel.onItemSelected(navController, movieId, mediaType)
                    }
                }
            }
            item {
                TextoArriba(string = "Popular movies")
                moviesPopular?.let { movieList ->
                    HorizontalCarousel(movieList = movieList) { movieId ->
                        viewModel.onItemSelected(navController, movieId, mediaType)
                    }
                }
            }
            item {
                TextoArriba(string = "Horror movies")
                moviesGenreTerror?.let { movieList ->
                    HorizontalCarousel(movieList = movieList) { movieId ->
                        viewModel.onItemSelected(navController, movieId, mediaType)
                    }
                }
            }
        } else {
            viewModel.loadSeries()
            mediaType = MediaType.Serie
            item {
                seriesTopRated?.let { movieList ->
                    HorizontalImageSlider(movieList = movieList) { movieId ->
                        viewModel.onItemSelected(navController, movieId, mediaType)
                    }
                }
            }
            item {
                TextoArriba(string = "Popular TvShows")
                seriesPopular?.let { movieList ->
                    HorizontalCarousel(movieList = movieList) { movieId ->
                        viewModel.onItemSelected(navController, movieId, mediaType)
                    }
                }
            }
            item {
                TextoArriba(string = "Mystery TvShows")
                seriesGenreTerror?.let { movieList ->
                    HorizontalCarousel(movieList = movieList) { movieId ->
                        viewModel.onItemSelected(navController, movieId, mediaType)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalImageSlider(movieList: List<MoviesModel>, onItemSelected: (Int) -> Unit) {
    val npeliculas = 10
    val peliculas = movieList.take(npeliculas)

    val pagerState = rememberPagerState(
        initialPage = 1000,
        initialPageOffsetFraction = 0f,
        pageCount = { 2000 }
    )

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val movie = peliculas[page % npeliculas]
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
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp), horizontalArrangement = Arrangement.Center
        ) {
            peliculas.forEachIndexed { index, _ ->
                Icon(
                    painter = painterResource(id = R.drawable.baseline_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .height(10.dp)
                        .padding(horizontal = 4.dp),
                    tint = if (pagerState.currentPage % npeliculas != index) Color.Gray else Color.White
                )
            }
        }
    }
}

@Composable
fun TextoArriba(string: String) {
    Text(
        text = "$string >",
        color = Color.Yellow,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 20.dp)
    )
}

@Composable
fun HorizontalCarousel(movieList: List<MoviesModel>, onItemSelected: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 15.dp)
            .padding(bottom = 16.dp)
    ) {
        items(movieList) { movie ->
            MovieItem(movie = movie) {
                onItemSelected(movie.id)
            }
        }
    }
}

@Composable
fun MovieItem(movie: MoviesModel, onMovieSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onMovieSelected)
            .padding(horizontal = 6.dp)
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
                .clip(RoundedCornerShape(8.dp))
                .size(width = 160.dp, height = 240.dp)
                .fillMaxWidth()
                .aspectRatio(0.67f),
            contentScale = ContentScale.Crop
        )
    }
}