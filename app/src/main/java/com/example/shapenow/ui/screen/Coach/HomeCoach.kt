package com.example.shapenow.ui.screen.Coach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeCoach(innerPadding: PaddingValues, viewModel: HomeCoachViewModel, coachId: String, onCreateWorkout:()->Unit){ //possivel erro em viewmodel
    val workouts by viewModel.workouts.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadWorkouts(coachId)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1B1B2F))){
        Column (modifier = Modifier.padding(16.dp)) {
            Text("Treinos passados", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            workouts.forEach { workout ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0xFF2F0C6D), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ){
                    Text(text = workout.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

        }
        FloatingActionButton(
            onClick = { onCreateWorkout() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            containerColor = Color(0xFF2F0C6D)
        ){
            Icon(Icons.Default.Add, contentDescription = "Criar Treino", tint = Color.White)
        }
    }

}
