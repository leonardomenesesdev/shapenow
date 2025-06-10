package com.example.shapenow.ui.screen.Coach.StudentDetail

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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1
import androidx.compose.runtime.getValue // <<< IMPORT MAIS IMPORTANTE
import androidx.compose.foundation.lazy.items // <<< ADICIONE ESTE IMPORT
// ...
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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = textColor1
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgColor)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (workouts.isEmpty()) {
                Text(
                    text = "Este aluno ainda não possui treinos.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor1.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
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
        colors = CardDefaults.cardColors(containerColor = secondaryBlue)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = workout.title,
                style = MaterialTheme.typography.titleMedium,
                color = textColor1
            )
            if (workout.description!!.isNotBlank()) {
                Spacer(modifier = Modifier.run { height(4.dp) })
                Text(
                    text = workout.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor1.copy(alpha = 0.8f)
                )
            }
        }
    }
}

