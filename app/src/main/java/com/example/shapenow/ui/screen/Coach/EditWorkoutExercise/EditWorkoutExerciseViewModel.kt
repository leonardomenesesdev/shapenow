package com.example.shapenow.ui.screen.Coach.EditWorkoutExercise

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.repository.ExerciseRepository
import com.example.shapenow.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditWorkoutExerciseViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    val workoutId: String = savedStateHandle.get<String>("workoutId") ?: ""
    private val exerciseIdToEdit: String = savedStateHandle.get<String>("exerciseIdToEdit") ?: ""

    private val _exerciseName = MutableStateFlow("")
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _exerciseWeight = MutableStateFlow("")
    val exerciseWeight: StateFlow<String> = _exerciseWeight.asStateFlow()

    private val _exerciseRepetitions = MutableStateFlow("")
    val exerciseRepetitions: StateFlow<String> = _exerciseRepetitions.asStateFlow()

    private val _exerciseRest = MutableStateFlow("")
    val exerciseRest: StateFlow<String> = _exerciseRest.asStateFlow()

    private val _exerciseImg = MutableStateFlow("")
    val exerciseImg: StateFlow<String> = _exerciseImg.asStateFlow()

    private val _exerciseObs = MutableStateFlow("")
    val exerciseObs: StateFlow<String> = _exerciseObs.asStateFlow()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    private var originalWorkoutNameForUpdate: String = ""
    private var originalWorkoutDescriptionForUpdate: String = ""

    init {
        if (workoutId.isBlank() || exerciseIdToEdit.isBlank()) {
            _statusMessage.value = "Erro: IDs de treino ou exercício inválidos."
            Log.e("EditWorkoutExerciseVM", "workoutId ou exerciseIdToEdit está em branco no init.")
        } else {
            loadExerciseDetails()
        }
    }

    private fun loadExerciseDetails() {
        viewModelScope.launch {
            val exercise = exerciseRepository.getExerciseById(exerciseIdToEdit)
            exercise?.let {
                _exerciseName.value = it.name
                _exerciseWeight.value = it.weight ?: ""
                _exerciseRepetitions.value = it.repetitions
                _exerciseRest.value = it.rest ?: ""
                _exerciseImg.value = it.img ?: ""
                _exerciseObs.value = it.obs ?: ""
            } ?: run {
                _statusMessage.value = "Erro ao carregar detalhes do exercício original."
                Log.e("EditWorkoutExerciseVM", "Exercício original com ID $exerciseIdToEdit não encontrado.")
            }
        }
    }

    private suspend fun getOriginalWorkout(): Boolean {
        val workout = workoutRepository.getWorkoutById(workoutId)
        return if (workout != null) {
            originalWorkoutNameForUpdate = workout.title
            originalWorkoutDescriptionForUpdate = workout.description ?: ""
            true
        } else {
            _statusMessage.value = "Erro crítico: Treino original não encontrado. Não é possível atualizar."
            Log.e("EditWorkoutExerciseVM", "Treino original com ID $workoutId não pôde ser buscado para salvar nome/descrição.")
            false
        }
    }

    fun onNameChange(name: String) { _exerciseName.value = name }
    fun onWeightChange(weight: String) { _exerciseWeight.value = weight }
    fun onRepetitionsChange(reps: String) { _exerciseRepetitions.value = reps }
    fun onRestChange(rest: String) { _exerciseRest.value = rest }
    fun onImgChange(img: String) { _exerciseImg.value = img }
    fun onObsChange(obs: String) { _exerciseObs.value = obs }


    fun saveEditedExercise(onSuccess: () -> Unit) {
        if (_exerciseName.value.isBlank()) {
            _statusMessage.value = "O nome do exercício não pode estar vazio."
            return
        }

        viewModelScope.launch {
            if (!getOriginalWorkout()) {
                return@launch
            }

            val exerciseDataToSave = Exercise(
                name = _exerciseName.value,
                weight = _exerciseWeight.value,
                repetitions = _exerciseRepetitions.value,
                rest = _exerciseRest.value,
                img = _exerciseImg.value,
                obs = _exerciseObs.value
            )

            val actualNewExerciseId = exerciseRepository.addExercise(exerciseDataToSave)

            if (actualNewExerciseId == null) {
                _statusMessage.value = "Erro ao salvar o novo exercício editado."
                Log.e("EditWorkoutExerciseVM", "Falha ao adicionar novo exercício, ID retornado foi null.")
                return@launch
            }
            Log.d("EditWorkoutExerciseVM", "Novo exercício editado salvo com ID REAL: $actualNewExerciseId")


            val currentWorkout = workoutRepository.getWorkoutById(workoutId)
            if (currentWorkout == null) {
                _statusMessage.value = "Erro: Treino original não encontrado para pegar a lista de exercícios."
                Log.e("EditWorkoutExerciseVM", "Treino original com ID $workoutId não encontrado ao tentar pegar a lista de exercícios.")
                return@launch
            }

            val updatedExerciseIds = currentWorkout.exercises.toMutableList()
            val originalExerciseIndex = updatedExerciseIds.indexOf(exerciseIdToEdit)

            if (originalExerciseIndex != -1) {
                updatedExerciseIds[originalExerciseIndex] = actualNewExerciseId
            } else {
                Log.w("EditWorkoutExerciseVM", "ID do exercício original ($exerciseIdToEdit) não encontrado na lista do treino $workoutId. O novo exercício $actualNewExerciseId será adicionado ao final.")
                updatedExerciseIds.add(actualNewExerciseId)
            }

            workoutRepository.updateWorkout(
                workoutId = workoutId,
                name = originalWorkoutNameForUpdate,
                description = originalWorkoutDescriptionForUpdate,
                exercises = updatedExerciseIds
            )
            _statusMessage.value = "Exercício atualizado no treino com sucesso!"
            Log.d("EditWorkoutExerciseVM", "Treino $workoutId atualizado. Exercício $exerciseIdToEdit substituído por $actualNewExerciseId.")
            onSuccess()
        }
    }
// ...

    fun clearStatusMessage() {
        _statusMessage.value = ""
    }

    // <<< ADICIONE OU AJUSTE A FACTORY AQUI >>>
    companion object {
        fun Factory(
            exerciseRepository: ExerciseRepository,
            workoutRepository: WorkoutRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return EditWorkoutExerciseViewModel(
                    savedStateHandle,
                    exerciseRepository,
                    workoutRepository
                ) as T
            }
        }
    }
}