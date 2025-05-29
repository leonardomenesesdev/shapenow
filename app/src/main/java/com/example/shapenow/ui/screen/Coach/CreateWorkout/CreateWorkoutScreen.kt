package com.example.shapenow.ui.screen.Coach.CreateWorkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.ui.component.DefaultButton
import com.example.shapenow.ui.component.DefaultTextField
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel


@Composable
fun CreateWorkoutScreen(
    innerPadding: PaddingValues,
    viewModel: CreateWorkoutViewmodel,
    onWorkoutCreated: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is CreateWorkoutViewmodel.UiState.Success) {
            onWorkoutCreated()
        }
    }
    Box(modifier = Modifier.fillMaxSize()
        .background(Color(0xFF1B1B2F))
        .padding(vertical = 24.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
            "Criar novo treino",
            fontWeight = FontWeight.Bold,
            fontFamily = rowdies,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                    text = "Nome do treino",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                DefaultTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it },
                    label = "Nome do treino",
                    padding = 10,
                )

                DefaultTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { description = it },
                    label = "Descrição",
                    padding = 10
                )

                DefaultTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = "Id do estudante",
                    padding = 10
                )

                Spacer(modifier = Modifier.height(16.dp))
                DefaultButton(
                    modifier = Modifier.align(Alignment.End),
                    text = "Salvar",
                    onClick = {
                        viewModel.title = title
                        viewModel.description = description
                        viewModel.studentId = studentId
                       viewModel.createWorkout()
                    },
                )


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
                    // Não chama navegação aqui!
                }

                else -> {}
            }
        }
    }
}
