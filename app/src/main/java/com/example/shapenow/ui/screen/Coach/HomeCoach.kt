//package com.example.shapenow.ui.screen.Coach
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.shapenow.ui.component.DefaultButton
//import com.example.shapenow.ui.component.WorkoutItem
//import com.example.shapenow.ui.screen.rowdies
//
//@Composable
//fun HomeCoach(
//    innerPadding: PaddingValues,
//    viewModel: HomeCoachViewModel,
//    coachId: String,
//    navController: NavController,
//    onCreateExercise: () -> Unit,
//    onCreateWorkout: () -> Unit,
//    onWorkoutClick: (workoutId: String) -> Unit
//) {
//    val workouts by viewModel.workouts.collectAsState()
//    val coach by viewModel.coach.collectAsState()
//
//    LaunchedEffect(coachId) {
//        viewModel.loadWorkouts(coachId)
//        viewModel.loadCoach(coachId)
//    }
//
//    // <<< MUDANÇA PRINCIPAL: Box trocado por Column >>>
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF1B1B2F))
//            .padding(innerPadding) // Usando o innerPadding do Scaffold, se houver
//    ) {
//        // HEADER COM NOME E BOTÃO DE LOGOUT
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
//                .shadow(4.dp, RoundedCornerShape(16.dp))
//                .background(Color(0xFF2F0C6D), shape = RoundedCornerShape(16.dp))
//                .clip(RoundedCornerShape(16.dp))
//                .padding(horizontal = 24.dp, vertical = 12.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(
//                        text = "Bem-vindo, Treinador!",
//                        color = Color.White.copy(alpha = 0.8f),
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                    Text(
//                        text = coach?.name ?: "...",
//                        color = Color.White,
//                        style = MaterialTheme.typography.headlineSmall,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//                IconButton(onClick = {
//                    Log.d("HomeCoach", "Botão de Logout foi clicado!")
//                    viewModel.performLogout(navController)
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Sair",
//                        tint = Color.White
//                    )
//                }
//            }
//        }
//
//        // <<< MUDANÇA 2: LazyColumn agora tem weight(1f) para ocupar o espaço restante >>>
//        LazyColumn(
//            modifier = Modifier.weight(1f),
//            // <<< MUDANÇA 3: Padding de topo removido, pois não há mais sobreposição >>>
//            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            item {
//                Text(
//                    text = "Treinos Passados",
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    textAlign = TextAlign.Center,
//                    fontFamily = rowdies,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 24.dp)
//                )
//            }
//
//            items(workouts) { workout ->
//                WorkoutItem(
//                    workout = workout,
//                    onClick = {
//                        onWorkoutClick(workout.id)
//                    }
//                )
//            }
//        }
//
//        // <<< MUDANÇA 4: A Row não precisa mais de .align, pois a Column já a posiciona no final >>>
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF1B1B2F).copy(alpha = 0.95f))
//                .padding(vertical = 20.dp, horizontal = 16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            DefaultButton(
//                text = "Criar Exercício",
//                modifier = Modifier.weight(1f),
//                onClick = { onCreateExercise() }
//            )
//            DefaultButton(
//                text = "Criar Treino",
//                modifier = Modifier.weight(1f),
//                onClick = { onCreateWorkout() }
//            )
//        }
//    }
//}
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
import com.example.shapenow.ui.component.DefaultButton
import com.example.shapenow.ui.component.DefaultButton2
import com.example.shapenow.ui.component.WorkoutItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@Composable
fun HomeCoach(
    innerPadding: PaddingValues,
    viewModel: HomeCoachViewModel,
    coachId: String,
    onCreateExercise: () -> Unit,
    onCreateWorkout: () -> Unit,
    navController: NavController,
    onWorkoutClick: (workoutId: String) -> Unit
) {
    val workouts by viewModel.workouts.collectAsState()
    val coach by viewModel.coach.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts(coachId)
        viewModel.loadCoach(coachId)

    }

    // O Box é o container principal que permite a sobreposição de elementos
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgColor)
            .padding(innerPadding)
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = textColor1,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                IconButton(onClick = {
                    Log.d("HomeCoach", "Botão de Logout foi clicado!")
                    viewModel.performLogout(navController)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Sair",
                        tint = Color.White
                    )
                }
            }
        }
        // Usamos LazyColumn para a lista rolável de treinos (melhor performance).
        // Adicionamos um padding na parte de baixo (bottom) para que o último item
        // não fique escondido atrás da nova barra de navegação.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgColor),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 100.dp),
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
                .fillMaxWidth() // Ocupa toda a largura
                .background(backgColor) // Fundo para se destacar
                .padding(  vertical = 20.dp), // Ajuste no padding
            horizontalArrangement = Arrangement.SpaceEvenly, // Cria um espaço fixo de 16.dp ENTRE os botões
            verticalAlignment = Alignment.CenterVertically
        ) {
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