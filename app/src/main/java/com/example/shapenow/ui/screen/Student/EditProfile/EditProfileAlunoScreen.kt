package com.example.shapenow.ui.screen.Student.EditProfile

import SetProfileViewmodel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.textColor1

@Composable
fun EditProfileAlunoScreen(
    navController: NavController, // <<< Adicionado NavController
    onProfileUpdated: () -> Unit
) {
    val viewModel: SetProfileViewmodel = viewModel()
    val peso by viewModel.peso.collectAsState()
    val altura by viewModel.altura.collectAsState()
    val objetivo by viewModel.objetivo.collectAsState()
    val imc by viewModel.imc.collectAsState()
    val statusMessage by viewModel.status.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        if (statusMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = statusMessage,
                duration = SnackbarDuration.Short
            )
            viewModel.clear()
        }
    }

    Scaffold(
        containerColor = backgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // Você pode adicionar um TopAppBar com botão de voltar aqui
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(backgColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Informações adicionais",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = textColor1,
                fontFamily = rowdies,
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { viewModel.onPeso(it) },
                label = { Text("Peso (kg)") },
                placeholder = { Text("Ex: 80") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = textColor1.copy(alpha = 0.7f),
                    focusedTrailingIconColor = actionColor1,
                    unfocusedTrailingIconColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = altura,
                onValueChange = { viewModel.onAltura(it) },
                label = { Text("Altura (m)") },
                placeholder = { Text("Ex: 1.75") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = textColor1.copy(alpha = 0.7f),
                    focusedTrailingIconColor = actionColor1,
                    unfocusedTrailingIconColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = objetivo,
                onValueChange = { viewModel.onObjetivo(it) },
                label = { Text("Meu Objetivo") },
                placeholder = { Text("Ex: Hipertrofia, Emagrecimento...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = textColor1.copy(alpha = 0.7f),
                    focusedTrailingIconColor = actionColor1,
                    unfocusedTrailingIconColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(24.dp))


            Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para baixo

            Button(
                onClick = { viewModel.save(onSuccess = onProfileUpdated) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    hoveredElevation = 10.dp,
                    focusedElevation = 10.dp
                )
            ) {
                Text(
                    text = "Salvar Perfil",
                    fontSize = 16.sp,
                    fontWeight = Bold,
                    color = Color.White
                )
            }
        }
    }
}

