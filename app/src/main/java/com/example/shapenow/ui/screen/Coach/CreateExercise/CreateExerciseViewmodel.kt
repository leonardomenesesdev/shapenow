package com.example.shapenow.ui.screen.Coach.CreateExercise

import androidx.lifecycle.ViewModel
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateExerciseViewmodel: ViewModel() {
    private val exerciseRepository = ExerciseRepository()
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val exercises = mutableListOf<Exercise>()
    fun addExercise(exercise: Exercise){
        exercises.add(exercise)
    }
    fun removeExercise(index: Int){
        if(index in exercises.indices){
            exercises.removeAt(index)
        }
    }

    fun getExercises(): List<Exercise> = exercises


    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
