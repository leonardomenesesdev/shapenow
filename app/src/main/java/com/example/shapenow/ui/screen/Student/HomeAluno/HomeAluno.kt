package com.example.shapenow.ui.screen.Student.HomeAluno

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.R
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.ui.theme.textColor1
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeAluno(innerPadding: PaddingValues, navController: NavController, studentId: String) {
    val backgroundColor = Color(0xFF1B1B2F)
    val headerColor = Color(0xFF2F0C6D)
    val cardColor = Color(0xFF512DA8)
    val lastWorkoutCardColor = Color(0xFF4A148C) // Um roxo mais escuro para destaque
    val textColor = Color.White

    val viewmodel: HomeAlunoViewmodel = viewModel()
    val workouts by viewmodel.workouts.collectAsState()
    val user by viewmodel.user.collectAsState()
    val lastCompletedWorkout by viewmodel.lastCompletedWorkout.collectAsState()

    LaunchedEffect(studentId) { // Use studentId como key para recarregar se mudar
        viewmodel.loadWorkouts(studentId)
        viewmodel.loadUser(studentId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        // Header Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .background(headerColor, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.bg_photo),
                    contentDescription = "User",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Bem-vindo, ${user?.name ?: "..."}!",
                    color = textColor,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SEÇÃO DO ÚLTIMO TREINO FEITO
        lastCompletedWorkout?.let { lastWorkout ->
            Text(
                text = "Último Treino Feito",
                style = MaterialTheme.typography.titleLarge,
                color = textColor1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LastWorkoutCard(
                navController = navController,
                lastWorkout = lastWorkout,
                completionDate = formatTimestamp(user?.lastWorkout?.completedAt),
                cardColor = lastWorkoutCardColor
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(
            text = "Meus Treinos",

            style = MaterialTheme.typography.titleLarge,
            color = textColor1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Lista de treinos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(workouts) { treino ->
                WorkoutCard(
                    navController = navController,
                    treino = treino,
                    cardColor = cardColor,
                    highlightColor = textColor
                )
            }
        }
    }
}

@Composable
fun WorkoutCard(
    navController: NavController,
    treino: Workout,
    cardColor: Color,
    highlightColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(cardColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("workout_detail/${treino.id}")
            }
            .padding(16.dp)
    ) {
        Text(
            text = treino.title,
            color = highlightColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun LastWorkoutCard(
    navController: NavController,
    lastWorkout: Workout,
    completionDate: String,
    cardColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(cardColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("workout_detail/${lastWorkout.id}")
            }
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = lastWorkout.title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Concluído em: $completionDate",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun formatTimestamp(timestamp: Timestamp?): String {
    if (timestamp == null) return ""
    val sdf = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}

@Preview(showBackground = true)
@Composable
fun WorkoutScreenPreview() {
    val navController = rememberNavController()
    HomeAluno(PaddingValues(0.dp), navController, studentId = "YryaeYWkghYyCEQNIP5J5ojzWo52")
}