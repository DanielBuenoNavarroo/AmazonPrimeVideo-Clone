package com.example.mvvmjetpackcomposelogin.screens.profilecreation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.example.mvvmjetpackcomposelogin.ui.theme.ColorFieldTextContainer
import com.example.mvvmjetpackcomposelogin.ui.theme.azulPrincipal
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPrincipal
import com.example.mvvmjetpackcomposelogin.ui.theme.botonDisabled

@Composable
fun ProfileCreationScreen(viewModel: ProfileCreationViewModel, navController: NavController) {

    val imageSelection: Boolean by viewModel.imageSelection.observeAsState(initial = false)
    val nombre: String by viewModel.name.observeAsState(initial = "")
    val currentImage: Int by viewModel.currentImage.observeAsState(initial = 0)

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
                Nuevo()
                Spacer(modifier = Modifier.height(16.dp))
                Foto(viewModel)
                Spacer(modifier = Modifier.height(16.dp))
                NameField(nombre, onvalueChange = { viewModel.onNameChanged(it) })
                BotonContinuar(navController)
            }
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    Text(text = "hola", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun Nuevo() {
    Text(
        text = "Nuevo", color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight(900)
    )
}

@Composable
fun Foto(viewModel: ProfileCreationViewModel) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_account_circle_24),
        contentDescription = null,
        modifier = Modifier
            .clickable { viewModel.changeToImageSelection() }
            .width(128.dp)
            .height(128.dp),
        tint = Color.White
    )
}

@Composable
fun NameField(nombre: String, onvalueChange: (String) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        TextField(
            value = nombre,
            onValueChange = { onvalueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.White)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = ColorFieldTextContainer,
                focusedContainerColor = ColorFieldTextContainer,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(textAlign = TextAlign.Center),
            visualTransformation = VisualTransformation.None,
        )

        Text(
            text = "Introducir nombre",
            modifier = Modifier.align(Alignment.Center),
            color = if (nombre != "") Color.Transparent else Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BotonContinuar(navController: NavController){
    Column (modifier = Modifier.fillMaxWidth()){
        Button(
            onClick = { navController.navigate(NavigationScreens.PagPScreen.route) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = azulPrincipal,
                disabledContainerColor = botonDisabled,
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Continuar")
        }
    }
}

@Composable
fun MostrarImagenes() {

}