package com.example.shapenow.ui.screen.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shapenow.R
import com.example.shapenow.ui.component.DefaultButton2
import com.example.shapenow.ui.component.DefaultTextField
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.buttonColor

@Composable
fun RegisterScreen(innerPadding: PaddingValues, registerViewModel: RegisterViewModel, onRegisterSuccess: () -> Unit){
    val registerState by registerViewModel.registerState.collectAsState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Para o campo de senha
    var errorMsg by remember { mutableStateOf<String?>(null) }

    // Efeito para tratar o sucesso ou erro do registro
    LaunchedEffect(registerState) {
        when(val currentState = registerState) {
            is RegisterViewModel.RegisterState.Success -> {
                onRegisterSuccess()
            }
            is RegisterViewModel.RegisterState.Error -> {
                errorMsg = currentState.message
            }
            else -> {
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
        // Overlay escuro
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f)))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp) // Padding lateral
                    .verticalScroll(rememberScrollState()), // Permite rolar
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centraliza todo o conteúdo
            ) {
                // Título SHAPENOW!
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp, fontFamily = rowdies, color = Color.White)) { append("SHAPE") }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontFamily = rowdies, fontSize = 36.sp, color = buttonColor)) { append("NOW!") }
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Crie sua conta",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de Nome
                DefaultTextField(
                    label = "Seu nome completo",
                    value = name,
                    onValueChange = { name = it },
                    padding = 0,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Email
                DefaultTextField(
                    label = "Ex: email@example.com",
                    value = email,
                    onValueChange = { email = it },
                    padding = 0,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Senha
                DefaultTextField(
                    label = "Crie uma senha",
                    value = senha,
                    onValueChange = { senha = it },
                    padding = 0,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = "Toggle password visibility")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botão Registrar
                DefaultButton2(
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa a largura para um melhor toque
                        .height(50.dp),
                    text = "Registrar",
                    onClick = {
                        registerViewModel.register(email, senha, name)
                    }
                )

                // Espaço para o indicador de Loading ou mensagem de erro
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (registerState is RegisterViewModel.RegisterState.Loading) {
                        CircularProgressIndicator(color = Color.White)
                    }
                    if (errorMsg != null) {
                        Text(
                            text = errorMsg!!,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
