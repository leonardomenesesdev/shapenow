package com.example.shapenow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.ui.theme.actionColor1

@Composable
fun ExerciseDetailCard(
    exercise: Exercise,
    seriesChecked: List<Boolean>,
    onSeriesCheckedChange: (Int, Boolean) -> Unit,
    cardColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(cardColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = exercise.name,
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Repetições: ${exercise.repetitions.substringAfter('x').trim()}", // Exibe apenas as reps "12" de "3x12"
                color = textColor.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
            if(exercise.weight!="") {
                Text(
                    text = "Carga: ${exercise.weight}",
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if(exercise.rest!="") {
                Text(
                    text = "Descanso: ${exercise.rest}",
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if(exercise.obs!=null) {
                Text(
                    text = "Observação: ${exercise.obs}",
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Linha com os Checkboxes para cada série
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                seriesChecked.forEachIndexed { index, isChecked ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { newCheckedState ->
                                onSeriesCheckedChange(index, newCheckedState)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = actionColor1,
                                uncheckedColor = textColor,
                                checkmarkColor = cardColor
                            )
                        )
                        Text(
                            text = "${index + 1}ª",
                            color = textColor,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}