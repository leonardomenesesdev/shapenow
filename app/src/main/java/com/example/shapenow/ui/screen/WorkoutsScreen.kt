package com.example.shapenow.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WorkoutScreen(innerPadding: PaddingValues, navController: NavController) {
    var text by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2F0C6D))


    ) {

        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = "Selecione seu Treino",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(modifier = Modifier.padding(top = 15.dp)) {

            LeftArrowButton {}

            Text(
                modifier = Modifier.padding(10.dp),
                text = "A",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold

            )

            RightArrowButton {}


        }

        Text(
            text = "Ombro e Peito",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 134.dp)
            .fillMaxSize()
            .background(Color(0x88000000))

    ) {

    }

}

@Composable
fun LeftArrowButton(onClick: () -> Unit) {
    TextButton(
        onClick = {},
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text("ᐊ")

    }
}

@Composable
fun RightArrowButton(onClick: () -> Unit) {
    TextButton(
        onClick = {}
    ) {
        Text("ᐅ")
    }
}

@Preview
@Composable
fun WorkoutScreenPreview() {
    val navController = rememberNavController()
    WorkoutScreen(PaddingValues(start = 16.dp), navController)
}