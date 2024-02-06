package com.example.mvvmjetpackcomposelogin.screens.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val nombre = auth.currentUser?.email

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text(text = "Bienvenido $nombre")
            Button(onClick = {
                auth.signOut()
                navController.navigate(NavigationScreens.LoginScreen.route)
            }) {
                Text(text = "LogOut")
            }
        }
    }
}