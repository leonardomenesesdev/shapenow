package com.example.shapenow.ui.screen.Student.Profile

import ImcStatus
// <<< IMPORTS NECESSÁRIOS >>>
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
// ... (seus outros imports)
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.ui.component.BottomBarActionItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.textColor1

@Composable
fun ProfileScreen(navController: NavController) {
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    val viewModel: ProfileViewmodel = viewModel()
    val peso by viewModel.peso.collectAsState()
    val altura by viewModel.altura.collectAsState()
    val objetivo by viewModel.objetivo.collectAsState()
    val imc by viewModel.imc.collectAsState()
    val nome by viewModel.nome.collectAsState()
    val mail by viewModel.mail.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadStudent()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = backgColor,
        bottomBar = {
            BottomAppBar(containerColor = buttonColor, contentColor = textColor1, tonalElevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarActionItem(text = "Início", icon = Icons.Default.Home, onClick = { navController.navigate("HomeAluno/${auth.currentUser?.uid}") })
                    BottomBarActionItem(text = "Perfil", icon = Icons.Default.AssignmentInd, onClick = {})
                }
            }
        }
    ) { innerPadding ->
        Column(
            // <<< MUDANÇA 1: TORNAR A COLUNA ROLÁVEL E AJUSTAR PADDING >>>
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Usa o padding do Scaffold para não sobrepor a barra inferior
                .padding(horizontal = 16.dp) // Adiciona padding lateral
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Perfil",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                fontSize = 32.sp,
                color = textColor1,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp))
                    .background(buttonColor, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(text = "Nome: $nome", color = textColor1, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp)) // Adicionado para espaçamento
                    Text(text = "E-mail: $mail", color = textColor1, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Status de saúde",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                fontSize = 32.sp,
                color = textColor1,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            // Removida a Column aninhada desnecessária
            Box(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)).background(buttonColor, shape = RoundedCornerShape(12.dp)).clip(RoundedCornerShape(12.dp)).padding(16.dp)
            ) {
                Text(text = "Objetivo: $objetivo", color = textColor1, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)).background(buttonColor, shape = RoundedCornerShape(12.dp)).clip(RoundedCornerShape(12.dp)).padding(16.dp)
            ) {
                Text(text = "Peso: $peso kg", color = textColor1, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)).background(buttonColor, shape = RoundedCornerShape(12.dp)).clip(RoundedCornerShape(12.dp)).padding(16.dp)
            ) {
                Text(text = "Altura: $altura m", color = textColor1, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)).background(buttonColor, shape = RoundedCornerShape(12.dp)).clip(RoundedCornerShape(12.dp)).padding(16.dp),
                contentAlignment = Alignment.Center // Adicionado para centralizar a Row do IMC
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) { // Adicionado CenterVertically
                    Text(text = "IMC: ${"%.1f".format(imc.toFloatOrNull() ?: 0.0f)}", color = textColor1, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.width(16.dp))
                    ImcStatus(imc = imc.toFloatOrNull() ?: 0.0f)
                }
            }

            // <<< MUDANÇA 2: USAR SPACER COM WEIGHT PARA EMPURRAR O BOTÃO PARA BAIXO >>>
            Spacer(modifier = Modifier.weight(1f))

            // Removida a Column aninhada desnecessária que envolvia o botão
            Button(
                onClick = { navController.navigate("EditProfileScreen") },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp, pressedElevation = 2.dp, hoveredElevation = 10.dp, focusedElevation = 10.dp),
                modifier = Modifier
                    .fillMaxWidth() // O botão agora pode ocupar a largura toda
                    .padding(vertical = 16.dp) // Adiciona espaçamento na base da tela
            ) {
                Text(
                    text = "Editar Perfil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}