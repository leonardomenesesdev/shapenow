package com.example.shapenow.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultButton( modifier: Modifier, text: String, onClick: () -> Unit, ){
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .width(150.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F44D6))
    ) {
        Text(text = text, fontSize = 24.sp)
    }
    Spacer(modifier = Modifier.height(20.dp))
}