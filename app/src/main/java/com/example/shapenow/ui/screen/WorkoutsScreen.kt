package com.example.shapenow.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.R

@Composable
fun WorkoutScreen(innerPadding: PaddingValues, navController: NavController) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x88000000))
            .padding(15.dp)

    ) {
        Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2F0C6D), shape = RoundedCornerShape(16.dp))
                    .padding(30.dp)
                    .clip(RoundedCornerShape(16.dp))
                )
        {


            Image(
                painter = painterResource(id = R.drawable.bg_photo),
                contentDescription = "USer",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Bem vindo, user!",
                modifier = Modifier
                    .padding(top = 35.dp,)
                    .padding(start = 150.dp)
            )

        }

    }

    LazyColumn() {




    }



}

@Preview
@Composable
fun WorkoutScreenPreview() {
    val navController = rememberNavController()
    WorkoutScreen(PaddingValues(start = 16.dp), navController)
}