package com.example.shapenow.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CreateWorkoutViewmodel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val exerciseRepository = ExerciseRepository()
    private val studentId: String = savedStateHandle.get<String>("studentId") ?: ""
    private var _status = MutableStateFlow("")
    val status: StateFlow<String> = _status.asStateFlow()
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()


}