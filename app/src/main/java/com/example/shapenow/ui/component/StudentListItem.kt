package com.example.shapenow.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.ui.theme.buttonColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@Composable
fun StudentListItem(student: User.Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = buttonColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.titleMedium,
                color = textColor1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = student.email,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor1.copy(alpha = 0.7f)
            )
        }
    }
}