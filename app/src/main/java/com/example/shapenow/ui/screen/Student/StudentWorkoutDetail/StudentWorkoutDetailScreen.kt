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
import com.example.shapenow.ui.component.ExerciseDetailCard

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
                        viewModel.completeWorkout(workoutId)
                        // Volta para a tela anterior
                        navController.popBackStack()
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

