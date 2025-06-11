package com.example.shapenow.ui.screen.Student.WorkoutDetail

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.ui.component.ExerciseDetailCard
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentWorkoutDetailScreen(
    navController: NavController,
    workoutId: String
) {
    val viewModel: StudentWorkoutDetailViewmodel = viewModel()
    val workout by viewModel.workout.collectAsState()
    val exercises by viewModel.exercises.collectAsState()
    val seriesState by viewModel.seriesState.collectAsState()

    // Estado para controlar a visibilidade do AlertDialog
    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = workoutId) {
        viewModel.loadWorkoutDetails(workoutId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        workout?.title ?: "Carregando Treino...",
                        color = textColor1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = rowdies,
                        fontSize = 32.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgColor)
            )
        },
        containerColor = backgColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (exercises.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(exercises, key = { it.id }) { exercise ->
                        ExerciseDetailCard(
                            exercise = exercise,
                            seriesChecked = seriesState[exercise.id] ?: emptyList(),
                            onSeriesCheckedChange = { seriesIndex, isChecked ->
                                viewModel.onSeriesCheckedChange(exercise.id, seriesIndex, isChecked)
                            },
                            cardColor = buttonColor,
                            textColor = textColor1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        // Exibir o AlertDialog em vez de navegar diretamente
                        showConfirmationDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text("CONCLUIR TREINO", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // AlertDialog para confirmação
    // AlertDialog para confirmação
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                // Ação ao dispensar o diálogo (clicar fora ou botão de voltar)
                showConfirmationDialog = false
                viewModel.completeWorkout(workoutId) // Marca o treino como completo
                // Navega para HomeAluno
                navController.navigate("HomeAluno") {
                    // Opcional: Limpar a backstack até um destino específico ou a raiz
                    // popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    // ou popUpTo("tela_anterior_especifica") { inclusive = true }
                }
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Treino Concluído!", color = textColor1)
                }
            },
            text = {
                Text(
                    "Parabéns por concluir o seu treino. Você será redirecionado para a tela inicial.",
                    color = textColor1,
                    textAlign = TextAlign.Center // Centraliza o texto do corpo também, se desejado
                )
            },
            confirmButton = {
                // Para centralizar o botão, usamos um Row que preenche a largura
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ), // Adiciona um pouco de padding
                    horizontalArrangement = Arrangement.Center // Centraliza o conteúdo do Row
                ) {
                    Button(
                        onClick = {
                            showConfirmationDialog = false
                            viewModel.completeWorkout(workoutId) // Marca o treino como completo
                            // Navega para HomeAluno
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                    ) {
                        Text("OK", color = Color.White)
                    }
                }
            },
            containerColor = backgColor // Cor de fundo do AlertDialog
        )
    }
}