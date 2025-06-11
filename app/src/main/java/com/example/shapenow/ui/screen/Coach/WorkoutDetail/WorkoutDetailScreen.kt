package com.example.shapenow.ui.screen.Coach.WorkoutDetail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Importe esta função
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shapenow.ui.component.ExerciseItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1
import com.google.android.play.integrity.internal.ac

@Composable
fun WorkoutDetailScreen(
    innerPadding: PaddingValues,
    viewModel: WorkoutDetailViewmodel,
    workoutId: String,
    onNavigateBack: () -> Unit,
    navController: NavController
) {
    LaunchedEffect(workoutId) {
        viewModel.loadExercises(workoutId = workoutId)
    }
    // Renomeei a variável de 'exercise' para 'exercises' para melhor clareza,
    // pois ela representa uma lista de exercícios.
    val exercises by viewModel.exercises.collectAsState()
    val isWorkoutDeleted by viewModel.workoutDeleted.collectAsState()

    LaunchedEffect(isWorkoutDeleted) {
        if (isWorkoutDeleted) {
            onNavigateBack()
            viewModel.onDeletionHandled()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgColor)
            .padding(vertical = 24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Exercícios",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
            LazyColumn {
                items(exercises) { exercise ->
                    Spacer(modifier = Modifier.height(16.dp))
                    ExerciseItem(
                        exercise = exercise,
                        onClick = {
                            navController.navigate("edit_workout_exercise_screen/${workoutId}/${exercise.id}")
                            Log.i("TAG", "WorkoutDetailScreen: ${exercise.id}")
                        },
                        modifier = Modifier.padding(bottom = 12.dp),
                        cardColor = buttonColor,
                        textColor = textColor1
                    )
                }
            }
        }

        // botao pra deletar o treino
        FloatingActionButton(
            onClick = { viewModel.deleteWorkout(workoutId) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFFA52A2A) // Cor marrom avermelhada para "Delete"
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Deletar Treino", tint = Color.White)
        }

        // TODO DIRECIONAR PARA UMA TELA AssociateWorkoutScreen
        FloatingActionButton(
            onClick = {
                navController.navigate("edit_workout_screen/$workoutId")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 10.dp)
                .height(70.dp)
                .width(200.dp),
            containerColor = buttonColor
        ) {
            Text(text = "Editar Treino", color = Color.White, fontSize = 18.sp)
        }
    }
}