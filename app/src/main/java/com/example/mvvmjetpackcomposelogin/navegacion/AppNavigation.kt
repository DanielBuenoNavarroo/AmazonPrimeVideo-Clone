package com.example.mvvmjetpackcomposelogin.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmjetpackcomposelogin.screens.SplashScreen
import com.example.mvvmjetpackcomposelogin.screens.home.ui.HomeScreen
import com.example.mvvmjetpackcomposelogin.screens.informacion.ui.InformationScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginViewModel
import com.example.mvvmjetpackcomposelogin.screens.moviePruebas.MoviePruebasScreen
import com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui.ProfileCreationScreen
import com.example.mvvmjetpackcomposelogin.screens.register.ui.RegisterScreen
import com.example.mvvmjetpackcomposelogin.screens.register.ui.RegisterViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SplashScreen.route,
    ){
        composable(route = NavigationScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = NavigationScreens.LoginScreen.route) {
            LoginScreen(LoginViewModel(), navController)
        }
        composable(route = NavigationScreens.RegisterScreen.route) {
            RegisterScreen(RegisterViewModel(), navController)
        }
        composable(route = NavigationScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = NavigationScreens.ProfileCreationScreen.route){
            ProfileCreationScreen(navController)
        }
        composable(route = NavigationScreens.MoviePruebasScreen.route){
            MoviePruebasScreen(navController)
        }
        composable(route = "${NavigationScreens.InformationScreen.route}/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            InformationScreen(idMovie = movieId ?: -1)
        }
    }
}