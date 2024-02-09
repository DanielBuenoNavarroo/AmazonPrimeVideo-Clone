package com.example.mvvmjetpackcomposelogin.screens.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("usuarios")
    val nombreState = remember { mutableStateOf("") }

    val query = collection.whereEqualTo("user_id", currentUserId)

    query.get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                if (document.exists()) {
                    val nombre = document.getString("nombre")
                    nombreState.value = nombre.toString()
                } else {
                    Log.d("errorApp", "No se encontró el documento")
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.d("errorApp", "Error al obtener documentos: ", exception)
        }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text(text = "Bienvenido ${nombreState.value}")
            Button(onClick = {
                auth.signOut()
                navController.navigate(NavigationScreens.LoginScreen.route)
            }) {
                Text(text = "Log Out")
            }
        }
    }
}