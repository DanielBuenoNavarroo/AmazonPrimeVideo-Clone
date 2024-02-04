package com.example.mvvmjetpackcomposelogin.navegacion

sealed class NavigationScreens(val route: String) {

    data object LoginScreen : NavigationScreens("/login")

    data object RegisterScreen : NavigationScreens("/register")

    data object HomeScreen : NavigationScreens("/home")

}