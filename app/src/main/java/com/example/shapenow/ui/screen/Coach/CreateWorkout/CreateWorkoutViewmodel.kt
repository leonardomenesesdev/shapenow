package com.example.shapenow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CreateWorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    var title = ""
    var description = ""
    var studentId = ""
    private val exercises = mutableListOf<Exercise>()

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }

    fun removeExercise(index: Int) {
        if (index in exercises.indices) {
            exercises.removeAt(index)
        }
    }

    fun getExercises(): List<Exercise> = exercises

    fun createWorkout() {
        if (title.isBlank() || studentId.isBlank() || exercises.isEmpty()) {
            _uiState.value = UiState.Error("Todos os campos devem ser preenchidos.")
            return
        }

        val workout = Workout(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            studentId = studentId,
            exercises = exercises
        )

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = workoutRepository.addWorkout(workout)
            _uiState.value = if (result.isSuccess) {
                UiState.Success
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Erro desconhecido")
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
