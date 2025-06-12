package com.example.shapenow.ui.screen.Login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shapenow.R
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.ui.component.DefaultButton2
import com.example.shapenow.ui.component.DefaultTextField
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.viewmodel.LoginViewModel

@Composable
fun LoginScreen(innerPadding: PaddingValues, navController: NavController, loginViewModel: LoginViewModel, onLoginSucess: (user: User?) -> Unit){
    val loginState by loginViewModel.loginState.collectAsState()
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        when (val currentState = loginState) {
            is LoginViewModel.LoginState.Success -> {
                currentState.user?.let { user ->
                    onLoginSucess(user)
                }
            }
            is LoginViewModel.LoginState.Error -> {
                errorMsg = currentState.message
            }
            is LoginViewModel.LoginState.Idle, is LoginViewModel.LoginState.Loading -> {
                errorMsg = null
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.bg_photo),
            contentDescription = "Foto de um homem treinando",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Overlay escuro para melhorar a legibilidade
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título SHAPENOW!
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp, fontFamily = rowdies, color = Color.White)) { append("SHAPE") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontFamily = rowdies, fontSize = 36.sp, color = buttonColor)) { append("NOW!") }
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Formulário de Login
            Text(
                text = "Acesse sua conta",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Campo de Email
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Email",
                value = email,
                onValueChange = {email = it},
                padding = 0
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Senha
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Senha",
                value = senha,
                onValueChange = {senha = it},
                padding = 0,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description, tint = Color.White.copy(alpha = 0.7f))
                    }
                }
            )

            // Texto "Registrar-se"
            Text(
                text = "Não tem uma conta? Registrar-se",
                color = Color(0xFFBBBBBB),
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { navController.navigate("RegisterScreen") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão Entrar
            DefaultButton2(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Entrar",
                onClick = {
                    if (email.isBlank() || senha.isBlank()) {
                        errorMsg = "Por favor, preencha o email e a senha."
                    } else {
                        loginViewModel.login(email, senha)
                    }
                }
            )

            // Espaço para o indicador de Loading ou mensagem de erro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                when (loginState) {
                    is LoginViewModel.LoginState.Loading -> {
                        CircularProgressIndicator(color = Color.White)
                    }
                    is LoginViewModel.LoginState.Error -> {
                        if (errorMsg != null) {
                            Text(text = errorMsg!!, color = Color.Red, textAlign = TextAlign.Center)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}