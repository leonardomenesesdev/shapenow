package com.example.shapenow.ui.screen.Student.Profile


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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditProfileAlunoScreen(
    // navController pode ser necessário para um botão de "Voltar"
) {
    val viewModel: EditProfileAlunoViewmodel = viewModel()
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // Você pode adicionar um TopAppBar com botão de voltar aqui
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Meu Perfil",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { viewModel.onPeso(it) },
                label = { Text("Peso (kg)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = altura,
                onValueChange = { viewModel.onAltura(it) },
                label = { Text("Altura (m)") },
                placeholder = { Text("Ex: 1.75") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = objetivo,
                onValueChange = { viewModel.onObjetivo(it) },
                label = { Text("Meu Objetivo") },
                placeholder = { Text("Ex: Hipertrofia, Emagrecimento...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Campo de IMC (apenas exibição)
            Text("Seu IMC (Índice de Massa Corporal)", style = MaterialTheme.typography.titleMedium)
            Text(
                text = imc,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            ImcCategoryText(imc.toFloatOrNull() ?: 0f)

            Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para baixo

            Button(
                onClick = { viewModel.save() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Salvar Perfil", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ImcCategoryText(imc: Float) {
    val (text, color) = when {
        imc < 18.5 -> "Abaixo do peso" to Color.Blue
        imc < 24.9 -> "Peso normal" to Color(0xFF008000) // Verde
        imc < 29.9 -> "Sobrepeso" to Color(0xFFFFA500) // Laranja
        else -> "Obesidade" to Color.Red
    }
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp)
    )
}