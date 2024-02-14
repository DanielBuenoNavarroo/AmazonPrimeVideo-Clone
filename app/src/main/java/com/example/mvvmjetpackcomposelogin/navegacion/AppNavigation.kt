package com.example.mvvmjetpackcomposelogin.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmjetpackcomposelogin.data.MediaType
import com.example.mvvmjetpackcomposelogin.screens.SplashScreen
import com.example.mvvmjetpackcomposelogin.screens.home.ui.HomeScreen
import com.example.mvvmjetpackcomposelogin.screens.informacion.ui.InformationScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginViewModel
import com.example.mvvmjetpackcomposelogin.screens.moviePruebas.PagPScreen
import com.example.mvvmjetpackcomposelogin.screens.moviePruebas.PagPViewModel
import com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui.ProfileCreationScreen
import com.example.mvvmjetpackcomposelogin.screens.register.ui.RegisterScreen
import com.example.mvvmjetpackcomposelogin.screens.register.ui.RegisterViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SplashScreen.route,
    ) {
        composable(route = NavigationScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = NavigationScreens.LoginScreen.route) {
            LoginScreen(LoginViewModel(), navController)
        }
        composable(route = NavigationScreens.RegisterScreen.route) {
            RegisterScreen(RegisterViewModel(), navController)
        }
        composable(route = NavigationScreens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = NavigationScreens.ProfileCreationScreen.route) {
            ProfileCreationScreen(navController)
        }
        composable(route = NavigationScreens.PagPScreen.route) {
            PagPScreen(PagPViewModel(), navController)
        }
        composable(route = "${NavigationScreens.InformationScreen.route}/{mediaType}/{movieId}") { backStackEntry ->
            val mediaType = backStackEntry.arguments?.getString("mediaType")?.let {
                when (it) {
                    MediaType.Movie.nombre -> MediaType.Movie
                    MediaType.Serie.nombre -> MediaType.Serie
                    else -> null
                }
            }
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            InformationScreen(idItem = movieId ?: -1, mediaType = mediaType ?: MediaType.Movie)
        }
    }
}