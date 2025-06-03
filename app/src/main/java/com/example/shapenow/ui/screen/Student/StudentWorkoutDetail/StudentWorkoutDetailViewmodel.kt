package com.example.shapenow.ui.screen.Student.WorkoutDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentWorkoutDetailViewmodel : ViewModel() {

    private val workoutRepository = WorkoutRepository()

    private val _workout = MutableStateFlow<Workout?>(null)
    val workout: StateFlow<Workout?> = _workout.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    // Key: exercise.id, Value: Lista de Boolean representando o estado (marcado/desmarcado) de cada série
    private val _seriesState = MutableStateFlow<Map<String, List<Boolean>>>(emptyMap())
    val seriesState: StateFlow<Map<String, List<Boolean>>> = _seriesState.asStateFlow()

    fun loadWorkoutDetails(workoutId: String) {
        viewModelScope.launch {
            // Carrega os detalhes do treino e a lista de exercícios
            _workout.value = workoutRepository.getWorkoutById(workoutId)
            val fetchedExercises = workoutRepository.getExercisesFromWorkout(workoutId)
            _exercises.value = fetchedExercises

            // Inicializa o estado das séries para cada exercício
            val initialSeriesState = fetchedExercises.associate { exercise ->
                // Cria uma lista de 'false' para cada série do exercício
                exercise.id to List(parseSeriesCount(exercise.repetitions)) { false }
            }
            _seriesState.value = initialSeriesState
        }
    }

    fun onSeriesCheckedChange(exerciseId: String, seriesIndex: Int, isChecked: Boolean) {
        val currentStates = _seriesState.value.toMutableMap()
        val seriesStatus = currentStates[exerciseId]?.toMutableList()

        if (seriesStatus != null && seriesIndex < seriesStatus.size) {
            seriesStatus[seriesIndex] = isChecked
            currentStates[exerciseId] = seriesStatus
            _seriesState.value = currentStates
        }
    }

    /**
     * Tenta extrair o número de séries de uma string como "3x12" ou "4x10-15".
     * Se o formato não for reconhecido, retorna um valor padrão (3).
     */
    private fun parseSeriesCount(repetitions: String): Int {
        return try {
            if (repetitions.contains("x", ignoreCase = true)) {
                repetitions.split("x", ignoreCase = true).first().trim().toInt()
            } else {
                3 // Valor padrão se não houver um 'x' para indicar as séries
            }
        } catch (e: Exception) {
            3 // Valor padrão em caso de erro de parsing
        }
    }
}