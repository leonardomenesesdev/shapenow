package com.example.shapenow.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.textColor1

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultTextField(modifier: Modifier, label: String, value: String, onValueChange: (String) -> Unit, padding : Int) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth()
            .padding(padding.dp),
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = actionColor1,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = textColor1,
            unfocusedLabelColor = textColor1
        )
    )
}

@Preview(showBackground = false)
@Composable
fun PreviewCustomTextField() {
    var text by remember { mutableStateOf("") }
//    CustomTextField(label = "A", value = text, onValueChange = { text = it }, padding = 40)
}