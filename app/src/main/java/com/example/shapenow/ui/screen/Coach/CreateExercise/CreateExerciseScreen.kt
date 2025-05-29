package com.example.shapenow.ui.screen.Coach.CreateExercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.ui.component.DefaultButton
import com.example.shapenow.ui.component.DefaultTextField
import com.example.shapenow.ui.screen.rowdies

@Composable
fun CreateExerciseScreen(
    innerPadding: PaddingValues,
    onCreate: () -> Unit
){
    val viewModel: CreateExerciseViewmodel = viewModel()
    val saveSuccess by viewModel.saveSuccess
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            onCreate()
        }
    }

    var exerciseName by remember { mutableStateOf("") }
    var repetitions by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()
        .background(Color(0xFF1B1B2F))
        .padding(vertical = 24.dp)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // <-- Centraliza horizontalmente
        ) {

        Text(
                "Adicionar Exercício",
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                text = "Nome do exercício",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.name.value,
                onValueChange = viewModel::onNameChange,
                label = "Exercício",
                padding = 10
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                text = "Repetições",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.repetitions.value,
                onValueChange = viewModel::onRepetitionsChange,
                label = "Repetições",
                padding = 10
            )
            DefaultButton(
                modifier = Modifier.align(Alignment.End),
                text = "Salvar",
                onClick = {
                    viewModel.saveExercise()
                },
            )
        }
    }


}