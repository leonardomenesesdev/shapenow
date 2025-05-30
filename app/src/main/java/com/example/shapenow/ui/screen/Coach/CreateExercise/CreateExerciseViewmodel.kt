package com.example.shapenow.ui.screen.Coach.CreateExercise

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class CreateExerciseViewmodel: ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val exerciseRepository: ExerciseRepository = ExerciseRepository()

    private val exercises = mutableListOf<Exercise>()
    private val _name = mutableStateOf("")
    val name: State<String> = _name
    private val _weight = mutableStateOf("")
    val weight: State<String> = _weight
    private val _repetitions = mutableStateOf("")
    val repetitions: State<String> = _repetitions
    private val _rest = mutableStateOf("")
    val rest: State<String> = _rest
    private val _obs = mutableStateOf("")
    val obs: State<String> = _obs
    private val _img = mutableStateOf("")
    val img: State<String> = _img

    private val _saveSuccess = mutableStateOf(false)
    val saveSuccess: State<Boolean> = _saveSuccess

    fun onNameChange(name: String) {
        _name.value = name
    }
    fun onWeightChange(weight: String) {
        _weight.value = weight
    }
    fun onRepetitionsChange(repetitions: String) {
        _repetitions.value = repetitions
    }
    fun onRestChange(rest: String) {
        _rest.value = rest
    }
    fun onObsChange(obs: String) {
        _obs.value = obs
    }
    fun onImgChange(img: String) {
        _img.value = img
    }

    fun saveExercise(){
        val name = name.value.trim()
        val weight = weight.value.trim()
        val repetitions = repetitions.value.trim()
        val rest = rest.value.trim()
        val obs = obs.value.trim()
        val img = img.value.trim()
        if(name.isNotEmpty() && repetitions.isNotEmpty()){
            val exercise = Exercise(
                name = name,
                weight = weight,
                repetitions = repetitions,
                rest = rest,
                img = img,
                obs = obs
            )
            viewModelScope.launch {
                try{
                    exerciseRepository.addExercise(exercise)
                    _saveSuccess.value = true
                } catch (e: Exception){
                    _saveSuccess.value = false
                    e.printStackTrace()
                }
            }

        }
    }

}
