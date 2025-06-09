package com.example.shapenow.ui.screen.Coach.AllStudents


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.secondaryBlue
import com.example.shapenow.ui.theme.textColor1

@Composable
fun AllStudentScreen(
    viewModel: AllStudentsViewModel = viewModel()
) {
    val searchQuery by viewModel.search.collectAsState()
    val filteredStudents by viewModel.filteredStudents.collectAsState()

        Column(
            modifier = Modifier
                .background(backgColor)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Alunos Cadastrados",
                style = MaterialTheme.typography.headlineLarge,
                color = textColor1,
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = rowdies,
                textAlign = TextAlign.Center
            )
            // Barra de Busca
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onsearchChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar por nome...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Ícone de busca")
                },
                singleLine = true
                // Adicione suas cores personalizadas aqui se necessário
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Alunos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredStudents, key = { it.uid!! }) { student ->
                    StudentListItem(student = student) {
                        // Ação de clique: navegar para um perfil de aluno detalhado (funcionalidade futura)
                        // Por exemplo: navController.navigate("student_profile_details/${student.uid}")
                    }
                }
            }
        }
    }


@Composable
fun StudentListItem(student: User.Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = secondaryBlue)
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