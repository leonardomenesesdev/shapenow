package com.example.shapenow.ui.screen.Coach

import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
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
import com.example.shapenow.ui.component.BottomBarActionItem
import com.example.shapenow.ui.component.StudentListItem
import com.example.shapenow.ui.component.WorkoutItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@Composable
fun HomeCoach(
    viewModel: HomeCoachViewModel,
    coachId: String,
    navController: NavController,
    onCreateExercise: () -> Unit,
    onCreateWorkout: () -> Unit,
    onWorkoutClick: (workoutId: String) -> Unit
) {
    val workouts by viewModel.workouts.collectAsState()
    val coach by viewModel.coach.collectAsState()
    val searchQuery by viewModel.search.collectAsState()
    val filteredStudents by viewModel.filteredStudents.collectAsState()
    LaunchedEffect(coachId) {
        viewModel.loadCoach(coachId)
        viewModel.loadAllStudents()
    }

    Scaffold(
        containerColor = backgColor,
        bottomBar = {
            BottomAppBar(
                containerColor = backgColor,
                contentColor = textColor1,
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgColor),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarActionItem(
                        text = "Novo Exercício",
                        icon = Icons.Default.List,
                        onClick = onCreateExercise

                    )

                    BottomBarActionItem(
                        text = "Novo treino",
                        icon = Icons.Default.Add,
                        onClick = onCreateWorkout
                    )

                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Aplica o padding do Scaffold
                .padding(innerPadding)
                .background(backgColor)
        ) {
            // Cabeçalho (Header)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(buttonColor)

                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Bem-vindo, ${coach?.name ?: "Treinador"}!",
                            color = textColor1,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    IconButton(onClick = { viewModel.performLogout(navController) }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Sair",
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Alunos Cadastrados",
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = textColor1
            )
            // Barra de Busca
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onsearchChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar por nome...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Ícone de busca")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = textColor1.copy(alpha = 0.7f),
                    focusedTrailingIconColor = actionColor1,
                    unfocusedTrailingIconColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Alunos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredStudents, key = { it.uid!! }) { student ->
                    StudentListItem(student = student) {
                        Log.i("msg", "Clicou no aluno ${student.name}")
                        navController.navigate("StudentDetailScreen/${student.uid}")
                    }
                }
            }
        }
    }
}
