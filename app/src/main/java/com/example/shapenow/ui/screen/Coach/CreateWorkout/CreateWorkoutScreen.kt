package com.example.shapenow.ui.screen.Coach // Ou o pacote correto da sua tela

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.textColor1
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel

@SuppressLint("StateFlowValueCalledInComposition") // Justificado pelo uso do filteredExercises como State
@Composable
fun CreateWorkoutScreen(
    paddingValues: PaddingValues, // paddingValues vindo do Scaffold da MainActivity, se houver
    onWorkoutCreated: () -> Unit,
) {
    // Use a Factory para garantir a injeção correta do SavedStateHandle no ViewModel
    val viewModel: CreateWorkoutViewmodel = viewModel(factory = CreateWorkoutViewmodel.Factory)
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    // <<< MUDANÇA: Coletar studentEmail em vez de studentId/student >>>
    val studentEmail by viewModel.studentEmail.collectAsState()
    val searchQuery by viewModel.search.collectAsState()
    val filteredExercises by viewModel.filteredExercises // Este é um State<List<Exercise>>
    val addedExerciseIds by viewModel.addedExercises.collectAsState()
    val status by viewModel.status.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(status) {
        if (status.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = status,
                duration = SnackbarDuration.Short
            )
            viewModel.clearStatus() // Limpa o status no ViewModel após ser exibido
        }
    }

    // O filtro agora é chamado dentro de onSearch no ViewModel,
    // então este LaunchedEffect pode não ser estritamente necessário se onSearch sempre atualizar.
    // Mas mantê-lo garante que o filtro seja aplicado se searchQuery mudar por outros meios.
    LaunchedEffect(searchQuery) {
        viewModel.filterExercises(searchQuery)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Adiciona o SnackbarHost aqui
        containerColor = backgColor, // Cor de fundo do Scaffold
        bottomBar = {
            Button(
                onClick = {
                    viewModel.createWorkout {
                        onWorkoutCreated()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Concluir Treino", // Texto mais descritivo
                    color = textColor1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerScaffoldPadding -> // innerScaffoldPadding do Scaffold interno
        Column(
            modifier = Modifier
                // .background(backgColor) // Já definido no Scaffold containerColor
                .padding(innerScaffoldPadding) // Usa o padding do Scaffold
                .padding(horizontal = 16.dp) // Padding adicional para o conteúdo da Column
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
                color = textColor1
            )
            Spacer(modifier = Modifier.height(24.dp)) // Aumentado o espaço

            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Título do treino", color = textColor1) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    disabledTextColor = Color.Gray,
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descrição do treino", color = textColor1) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    disabledTextColor = Color.Gray,
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            // <<< MUDANÇA: Campo para Email do Aluno >>>
            OutlinedTextField(
                value = studentEmail,
                onValueChange = { viewModel.onStudentEmailChange(it) }, // Chama a função correta
                label = {
                    Text(
                        "Email do estudante", // Label atualizado
                        color = textColor1
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true, // Email geralmente é uma linha
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    disabledTextColor = Color.Gray,
                    cursorColor = actionColor1,
                    focusedBorderColor = actionColor1,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = actionColor1,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                )
            )
            Spacer(modifier = Modifier.height(24.dp)) // Aumentado o espaço
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Adicionar Exercícios",
                color = textColor1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearch(it) },
                    label = { Text("Buscar Exercício", color = textColor1) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                        disabledTextColor = Color.Gray,
                        cursorColor = actionColor1,
                        focusedBorderColor = actionColor1,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = actionColor1,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    )
                )
                // O botão de busca foi removido no ViewModel, então não é mais necessário aqui
                // Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (filteredExercises.isEmpty() && searchQuery.isNotBlank()) {
                Text(
                    "Nenhum exercício encontrado para \"$searchQuery\"",
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = textColor1.copy(alpha = 0.7f)
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f) // Ocupa o espaço restante
            ) {
                items(filteredExercises, key = { exercise -> exercise.id }) { exercise ->
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
                    Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 0.5.dp)
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
            .clickable(onClick = onToggleExercise) // Torna a linha inteira clicável
            .padding(vertical = 12.dp, horizontal = 8.dp), // Adiciona padding horizontal
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                exercise.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor1
            )
            // Exibe repetições e observações se existirem
            val details = mutableListOf<String>()
            if (exercise.repetitions.isNotBlank()) details.add("Reps: ${exercise.repetitions}")
            if (exercise.obs!!.isNotBlank()) details.add("Obs: ${exercise.obs}")

            if (details.isNotEmpty()) {
                Text(
                    text = details.joinToString(" | "),
                    fontSize = 13.sp,
                    color = textColor1.copy(alpha = 0.8f)
                )
            }
        }
        IconButton(onClick = onToggleExercise) { // Mantém o IconButton para feedback visual claro
            Icon(
                imageVector = if (isAdded) Icons.Filled.CheckCircle else Icons.Filled.AddCircle,
                contentDescription = if (isAdded) "Remover Exercício" else "Adicionar Exercício",
                tint = if (isAdded) actionColor1 else textColor1.copy(alpha = 0.8f)
            )
        }
    }
}