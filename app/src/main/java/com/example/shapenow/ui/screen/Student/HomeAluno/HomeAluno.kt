package com.example.shapenow.ui.screen.Student.HomeAluno

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.ui.component.BottomBarActionItem
import com.example.shapenow.ui.component.LastWorkoutCard
import com.example.shapenow.ui.component.WorkoutCard
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeAluno( navController: NavController, studentId: String) {

    val viewmodel: HomeAlunoViewmodel = viewModel()
    val workouts by viewmodel.workouts.collectAsState()
    val user by viewmodel.user.collectAsState()
    val lastCompletedWorkout by viewmodel.lastCompletedWorkout.collectAsState()

    LaunchedEffect(studentId) { // Use studentId como key para recarregar se mudar
        viewmodel.loadWorkouts(studentId)
        viewmodel.loadUser(studentId)
    }
    Scaffold (
        containerColor = backgColor,
        bottomBar = {
            BottomAppBar(
                containerColor = buttonColor,
                contentColor = textColor1,
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarActionItem(
                        text = "Início",
                        icon = Icons.Default.Home,
                        onClick = {}
                    )

                    BottomBarActionItem(
                        text = "Perfil",
                        icon = Icons.Default.AssignmentInd,
                        onClick = {navController.navigate("ProfileScreen")}
                    )

                }
            }
        }
    ){innerPadding->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgColor) //removi o innerPadding, acho q n muda nada
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            // Header Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .background(buttonColor, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .background(buttonColor)
                    ) {
                        Text(
                            text = "Bem-vindo, ${user?.name ?: "Treinador"}!",
                            color = textColor1,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    IconButton(onClick = { viewmodel.performLogout(navController) }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Sair",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SEÇÃO DO ÚLTIMO TREINO FEITO
            lastCompletedWorkout?.let { lastWorkout ->
                Text(
                    text = "Último Treino Feito",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = rowdies,
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                LastWorkoutCard(
                    navController = navController,
                    lastWorkout = lastWorkout,
                    completionDate = formatTimestamp(user?.lastWorkout?.completedAt),
                    cardColor = buttonColor
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Text(
                text = "Meus Treinos",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleLarge,
                color = textColor1,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()

            )
          if (workouts.isEmpty()) {
                Text(
                    text = "Você ainda não possui treinos, entre em contato com um professor.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor1.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Lista de treinos
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(workouts) { treino ->
                        WorkoutCard(
                            navController = navController,
                            treino = treino,
                            cardColor = buttonColor,
                            highlightColor = textColor1
                        )
                    }
                }
            }


        }
    }

}


fun formatTimestamp(timestamp: Timestamp?): String {
    if (timestamp == null) return ""
    val sdf = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}


