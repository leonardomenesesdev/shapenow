package com.example.shapenow.ui.screen.Coach.StudentDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.StudentRepository
import com.example.shapenow.data.repository.WorkoutRepository
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.secondaryBlue // Não será mais usado diretamente para o card principal
import com.example.shapenow.ui.theme.textColor1
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.buttonColor // Certifique-se de que esta cor está definida

// ... (ViewModelFactory e ViewModel - mantive os seus)
class StudentWorkoutsViewModelFactory(
    private val studentRepository: StudentRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return StudentDetailViewmodel(
            extras.createSavedStateHandle(),
            studentRepository,
            workoutRepository
        ) as T
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailScreen(
    navController: NavController,
    viewModel: StudentDetailViewmodel = viewModel(
        factory = StudentWorkoutsViewModelFactory(StudentRepository(), WorkoutRepository())
    )
) {
    val student by viewModel.studentDetails.collectAsState()
    val workouts by viewModel.studentWorkouts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        containerColor = backgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = student?.name ?: "Carregando...",
                        color = textColor1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = textColor1
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(backgColor)
                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding para o conteúdo da coluna
        ) {
            Text(
                text = "Informações do Aluno",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                color = textColor1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            // Card de detalhes do aluno - Agora com o mesmo estilo dos treinos
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = buttonColor), // Usando buttonColor
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Adiciona sombra
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Nome: ${student?.name ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor1
                    )
                    Text(
                        text = "Email: ${student?.email ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor1
                    )
                    Text(
                        text = "Objetivo: ${student?.objetivo ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor1
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espaço entre os detalhes do aluno e a lista de treinos


            // Título para a seção de treinos
            Text(
                text = "Treinos",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                color = textColor1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )

            // Seção da lista de treinos
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (workouts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Este aluno ainda não possui treinos.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor1.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(workouts, key = { it.id }) { workout ->
                        WorkoutListItem(workout = workout) {
                            navController.navigate("WorkoutDetailsScreen/${workout.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutListItem(workout: Workout, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = buttonColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = workout.title,
                style = MaterialTheme.typography.titleMedium,
                color = textColor1
            )
            if (workout.description?.isNotBlank() == true) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = workout.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor1.copy(alpha = 0.8f)
                )
            }
        }
    }
}