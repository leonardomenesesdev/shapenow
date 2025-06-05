package com.example.shapenow.ui.screen.Coach.WorkoutDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.shapenow.ui.component.ExerciseItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor

@Composable
fun WorkoutDetailScreen(
    innerPadding: PaddingValues,
    viewModel: WorkoutDetailViewmodel,
    workoutId: String,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(workoutId) {
        viewModel.loadExercises(workoutId = workoutId)
    }
    val exercise by viewModel.exercises.collectAsState() // Renomeado para 'exercises'
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
            Spacer(modifier = Modifier.height(16.dp))
            //TODO IMPLEMENTAR TELA DE EDIÇÃO DE EXERCICIO AO CLICAR EM UMA BARRA

            exercise.forEach { exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onClick = {
                        // Chamar a função de callback passando o ID do workout
                    },
                    modifier = Modifier.padding(bottom = 12.dp) // Adiciona espaçamento inferior
                )
            }
        }

        // botao pra deletar o treino
        FloatingActionButton(
            onClick = { viewModel.deleteWorkout(workoutId) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFFA52A2A)
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Deletar Treino", tint = Color.White)
        }

        //TODO DIRECIONAR PARA UMA TELA AssociateWorkoutScreen
        FloatingActionButton(
            onClick = { },
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