package com.example.shapenow.ui.screen.Coach

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.shapenow.ui.component.DefaultButton // Supondo que este seja o botão que você quer
import com.example.shapenow.ui.component.DefaultButton2
// import com.example.shapenow.ui.component.DefaultButton2 // Removido se DefaultButton for o correto
import com.example.shapenow.ui.component.WorkoutItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
// import com.example.shapenow.ui.theme.buttonColor // Se DefaultButton já define sua cor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@Composable
fun HomeCoach(
    innerPadding: PaddingValues, // Padding do Scaffold da MainActivity, se houver
    viewModel: HomeCoachViewModel,
    coachId: String,
    navController: NavController,
    onCreateExercise: () -> Unit,
    onCreateWorkout: () -> Unit,
    onWorkoutClick: (workoutId: String) -> Unit
) {
    val workouts by viewModel.workouts.collectAsState()
    val coach by viewModel.coach.collectAsState()

    LaunchedEffect(coachId) { // Chave corrigida para coachId
        viewModel.loadWorkouts(coachId)
        viewModel.loadCoach(coachId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgColor)
            .padding(innerPadding) // Aplica o padding externo aqui, se vier de um Scaffold
        // Removido o .padding(16.dp) daqui para dar mais controle aos filhos
    ) {

        // Cabeçalho
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp) // Padding específico do header
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .background(secondaryBlue, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Bem-vindo, ${coach?.name ?: "Treinador"}!",
                        // Removido fontWeight e fontSize daqui para usar o do style
                        color = textColor1,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold) // Adiciona Bold ao estilo
                    )
                }
                IconButton(onClick = {
                    Log.d("HomeCoach", "Botão de Logout foi clicado!")
                    viewModel.performLogout(navController)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, // Ícone semanticamente correto
                        contentDescription = "Sair",
                        tint = Color.White
                    )
                }
            }
        }

        // Lista de Treinos
        LazyColumn(
            // <<< MUDANÇA PRINCIPAL AQUI >>>
            modifier = Modifier
                .weight(1f) // Faz a LazyColumn ocupar o espaço restante
                .fillMaxWidth() // Garante que ocupe a largura
                .background(backgColor)
                .padding(horizontal = 16.dp), // Padding horizontal para a lista
            // <<< Padding de contentPAdding ajustado, principalmente o bottom >>>
            contentPadding = PaddingValues(top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Treinos Passados",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor1,
                    textAlign = TextAlign.Center,
                    fontFamily = rowdies,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp) // Espaço após o título da lista
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

        // Barra de Botões Inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgColor.copy(alpha = 0.95f)) // Leve transparência ou cor sólida
                .padding(horizontal = 16.dp, vertical = 20.dp), // Padding para a barra de botões
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Espaço entre os botões
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Supondo que DefaultButton seja o componente de botão que você quer usar.
            // Se você tinha um DefaultButton2 específico, pode mantê-lo.
            DefaultButton2(
                text = "Criar Exercício",
                modifier = Modifier.weight(1f),
                onClick = { onCreateExercise() }
            )

            DefaultButton2(
                text = "Criar Treino",
                modifier = Modifier.weight(1f),
                onClick = { onCreateWorkout() }
            )
        }
    }
}