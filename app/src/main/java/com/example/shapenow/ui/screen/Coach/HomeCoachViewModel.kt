package com.example.shapenow.ui.screen.Coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeCoachViewModel : ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val _workout = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workout
    fun loadWorkouts(coachId: String){
        viewModelScope.launch {
            _workout.value = workoutRepository.getWorkouts(coachId)
        }
    }
}