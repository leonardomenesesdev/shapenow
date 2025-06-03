package com.example.shapenow.ui.screen.Coach.WorkoutDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutDetailViewmodel : ViewModel() {
    private val workoutRepository = WorkoutRepository()

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    private val _workoutDeleted = MutableStateFlow(false)
    val workoutDeleted: StateFlow<Boolean> = _workoutDeleted

    fun loadExercises(workoutId: String) {
        viewModelScope.launch {
            try {
                val result = workoutRepository.getExercisesFromWorkout(workoutId)
                _exercises.value = result
            } catch (e: Exception) {
                Log.e("WorkoutDetailViewmodel", "Erro ao carregar exercícios", e)
            }
        }
    }

    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            try {
                workoutRepository.deleteWorkout(workoutId)
                _workoutDeleted.value = true
            } catch (e: Exception) {
                Log.e("WorkoutDetailViewmodel", "Erro ao deletar o treino", e)
            }
        }
    }
    fun onDeletionHandled() {
        _workoutDeleted.value = false
    }
}