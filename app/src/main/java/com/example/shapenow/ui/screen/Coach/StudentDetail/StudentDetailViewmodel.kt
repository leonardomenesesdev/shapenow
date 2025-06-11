package com.example.shapenow.ui.screen.Coach.StudentDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.StudentRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentDetailViewmodel (
    savedStateHandle: SavedStateHandle,
    private val studentRepository: StudentRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val studentId: String = savedStateHandle.get<String>("studentId")!!

    // Estado para guardar os detalhes do aluno (nome, email, etc.)
    private val _studentDetails = MutableStateFlow<User.Student?>(null)
    val studentDetails = _studentDetails.asStateFlow()

    // Estado para guardar a lista de treinos do aluno
    private val _studentWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val studentWorkouts = _studentWorkouts.asStateFlow()

    // Estado para controlar o carregamento da tela
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadStudentData()
    }

     fun loadStudentData() {
        if (studentId.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            // Usamos 'async' para buscar os dados do aluno e seus treinos em paralelo
            val studentDetailsDeferred = async { studentRepository.getStudentById(studentId) }
            val workoutsDeferred = async { workoutRepository.getWorkoutsByStudent(studentId) }

            // Esperamos os resultados e atualizamos os estados
            _studentDetails.value = studentDetailsDeferred.await()
            _studentWorkouts.value = workoutsDeferred.await()
            _isLoading.value = false
        }
    }
}