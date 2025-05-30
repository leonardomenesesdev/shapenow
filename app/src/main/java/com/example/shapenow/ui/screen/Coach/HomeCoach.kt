package com.example.shapenow.ui.screen.Coach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.ui.component.WorkoutItem
import com.example.shapenow.ui.screen.rowdies


@Composable
fun HomeCoach(innerPadding: PaddingValues, viewModel: HomeCoachViewModel, coachId: String, onCreateExercise:()->Unit , onCreateWorkout:()->Unit,  onWorkoutClick: (workoutId: String) -> Unit){ //possivel erro em viewmodel
    val workouts by viewModel.workouts.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadWorkouts(coachId)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1B1B2F)).padding(vertical = 24.dp)){
        Column (modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Treinos Passados",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            workouts.forEach { workout ->
                // Certifique-se que seu objeto workout tem uma propriedade 'id'
                WorkoutItem(
                    workout = workout,
                    onClick = {
                        // Chamar a função de callback passando o ID do workout
                        onWorkoutClick(workout.id) // Supondo que workout.id existe
                    },
                    modifier = Modifier.padding(bottom = 12.dp) // Adiciona espaçamento inferior
                )
            }

            Button(
                onClick = {onCreateExercise()}
            ){

            }
        }
        FloatingActionButton(
            onClick = { onCreateWorkout() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            containerColor = Color(0xFF2F0C6D)
        ){
            Icon(Icons.Default.Add, contentDescription = "Criar Treino", tint = Color.White)
        }
    }

}
