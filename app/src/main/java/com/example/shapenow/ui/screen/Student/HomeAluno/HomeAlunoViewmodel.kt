package com.example.shapenow.ui.screen.Student.HomeAluno

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.AuthRepository
import com.example.shapenow.data.repository.StudentRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeAlunoViewmodel (): ViewModel(){
    private val workoutRepository = WorkoutRepository()
    private val userRepository = StudentRepository()
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user
    // <<< NOVOS ESTADOS PARA O ÚLTIMO TREINO >>>
    private val _lastCompletedWorkout = MutableStateFlow<Workout?>(null)
    val lastCompletedWorkout: StateFlow<Workout?> = _lastCompletedWorkout

    private val _workout = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workout
    fun loadWorkouts(studentId: String){
        viewModelScope.launch {
            _workout.value = workoutRepository.getWorkoutsByStudent(studentId)
        }
    }

    private val TAG = "HomeAlunoViewModel" // <-- Defina uma TAG

    fun loadUser(studentId: String) {
        Log.d("HomeAlunoViewModel", "ViewModel chamando loadUser com studentId: $studentId")
        viewModelScope.launch {
            val student = userRepository.getStudentById(studentId)
            _user.value = student

            // <<< LÓGICA PARA CARREGAR OS DETALHES DO ÚLTIMO TREINO >>>
            student?.lastWorkout?.workoutId?.let { lastWorkoutId ->
                if (lastWorkoutId.isNotEmpty()) {
                    _lastCompletedWorkout.value = workoutRepository.getWorkoutById(lastWorkoutId)
                }
            }
        }
    }
}
