package com.example.shapenow.ui.screen.Coach.EditWorkoutExercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavController
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.textColor1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWorkoutExerciseScreen(
    navController: NavController,
    workoutId: String,
    exerciseIdToEdit: String,
    onExerciseEdited: () -> Unit
) {
    val exerciseRepository = remember { ExerciseRepository() }
    val workoutRepository = remember { WorkoutRepository() }

    val viewModel: EditWorkoutExerciseViewModel = viewModel(
            factory = EditWorkoutExerciseViewModel.Factory(
            exerciseRepository = exerciseRepository,
            workoutRepository = workoutRepository
        )
    )

    val exerciseName by viewModel.exerciseName.collectAsState()
    val exerciseWeight by viewModel.exerciseWeight.collectAsState()
    val exerciseRepetitions by viewModel.exerciseRepetitions.collectAsState()
    val exerciseRest by viewModel.exerciseRest.collectAsState()
    val exerciseObs by viewModel.exerciseObs.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White, // Cor do texto quando o campo está focado
        unfocusedTextColor = Color.White.copy(alpha = 0.8f), // Cor do texto quando não focado
        disabledTextColor = Color.Gray, // Cor do texto quando desabilitado
        cursorColor = actionColor1, // Cor do cursor
        focusedBorderColor = actionColor1, // Cor da borda quando focado
        unfocusedBorderColor = Color.Gray, // Cor da borda quando não focado
        focusedLabelColor = actionColor1, // Cor do label quando focado
        unfocusedLabelColor = textColor1.copy(alpha = 0.7f), // Cor do label quando não focado
        // Você pode adicionar errorBorderColor, errorTextColor, etc., se necessário
    )

    LaunchedEffect(statusMessage) {
        if (statusMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = statusMessage,
                duration = SnackbarDuration.Short
            )
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        containerColor = backgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Editar Exercício",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = rowdies,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                            .padding(top = 10.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgColor)
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.saveEditedExercise {
                        onExerciseEdited()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    hoveredElevation = 10.dp,
                    focusedElevation = 10.dp
                )
            ) {
                Text("Salvar Alterações", color = textColor1, fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nome do Exercício", color = textColor1) }, // textColor1 para o label é bom
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = customTextFieldColors // <<< USANDO AS CORES PADRÃO >>>
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = exerciseWeight,
                onValueChange = { viewModel.onWeightChange(it) },
                label = { Text("Carga (ex: 10kg, Corporal)", color = textColor1) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = customTextFieldColors // <<< USANDO AS CORES PADRÃO >>>
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = exerciseRepetitions,
                onValueChange = { viewModel.onRepetitionsChange(it) },
                label = { Text("Séries e Repetições (ex: 3x12)", color = textColor1) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = customTextFieldColors // <<< USANDO AS CORES PADRÃO >>>
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = exerciseRest,
                onValueChange = { viewModel.onRestChange(it) },
                label = { Text("Descanso (ex: 60s)", color = textColor1) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = customTextFieldColors // <<< USANDO AS CORES PADRÃO >>>
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = exerciseObs,
                onValueChange = { viewModel.onObsChange(it) },
                label = { Text("Observações (opcional)", color = textColor1) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = customTextFieldColors // <<< USANDO AS CORES PADRÃO >>>
            )
        }
    }
}