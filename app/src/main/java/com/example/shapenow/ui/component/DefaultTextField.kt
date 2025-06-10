package com.example.shapenow.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions // Importe
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation // Importe
import androidx.compose.ui.unit.dp
import com.example.shapenow.ui.theme.actionColor1
import com.example.shapenow.ui.theme.textColor1

@Composable
fun DefaultTextField(
    modifier: Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    padding: Int,
    // <<< PARÂMETROS ADICIONADOS >>>
    visualTransformation: VisualTransformation = VisualTransformation.None, // Padrão é não transformar
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default, // Padrão é o teclado normal
    trailingIcon: @Composable (() -> Unit)? = null // Ícone opcional no final
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = textColor1) },
        modifier = modifier.padding(padding.dp), // Usa o padding que você já tinha
        singleLine = true, // Você pode querer manter isso

        // <<< PARÂMETROS APLICADOS AQUI >>>
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,

        // Suas cores personalizadas
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White.copy(alpha = 0.8f),
            cursorColor = actionColor1,
            focusedBorderColor = actionColor1,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = actionColor1,
            unfocusedLabelColor = textColor1.copy(alpha = 0.7f),
            focusedTrailingIconColor = actionColor1,
            unfocusedTrailingIconColor = Color.Gray
        )
    )
}