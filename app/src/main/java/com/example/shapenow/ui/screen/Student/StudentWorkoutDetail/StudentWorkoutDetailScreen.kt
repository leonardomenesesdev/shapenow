package com.example.shapenow.ui.screen.Student.WorkoutDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.data.datasource.model.Exercise

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

    // Carrega os dados quando a tela é iniciada
    LaunchedEffect(key1 = workoutId) {
        viewModel.loadWorkoutDetails(workoutId)
    }

    val backgroundColor = Color(0xFF1B1B2F)
    val cardColor = Color(0xFF2F0C6D)
    val textColor = Color.White
    val buttonColor = Color(0xFF512DA8)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(workout?.title ?: "Carregando Treino...", color = textColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
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
                            cardColor = cardColor,
                            textColor = textColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        // TODO: Adicionar lógica para salvar o progresso do treino
                        // Ex: Marcar o treino como concluído, salvar data, etc.
                        navController.popBackStack() // Volta para a tela anterior
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("CONCLUIR TREINO", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ExerciseDetailCard(
    exercise: Exercise,
    seriesChecked: List<Boolean>,
    onSeriesCheckedChange: (Int, Boolean) -> Unit,
    cardColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(cardColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = exercise.name,
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Repetições: ${exercise.repetitions.substringAfter('x').trim()}", // Exibe apenas as reps "12" de "3x12"
                color = textColor.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Carga: ${exercise.weight}",
                color = textColor.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Descanso: ${exercise.rest}",
                color = textColor.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Linha com os Checkboxes para cada série
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                seriesChecked.forEachIndexed { index, isChecked ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { newCheckedState ->
                                onSeriesCheckedChange(index, newCheckedState)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Green,
                                uncheckedColor = textColor,
                                checkmarkColor = cardColor
                            )
                        )
                        Text(
                            text = "${index + 1}ª",
                            color = textColor,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}