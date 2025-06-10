package com.example.shapenow.ui.screen.Coach.AllStudents


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shapenow.ui.component.StudentListItem
import com.example.shapenow.ui.screen.rowdies
import com.example.shapenow.ui.theme.backgColor
import com.example.shapenow.ui.theme.textColor1

@Composable
fun AllStudentScreen(
    viewModel: AllStudentsViewModel = viewModel(),
    navController: NavController
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
                        Log.i("msg", "Clicou no aluno ${student.name}")
                        navController.navigate("StudentDetailScreen/${student.uid}")
                    }
                }
            }
        }
    }


