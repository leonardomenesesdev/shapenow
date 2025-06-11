package com.example.shapenow.ui.screen.Coach.EditWorkout


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

// Factory para o EditWorkoutViewModel
class EditWorkoutViewModelFactory(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return EditWorkoutViewModel(
            extras.createSavedStateHandle(),
            workoutRepository,
            exerciseRepository
        ) as T
    }
}

@Composable
fun EditWorkoutScreen(
    workoutId: String, // Recebido da navegação, usado pelo ViewModel
    onWorkoutUpdated: () -> Unit
) {
    val viewModel: EditWorkoutViewModel = viewModel(
        factory = EditWorkoutViewModelFactory(
            WorkoutRepository(), // Idealmente injetado
            ExerciseRepository()   // Idealmente injetado
        )
    )
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White.copy(alpha = 0.8f), // Cor do texto quando não focado
        disabledTextColor = Color.Gray, // Cor do texto quando desabilitado
        cursorColor = actionColor1, // Cor do cursor
        focusedBorderColor = actionColor1, // Cor da borda quando focado
        unfocusedBorderColor = Color.Gray, // Cor da borda quando não focado
        focusedLabelColor = actionColor1, // Cor do label quando focado
        unfocusedLabelColor = textColor1.copy(alpha = 0.7f), // Cor do label quando não focado
        // Você pode adicionar errorBorderColor, errorTextColor, etc., se necessário
    )

    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val currentExerciseIds by viewModel.currentExercisesId.collectAsState()
    val allExercises by viewModel.allExercises.collectAsState()
    val statusMessage by viewModel.status.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Encontra os objetos Exercise completos para os IDs no treino atual
    val currentExercisesInWorkout = remember(currentExerciseIds, allExercises) {
        allExercises.filter { it.id in currentExerciseIds }
    }

    // Lista de exercícios que podem ser adicionados (todos - os que já estão no treino)
    val exercisesAvailableToAdd = remember(currentExerciseIds, allExercises) {
        allExercises.filter { it.id !in currentExerciseIds }
    }

    LaunchedEffect(statusMessage) {
        if (statusMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(statusMessage)
            viewModel.clearStatus()
        }
    }

    Scaffold(
        containerColor = backgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.save(onSuccess = onWorkoutUpdated)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Salvar Alterações", color = textColor1)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Editar Treino",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = rowdies,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.onTitle(it) },
                label = { Text("Título do Treino") },
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.onDescription(it) },
                label = { Text("Descrição do Treino") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = customTextFieldColors
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            // Usando abas (Tabs) para separar as listas
            var tabIndex by remember { mutableStateOf(0) }
            val tabs = listOf("Exercícios no Treino", "Adicionar Exercícios")

            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = buttonColor,
                contentColor = textColor1
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }

            // Conteúdo que muda com a aba
            when (tabIndex) {
                0 -> CurrentExercisesList(
                    exercises = currentExercisesInWorkout,
                    onRemoveClick = { viewModel.removeExercise(it) }
                )

                1 -> AvailableExercisesList(
                    exercises = exercisesAvailableToAdd,
                    onAddClick = { viewModel.addNewExercise(it) }

                )
            }
        }
    }
}

// Componente para a lista de exercícios atuais
@Composable
fun ColumnScope.CurrentExercisesList(exercises: List<Exercise>, onRemoveClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.weight(1f)) {
        items(exercises, key = { it.id }) { exercise ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(exercise.name, modifier = Modifier.weight(1f), color = textColor1)
                IconButton(onClick = { onRemoveClick(exercise.id) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remover Exercício",
                        tint = Color.Red
                    )
                }
            }
            Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 0.8.dp)

        }

    }
}

// Componente para a lista de exercícios disponíveis para adicionar
@Composable
fun ColumnScope.AvailableExercisesList(exercises: List<Exercise>, onAddClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.weight(1f)) {
        items(exercises, key = { it.id }) { exercise ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(exercise.name, modifier = Modifier.weight(1f), color = textColor1)
                IconButton(onClick = { onAddClick(exercise.id) }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Adicionar Exercício",
                        tint = actionColor1
                    )
                }
            }
            Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 0.8.dp)
        }
    }
}