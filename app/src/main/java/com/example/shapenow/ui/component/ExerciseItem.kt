package com.example.shapenow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // <<< Importe o clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // Removido Alignment pois não está sendo usado diretamente no Box
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shapenow.data.datasource.model.Exercise
// import com.example.shapenow.ui.theme.secondaryBlue // Não é mais necessário se cardColor é parâmetro

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onClick: () -> Unit, // A lambda que você passa da WorkoutDetailScreen
    modifier: Modifier = Modifier, // O modifier que você passa (ex: padding)
    cardColor: Color,
    textColor: Color
) {
    Box(
        // Aplica primeiro o modifier externo, depois os internos, incluindo o clickable
        modifier = modifier // Este já vem com .padding(bottom = 12.dp) da WorkoutDetailScreen
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(cardColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick) // <<< ADICIONADO AQUI PARA TORNAR O CARD CLICÁVEL
            .padding(16.dp) // Padding interno para o conteúdo do Box
    ) {
        Column {
            Text(
                text = exercise.name,
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Exibindo detalhes apenas se existirem e não forem vazios
            if (exercise.repetitions.isNotBlank()) {
                Text(
                    text = "Repetições: ${
                        // Sua lógica para extrair apenas as repetições está OK, 
                        // mas certifique-se que o formato "x" sempre existe se for usar substringAfter
                        // Uma forma mais segura seria verificar antes ou usar regex.
                        // Por ora, mantendo sua lógica original:
                        if (exercise.repetitions.contains("x", ignoreCase = true))
                            exercise.repetitions.substringAfter('x', exercise.repetitions).trim()
                        else
                            exercise.repetitions
                    }",
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (exercise.weight!!.isNotBlank()) {
                Text(
                    text = "Carga: ${exercise.weight}",
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (exercise.rest!!.isNotBlank()) {
                Text(
                    text = "Descanso: ${exercise.rest}",
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (exercise.obs!!.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp)) // Espaço extra antes das observações
                Text(
                    text = "Obs: ${exercise.obs}",
                    color = textColor.copy(alpha = 0.7f), // Um pouco mais sutil
                    style = MaterialTheme.typography.bodySmall // Fonte menor para observações
                )
            }
        }
    }
}