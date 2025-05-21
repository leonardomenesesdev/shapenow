package com.example.shapenow.ui.screen.WorkoutSelector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.R

@Composable
fun WorkoutSelectorScreen(innerPadding: PaddingValues, navController: NavController) {
    val backgroundColor = Color(0xFF1B1B2F)
    val headerColor = Color(0xFF2F0C6D)
    val cardColor = Color(0xFF512DA8)
    val textColor = Color.White
    val highlightColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        // Header Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .background(headerColor, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.bg_photo),
                    contentDescription = "User",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Bem-vindo, user!",
                    color = textColor,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de treinos
        val workouts = listOf("Peito e Tríceps", "Costas e Bíceps", "Pernas", "Ombro e Abdômen")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(workouts) { treino ->
                WorkoutCard(
                    title = treino,
                    cardColor = cardColor,
                    textColor = textColor,
                    highlightColor = highlightColor
                )
            }
        }
    }
}

@Composable
fun WorkoutCard(title: String, cardColor: Color, textColor: Color, highlightColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(cardColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(16.dp)
//            .clickable {
//                navController.navigate("exercicios/$treino")
//            }
    ) {
        Text(
            text = title,
            color = highlightColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutScreenPreview() {
    val navController = rememberNavController()
    WorkoutSelectorScreen(PaddingValues(0.dp), navController)
}
