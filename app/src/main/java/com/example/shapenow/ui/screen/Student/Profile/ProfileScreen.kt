package com.example.shapenow.ui.screen.Student.Profile

import ImcStatus
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@Composable
fun ProfileScreen(navController: NavController ){
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
            // Recarrega os dados toda vez que a tela é resumida (volta a ficar visível)
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadStudent()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        // Remove o observador quando o Composable é descartado
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Scaffold (
        containerColor = backgColor,
        bottomBar = {
            BottomAppBar(
                containerColor = secondaryBlue,
                contentColor = textColor1,
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarActionItem(
                        text = "Início",
                        icon = Icons.Default.Home,
                        onClick = {navController.navigate("HomeAluno/${auth.currentUser?.uid}")}
                    )

                    BottomBarActionItem(
                        text = "Perfil",
                        icon = Icons.Default.AssignmentInd,
                        onClick = {}
                    )

                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .background(backgColor)
                .padding(16.dp)
                .padding(innerPadding)

        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Perfil",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleLarge,
                color = textColor1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp))
                    .background(secondaryBlue, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .padding(20.dp),
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nome: $nome",
                        color = textColor1,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = "E-mail: $mail",
                        color = textColor1,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Status de saúde",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleLarge,
                color = textColor1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .background(secondaryBlue, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Objetivo: $objetivo",
                        color = textColor1,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .background(secondaryBlue, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .padding(16.dp)

                ) {
                    Text(
                        text = "Peso: $peso kg",
                        color = textColor1,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .background(secondaryBlue, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Altura: $altura m",
                        color = textColor1,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .background(secondaryBlue, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    //TODO AJUSTAR A EXIBICAO DO STATUS DO IMC
                    Row() {
                        Text(
                            text = "IMC: ${"%.2f".format(imc.toFloatOrNull() ?: 0.0f)}",
                            color = textColor1,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        ImcStatus(imc = imc.toFloatOrNull() ?: 0.0f)
                    }
                }
            }
            Button(onClick = { navController.navigate("EditProfileScreen") }) {
                Text(text = "Editar Perfil")

            }

        }

    }
}