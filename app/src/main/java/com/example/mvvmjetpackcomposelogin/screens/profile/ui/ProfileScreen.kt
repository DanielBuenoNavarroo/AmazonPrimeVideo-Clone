package com.example.mvvmjetpackcomposelogin.screens.profile.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.example.mvvmjetpackcomposelogin.ui.theme.azulPrincipal
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPrincipal
import com.example.mvvmjetpackcomposelogin.ui.theme.botonDisabled
import com.example.mvvmjetpackcomposelogin.ui.theme.noSeleccionado
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val imageSelection: Boolean by viewModel.imageSelection.observeAsState(initial = false)
    val imagenInicial : String? by viewModel.imagenInicial.observeAsState(initial = "")
    val currentImage: String? by viewModel.currentImage.observeAsState(initial = "")
    val currentName: String? by viewModel.currentName.observeAsState(initial = "")
    val nombre: String? by viewModel.name.observeAsState(initial = "")
    val mail : String? by viewModel.mail.observeAsState(initial = "")
    val imagenes by viewModel.images.observeAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(bgPrincipal)
            .padding(horizontal = 64.dp, vertical = 32.dp)
    ) {
        if (!imageSelection) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Nombre(nombre)
                Spacer(modifier = Modifier.height(8.dp))
                Mail(mail)
                Spacer(modifier = Modifier.height(16.dp))
                if (currentImage != "") {
                    currentImage?.let { Foto(viewModel, it ) }
                }else {
                    imagenInicial?.let { Foto(viewModel, it) }
                }
                Spacer(modifier = Modifier.height(16.dp))
                currentImage?.let {
                    nombre?.let { it1 -> BotonContinuar(navController, viewModel, it, it1) }
                }
                ButtonCerrarSesion(navController)
                ButtonCancelar(navController)
            }
        } else {
            LazyColumn(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(imagenes ?: emptyList()) { image ->
                    val link = "https://image.tmdb.org/t/p/w342/"
                    Image(
                        painter = rememberImagePainter(
                            data = "$link${image.profilePath}",
                            builder = {
                                crossfade(false)
                            }),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { viewModel.setCurrentImage("$link${image.profilePath}") }
                            .clip(shape = RoundedCornerShape(12.dp))
                            .width(300.dp)
                            .height(200.dp)
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Nombre(nombre: String?) {
    if (nombre != null) {
        Column (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start,){
            Text(text = "Nombre: ", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = nombre, color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight(900)
            )
        }
    }
}

@Composable
fun Mail(nombre: String?) {
    if (nombre != null) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Text(text = "Gmail: ", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = nombre, color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight(900)
            )
        }
    }
}

@Composable
fun Foto(viewModel: ProfileViewModel, imagen: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { viewModel.changeToImageSelection() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imagen == "") {
            Icon(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = null,
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp),
                tint = Color.White
            )
        } else {
            Image(
                painter = rememberImagePainter(
                    data = imagen,
                    builder = {
                        crossfade(false)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp)
                    .clip(shape = CircleShape)
                    .border(2.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Cambiar de imagen", color = Color.White)
    }
}

@Composable
fun BotonContinuar(
    navController: NavController,
    viewModel: ProfileViewModel,
    imagen: String,
    nombre: String,
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                if (imagen != "" && nombre != "") {
                    viewModel.guardarDatos(imagen, nombre) {
                        navController.navigate(
                            NavigationScreens.PagPScreen.route
                        )
                    }
                } else Toast.makeText(context, "No se han hecho cambios", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = azulPrincipal,
                disabledContainerColor = botonDisabled,
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Aplicar Cambios")
        }
    }
}

@Composable
fun ButtonCerrarSesion(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    Button(
        onClick = {
                    auth.signOut()
                    navController.navigate(NavigationScreens.LoginScreen.route)
                  },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = botonDisabled,
            disabledContainerColor = botonDisabled,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(2.dp),
    ) {
        Text(text = "Cerrar Sesion")
    }
}

@Composable
fun ButtonCancelar(navController: NavController) {
    Button(
        onClick = { navController.navigate(NavigationScreens.PagPScreen.route) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = noSeleccionado,
            disabledContainerColor = botonDisabled,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(2.dp),
    ) {
        Text(text = "Cancelar")
    }
}