package com.example.shapenow.ui.screen.Coach.CreateWorkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel


@Composable
fun CreateWorkoutScreen(
    innerPadding: PaddingValues,
    viewModel: CreateWorkoutViewmodel,
    onWorkoutCreated: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }

    var exerciseName by remember { mutableStateOf("") }
    var repetitions by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Criar Novo Treino", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                viewModel.title = it
            },
            label = { Text("Título do treino") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                viewModel.description = it
            },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = studentId,
            onValueChange = {
                studentId = it
                viewModel.studentId = it
            },
            label = { Text("ID do aluno") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Adicionar Exercício", fontWeight = FontWeight.SemiBold)

        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Nome do exercício") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = repetitions,
            onValueChange = { repetitions = it },
            label = { Text("Repetições") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (exerciseName.isNotBlank() && repetitions.isNotBlank()) {
                    viewModel.addExercise(
                        Exercise(
                            name = exerciseName,
                            repetitions = repetitions
                        )
                    )
                    exerciseName = ""
                    repetitions = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Adicionar exercício")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Exercícios adicionados:")
        viewModel.getExercises().forEachIndexed { index, exercise ->
            Text("- ${exercise.name} (${exercise.repetitions})")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.createWorkout() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar treino")
        }

        when (uiState) {
            is CreateWorkoutViewmodel.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is CreateWorkoutViewmodel.UiState.Error -> {
                Text(
                    text = (uiState as CreateWorkoutViewmodel.UiState.Error).message,
                    color = Color.Red
                )
            }
            is CreateWorkoutViewmodel.UiState.Success -> {
                Text("Treino criado com sucesso!", color = Color.Green)
                onWorkoutCreated()
            }
            else -> {}
        }
    }
}
