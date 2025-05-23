package com.example.shapenow.ui.screen.Coach.WorkoutDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import com.example.shapenow.viewmodel.CreateWorkoutViewmodel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutDetailViewmodel : ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises
    fun loadExercises(workoutId: String) {
        viewModelScope.launch {
            val result = workoutRepository.getExercises(workoutId)
            result.onSuccess {
                _exercises.value = it
            }.onFailure {
                Log.i("error", "error on workoutdetailviewmodel")
            }
        }
    }

}
