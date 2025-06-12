package com.example.shapenow.ui.screen.Coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.AuthRepository
import com.example.shapenow.data.repository.StudentRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeCoachViewModel : ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val studentRepository = StudentRepository() // <<< Apenas uma instância
    private val authRepository = AuthRepository()

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    private val _coach = MutableStateFlow<User?>(null)
    val coach: StateFlow<User?> = _coach

    private val _allStudents = MutableStateFlow<List<User.Student>>(emptyList())
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    val filteredStudents = combine(_allStudents, _search) { students, query ->
        if (query.isBlank()) {
            students
        } else {
            students.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    fun loadWorkouts(coachId: String) {
        viewModelScope.launch {
            _workouts.value = workoutRepository.getWorkoutsByCoach(coachId)
        }
    }

    fun loadCoach(coachId: String) {
        viewModelScope.launch {
            _coach.value = studentRepository.getStudentById(coachId)
        }
    }

    fun loadAllStudents() {
        viewModelScope.launch {
            _allStudents.value = studentRepository.getAllStudents()
        }
    }

    fun onsearchChange(query: String) {
        _search.value = query
    }

    fun performLogout(navController: NavController) {
        authRepository.logout()
        navController.navigate("LoginScreen") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}