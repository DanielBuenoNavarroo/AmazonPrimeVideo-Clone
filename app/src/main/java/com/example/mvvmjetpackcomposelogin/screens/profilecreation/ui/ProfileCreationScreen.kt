package com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPrincipal

@Composable
fun ProfileCreationScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(bgPrincipal)
            .padding(32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Nuevo()
    }
}

@Composable
fun Nuevo() {
    Text(
        text = "Nuevo", color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight(900))
}

@Composable
fun Foto() {

}