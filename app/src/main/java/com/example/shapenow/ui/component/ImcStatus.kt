import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ImcStatus(imc: Float) {
    val (text, color) = when {
        imc < 18.5 -> "Abaixo do peso" to Color.Blue
        imc < 24.9 -> "Peso normal" to Color(0xFF008000) // Verde
        imc < 29.9 -> "Sobrepeso" to Color(0xFFFFA500) // Laranja
        else -> "Obesidade" to Color.Red
    }
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
    )
}