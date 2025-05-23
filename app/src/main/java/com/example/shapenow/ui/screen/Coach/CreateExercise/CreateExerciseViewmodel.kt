package com.example.shapenow.ui.screen.Coach.CreateExercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateExerciseViewmodel: ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val exercises = mutableListOf<Exercise>()
    fun addExercise(workoutId: String, exercise: Exercise){
//        exercises.add(exercise)
        viewModelScope.launch {
            val result = workoutRepository.updateWorkout(workoutId, exercise)
            _uiState.value = if (result.isSuccess) {
                CreateExerciseViewmodel.UiState.Success
            } else {
                CreateExerciseViewmodel.UiState.Error(result.exceptionOrNull()?.message ?: "Erro desconhecido")
            }
        }    }
    fun removeExercise(index: Int){
        if(index in exercises.indices){
            exercises.removeAt(index)
        }
    }

    fun getExercises(): List<Exercise> = exercises

    fun loadExercises(workoutId: String) {
    viewModelScope.launch {
        val result = workoutRepository.getExercises(workoutId)
        }
    }
    fun resetState() {
        _uiState.value = UiState.Idle
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
