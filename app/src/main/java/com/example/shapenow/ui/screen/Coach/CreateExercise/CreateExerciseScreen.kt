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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shapenow.ui.component.DefaultButton2
import com.example.shapenow.ui.component.DefaultTextField
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.textColor1

@Composable
fun CreateExerciseScreen(
    innerPadding: PaddingValues,
    onCreate: () -> Unit
) {
    val viewModel: CreateExerciseViewmodel = viewModel()
    val saveSuccess by viewModel.saveSuccess
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            onCreate()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgColor)
            .padding(vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // <-- Centraliza horizontalmente
        ) {

            Text(
                "Criar Exercício",
                fontWeight = FontWeight.Bold,
                fontFamily = rowdies,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = textColor1
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = "Nome do exercício",
                color = textColor1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.name.value,
                onValueChange = viewModel::onNameChange,
                label = "Exercício",
                padding = 10,


                )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = "Carga",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.weight.value,
                onValueChange = viewModel::onWeightChange,
                label = "Carga",
                padding = 10
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = "Descanso",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.rest.value,
                onValueChange = viewModel::onRestChange,
                label = "Descanso",
                padding = 10
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = "Observação",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.obs.value,
                onValueChange = viewModel::onObsChange,
                label = "Observação",
                padding = 10
            )
            DefaultButton2(
                modifier = Modifier.align(Alignment.End),
                text = "Salvar",
                onClick = {
                    viewModel.saveExercise()
                },
            )
        }
    }


}