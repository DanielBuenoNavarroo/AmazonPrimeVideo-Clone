package com.example.mvvmjetpackcomposelogin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPrincipal
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(3500L)
        navController.navigate(NavigationScreens.LoginScreen.route)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgPrincipal)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.amazon_prime_video_logo),
            contentDescription = null
        )
    }
}