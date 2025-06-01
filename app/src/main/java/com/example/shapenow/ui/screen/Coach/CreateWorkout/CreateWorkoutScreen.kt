import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle // Ícone para adicionar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle // Ícone para já adicionado/remover
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shapenow.data.datasource.model.Exercise // Importe sua classe Exercise
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel // Importe seu ViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateWorkoutScreen(
    paddingValues: PaddingValues,
    onWorkoutCreated: () -> Unit, // Callback para quando o treino for criado com sucesso
) {
    val viewModel: CreateWorkoutViewmodel = viewModel()
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState() // Coletar estado da descrição
    val studentId by viewModel.student.collectAsState()
    val searchQuery by viewModel.search.collectAsState()
    val filteredExercises by viewModel.filteredExercises
    val addedExerciseIds by viewModel.addedExercises.collectAsState()
    val status by viewModel.status.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(status) {
        if (status.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = status,
                duration = SnackbarDuration.Short
            )
            // Considerar limpar o status no ViewModel após exibição
            // viewModel.clearStatus() // Você precisaria adicionar essa função no ViewModel
        }
    }

    LaunchedEffect(searchQuery) {
        viewModel.filterExercises(searchQuery)
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    viewModel.createWorkout {
                        onWorkoutCreated() // Navegar ou mostrar mensagem de sucesso
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Concluir")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Criar Treino",
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Título do treino") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descrição do treino") }, // Campo adicionado
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = studentId,
                onValueChange = { viewModel.onStudentIdChange(it) },
                label = { Text("Id do estudante") }, // Campo adicionado
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Adicionar Exercícios",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearch(it) },
                    label = { Text("Buscar Exercício") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (filteredExercises.isEmpty() && searchQuery.isNotBlank()) {
                Text(
                    "Nenhum exercício encontrado para \"$searchQuery\"",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else if (viewModel.addedExercises.value.isEmpty()) { // Acessando via allExercises (supondo que seja pública)
                // Se allExercises for private, você pode adicionar um StateFlow no VM para isEmpty
                Text(
                    "Nenhum exercício disponível para seleção.",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }


            LazyColumn(
                modifier = Modifier.weight(1f) // Ocupa o espaço restante
            ) {
                items(filteredExercises, key = { exercise -> exercise.id!! }) { exercise ->
                    ExerciseListItem(
                        exercise = exercise,
                        isAdded = addedExerciseIds.contains(exercise.id),
                        onToggleExercise = {
                            if (addedExerciseIds.contains(exercise.id)) {
                                viewModel.deleteExercise(exercise)
                            } else {
                                viewModel.addExercise(exercise)
                            }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ExerciseListItem(
    exercise: Exercise,
    isAdded: Boolean,
    onToggleExercise: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(exercise.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            exercise.repetitions.let{
                Text(
                    "• ${exercise.repetitions}", // Usando o campo 'repetitions'
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            exercise.obs?.let {
                if (!it.isBlank()) {
                    Text(
                        "• ${exercise.obs}", // Usando o campo 'obs'
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
        IconButton(onClick = onToggleExercise) {
            Icon(
                imageVector = if (isAdded) Icons.Filled.CheckCircle else Icons.Filled.AddCircle,
                contentDescription = if (isAdded) "Remover Exercício" else "Adicionar Exercício",
                tint = if (isAdded) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }
}