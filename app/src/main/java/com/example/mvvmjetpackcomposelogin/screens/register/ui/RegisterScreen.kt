package com.example.mvvmjetpackcomposelogin.screens.register.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.R
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.example.mvvmjetpackcomposelogin.ui.theme.TextFieldTextColor
import com.example.mvvmjetpackcomposelogin.ui.theme.azulPrincipal
import com.example.mvvmjetpackcomposelogin.ui.theme.bgPrincipal
import com.example.mvvmjetpackcomposelogin.ui.theme.botonDisabled
import com.example.mvvmjetpackcomposelogin.ui.theme.linksColor
import com.example.mvvmjetpackcomposelogin.ui.theme.noSeleccionado
import com.example.mvvmjetpackcomposelogin.ui.theme.textColor
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .background(bgPrincipal)
            .padding(16.dp)
    ) {
        Register(viewModel, navController)
    }
}

@Composable
fun Register(viewModel: RegisterViewModel, navController: NavController) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val regsterEnable: Boolean by viewModel.registerEnable.observeAsState(initial = false)
    val passwordVisibility by viewModel.passwordVisibility.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

    val errorMessage: String by viewModel.errorMessage.observeAsState(initial = "")

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        if (errorMessage != "") {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }

        Column {
            Welcome()
            Spacer(modifier = Modifier.height(16.dp))
            RegisterBox()
            Spacer(modifier = Modifier.height(16.dp))
            Mail(email) { viewModel.onRegisterChanged(it, password) }
            Spacer(modifier = Modifier.height(16.dp))
            Password(
                password = password,
                passwordVisibility = passwordVisibility,
                onTextFieldChanged = { viewModel.onRegisterChanged(email, it) },
                toglePasswordVisibility = { viewModel.togglePasswordVisibility() }
            )
            Spacer(modifier = Modifier.height(32.dp))
            RegisterButton(regsterEnable) {
                coroutineScope.launch {
                    viewModel.onRegisterSelected(navController)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TermsOfUse()
            Spacer(modifier = Modifier.height(64.dp))
            LoginBox(navController)
        }
    }
}

@Composable
fun Welcome() {
    Text(text = "Welcome", color = Color.White, fontSize = 24.sp)
}

@Composable
fun RegisterBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.twotone_circle_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Create account", fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "New to Amaclon?", fontWeight = FontWeight.Normal, color = Color.White)
        }
    }
}

@Composable
fun Mail(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = "Email"
            )
        },
        label = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.DarkGray,
            unfocusedTextColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Password(
    password: String,
    passwordVisibility: Boolean,
    onTextFieldChanged: (String) -> Unit,
    toglePasswordVisibility: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = password,
            onValueChange = { onTextFieldChanged(it) },
            placeholder = {
                Text(
                    text = "Password"
                )
            },
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            maxLines = 1,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedTextColor = TextFieldTextColor,
                unfocusedTextColor = TextFieldTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordBottom(passwordVisibility, toglePasswordVisibility)
    }
}

@Composable
fun PasswordBottom(passwordVisibility: Boolean, togglePasswordVisibility: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { togglePasswordVisibility() }
            ) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Default.CheckCircle else Icons.Default.Clear,
                    contentDescription = null,
                    tint = if (passwordVisibility) Color.Green else Color.Red,

                    )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Show password",
                    color = Color.White
                )
            }
        }
        Text(
            text = "Forgot password?",
            color = azulPrincipal,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun RegisterButton(registerEnable: Boolean, onRegisterSelected: () -> Unit) {
    Button(
        onClick = { onRegisterSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = azulPrincipal,
            disabledContainerColor = botonDisabled,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(2.dp),
        enabled = registerEnable
    ) {
        Text(text = "Continue")
    }
}

@Composable
fun TermsOfUse() {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = textColor)) {
            append("By signing in, you agree to the")
        }
        append("  ")
        withStyle(style = SpanStyle(color = linksColor)) {
            append("Prime Video Terms of Use")
        }
        append("  ")
        withStyle(style = SpanStyle(color = textColor)) {
            append("and license agreements which can be found on the Amazon website.")
        }
    })
}

@Composable
fun LoginBox(navController: NavController) {
    Box(
        modifier = Modifier
            .clickable { navController.navigate(NavigationScreens.LoginScreen.route) }
            .fillMaxWidth()
            .height(50.dp).clip(RoundedCornerShape(4.dp))
            .background(noSeleccionado)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_circle_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Sign in Already a customer?", color = Color.White)
        }
    }
}