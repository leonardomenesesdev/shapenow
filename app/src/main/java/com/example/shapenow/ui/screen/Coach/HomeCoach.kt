package com.example.shapenow.ui.screen.Coach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.shapenow.ui.component.DefaultButton
import com.example.shapenow.ui.component.WorkoutItem
import com.example.shapenow.ui.screen.rowdies

@Composable
fun HomeCoach(
    innerPadding: PaddingValues,
    viewModel: HomeCoachViewModel,
    coachId: String,
    onCreateExercise: () -> Unit,
    onCreateWorkout: () -> Unit,
    onWorkoutClick: (workoutId: String) -> Unit
) {
    val workouts by viewModel.workouts.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadWorkouts(coachId)
    }

    // O Box é o container principal que permite a sobreposição de elementos
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1B2F))
    ) {
        // Usamos LazyColumn para a lista rolável de treinos (melhor performance).
        // Adicionamos um padding na parte de baixo (bottom) para que o último item
        // não fique escondido atrás da nova barra de navegação.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
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
            }

            items(workouts) { workout ->
                WorkoutItem(
                    workout = workout,
                    onClick = {
                        onWorkoutClick(workout.id)
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Alinha na base do Box
                .fillMaxWidth() // Ocupa toda a largura
                .background(Color(0xFF1B1B2F).copy(alpha = 0.95f)) // Fundo para se destacar
                .padding(  vertical = 20.dp), // Ajuste no padding
            horizontalArrangement = Arrangement.SpaceEvenly, // Cria um espaço fixo de 16.dp ENTRE os botões
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultButton(
                text = "Criar Exercício",
                modifier = Modifier.weight(1f),
                onClick = { onCreateExercise() }
            )

            DefaultButton(
                text = "Criar Treino",
                modifier = Modifier.weight(1f),
                onClick = { onCreateWorkout() }
            )
        }
    }
}