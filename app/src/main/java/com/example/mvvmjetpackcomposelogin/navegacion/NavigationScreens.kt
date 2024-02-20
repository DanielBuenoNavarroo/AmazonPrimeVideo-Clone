package com.example.mvvmjetpackcomposelogin.navegacion

sealed class NavigationScreens(val route: String) {

    data object SplashScreen : NavigationScreens("/splash")

    data object LoginScreen : NavigationScreens("/login")

    data object RegisterScreen : NavigationScreens("/register")

    data object ProfileCreationScreen : NavigationScreens("/profile-creation")

    data object ProfileScreen : NavigationScreens("/profile")

    data object PagPScreen : NavigationScreens("/PagPScreen")

    data object InformationScreen : NavigationScreens("/information")

}