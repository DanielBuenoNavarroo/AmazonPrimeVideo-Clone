package com.example.mvvmjetpackcomposelogin.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmjetpackcomposelogin.data.model.MediaType
import com.example.mvvmjetpackcomposelogin.screens.SplashScreen
import com.example.mvvmjetpackcomposelogin.screens.informacion.ui.InformationScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginViewModel
import com.example.mvvmjetpackcomposelogin.screens.pagPrincipal.PagPScreen
import com.example.mvvmjetpackcomposelogin.screens.pagPrincipal.PagPViewModel
import com.example.mvvmjetpackcomposelogin.screens.profile.ui.ProfileScreen
import com.example.mvvmjetpackcomposelogin.screens.profile.ui.ProfileViewModel
import com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui.ProfileCreationScreen
import com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui.ProfileCreationViewModel
import com.example.mvvmjetpackcomposelogin.screens.register.ui.RegisterScreen
import com.example.mvvmjetpackcomposelogin.screens.register.ui.RegisterViewModel

@Composable
fun AppNavigation() {
    // Crea un NavController para gestionar la navegación entre diferentes pantallas.
    val navController = rememberNavController()

    // Define el punto de entrada de la navegación y el NavController asociado.
    NavHost(
        navController = navController,
        // Establece la pantalla de inicio del NavController.
        startDestination = NavigationScreens.SplashScreen.route,
    ) {
        // Configura las rutas y las pantallas correspondientes.

        // Pantalla de inicio de la aplicación.
        composable(route = NavigationScreens.SplashScreen.route) {
            SplashScreen(navController)
        }

        // Pantalla de inicio de sesión.
        composable(route = NavigationScreens.LoginScreen.route) {
            LoginScreen(LoginViewModel(), navController)
        }

        // Pantalla de registro.
        composable(route = NavigationScreens.RegisterScreen.route) {
            RegisterScreen(RegisterViewModel(), navController)
        }

        // Pantalla de creación de perfil.
        composable(route = NavigationScreens.ProfileCreationScreen.route) {
            ProfileCreationScreen(ProfileCreationViewModel(), navController)
        }

        // Pantalla de perfil.
        composable(route = NavigationScreens.ProfileScreen.route){
            ProfileScreen(navController, ProfileViewModel())
        }

        // Pantalla de PagP.
        composable(route = NavigationScreens.PagPScreen.route) {
            PagPScreen(PagPViewModel(), navController)
        }

        // Pantalla de información con argumentos dinámicos.
        composable(route = "${NavigationScreens.InformationScreen.route}/{mediaType}/{movieId}") { backStackEntry ->
            // Obtiene los argumentos de la ruta.
            val mediaType = backStackEntry.arguments?.getString("mediaType")?.let {
                when (it) {
                    // Convierte el tipo de medio de la cadena de ruta a un objeto MediaType.
                    MediaType.Movie.nombre -> MediaType.Movie
                    MediaType.Serie.nombre -> MediaType.Serie
                    else -> null
                }
            }
            // Obtiene el ID de la película de la ruta.
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            // Muestra la pantalla de información con los parámetros obtenidos.
            InformationScreen(idItem = movieId ?: -1, mediaType = mediaType ?: MediaType.Movie, navController)
        }
    }
}