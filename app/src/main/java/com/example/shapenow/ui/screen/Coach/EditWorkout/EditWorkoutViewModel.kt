package com.example.shapenow.ui.screen.Coach.EditWorkout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
): ViewModel() {
    private val workoutId: String = savedStateHandle.get<String>("workoutId") ?: ""
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()
    private val _currentExercisesId = MutableStateFlow<List<String>>(emptyList())
    val currentExercisesId = _currentExercisesId.asStateFlow()
    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val allExercises = _allExercises.asStateFlow()
    private val _status = MutableStateFlow("")
    val status = _status.asStateFlow()
    init{
        loadDados()
    }
    private fun loadDados(){
        if(workoutId.isBlank()){
            _status.value = "Erro: Id vazio/nao idenficado."
            return
        }
        viewModelScope.launch {
            workoutRepository.getWorkoutById(workoutId)?.let{workout ->
                _title.value = workout.title
                _description.value = workout.description ?: ""
                _currentExercisesId.value = workout.exercises
            } ?: run {
                _status.value = "Erro: treino não encontrado."
            }
            _allExercises.value = exerciseRepository.getExercises()
        }
    }
    fun onTitle(title: String){
        _title.value = title
    }
    fun onDescription(description: String) {
        _description.value = description
    }
    fun addNewExercise(exerciseId: String){
        if(!_currentExercisesId.value.contains(exerciseId)){
            _currentExercisesId.value = _currentExercisesId.value + exerciseId
        }
    }
    fun removeExercise(exerciseId: String){
        if(_currentExercisesId.value.contains(exerciseId)){
            _currentExercisesId.value = _currentExercisesId.value - exerciseId
        }
    }
    fun save(onSuccess: () -> Unit){
        if(_title.value.isBlank()||_currentExercisesId.value.isEmpty()){
            _status.value = "Erro: titulo vazio/sem exercicios."
            return
        }
        viewModelScope.launch {
            workoutRepository.updateWorkout(
                workoutId = workoutId,
                name = _title.value,
                description = _description.value,
                exercises = _currentExercisesId.value
            )
            onSuccess()
        }
    }
    fun clearStatus(){
        _status.value = ""
    }
}