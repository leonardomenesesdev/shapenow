package com.example.shapenow.ui.screen.Coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.shapenow.data.datasource.model.User // Import genérico de User
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.AuthRepository
import com.example.shapenow.data.repository.StudentRepository // Repositório que pode buscar usuários
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeCoachViewModel : ViewModel() {
    private val workoutRepository = WorkoutRepository()
    // <<< ADIÇÕES AQUI >>>
    private val userRepository = StudentRepository() // Assumindo que ele tem um método para buscar qualquer usuário
    private val authRepository = AuthRepository()

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    private val _coach = MutableStateFlow<User?>(null)
    val coach: StateFlow<User?> = _coach

    fun loadWorkouts(coachId: String) {
        viewModelScope.launch {
            _workouts.value = workoutRepository.getWorkoutsByCoach(coachId)
        }
    }

    fun loadCoach(coachId: String) {
        viewModelScope.launch {
            // Assumindo que o método getStudentById pode buscar qualquer usuário pelo ID
            _coach.value = userRepository.getStudentById(coachId)
        }
    }

    fun performLogout(navController: NavController) {
        authRepository.logout()
        // Navega para a tela de login e limpa todas as telas anteriores da pilha
        navController.navigate("LoginScreen") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}