package com.example.shapenow.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
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
    private val _coachId = MutableStateFlow("")
    val coachId: StateFlow<String> = _coachId.asStateFlow()
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search.asStateFlow()
    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    private val _addedExercises = MutableStateFlow<List<String>>(emptyList())
    val addedExercises: StateFlow<List<String>> = _addedExercises.asStateFlow()
    private val _filteredExercises = mutableStateOf<List<Exercise>>(emptyList())
    val filteredExercises: State<List<Exercise>> = _filteredExercises
    init{
        viewModelScope.launch {
            val exercises = exerciseRepository.getExercises()
            _allExercises.value = exercises
            _filteredExercises.value = exercises
        }
    }
    fun filterExercises(search: String){
        if(search.isBlank()){
            _filteredExercises.value = _allExercises.value
        }
        _filteredExercises.value = _allExercises.value.filter {
            it.name.contains(search, ignoreCase = true)
        }

    }
    fun onSearch(search: String){
        _search.value = search
    }
    fun onTitleChange(title: String){
        _title.value = title
    }
    fun onDescriptionChange(description: String){
        _description.value = description
    }
    fun createWorkout(onSuccess: () -> Unit) {
        // A validação está correta
        if (_title.value.isBlank() || _description.value.isBlank() || _addedExercises.value.isEmpty()) {
            _status.value = "Preencha todos os campos e adicione ao menos um exercício" // Mensagem de status um pouco mais clara
            return
        }

        viewModelScope.launch {
            val workoutParaSalvar = Workout(
                id = UUID.randomUUID().toString(),
                title = _title.value,
                description = _description.value,
                coachId = _coachId.value, // Certifique-se que _coachId está recebendo um valor apropriado em algum momento
                studentId = studentId,
                exercises = _addedExercises.value // <<====== CORREÇÃO APLICADA AQUI!
            )

            // Adicione este log para verificar o objeto antes de salvar
            Log.d("CreateWorkoutVM", "Salvando Workout: $workoutParaSalvar")

            val result = workoutRepository.addWorkout(workout = workoutParaSalvar) // Capture o resultado (opcional)

            // Tratar o resultado (opcional, mas bom para depuração)
             if (result.isSuccess) {
                 Log.d("CreateWorkoutVM", "Workout salvo com sucesso no repositório.")
                 onSuccess()
             } else {
                 Log.e("CreateWorkoutVM", "Erro ao salvar workout: ${result.exceptionOrNull()?.message}")
                 _status.value = "Erro ao salvar o treino: ${result.exceptionOrNull()?.message}"
             }
            // OU simplesmente chame onSuccess se não quiser tratar o resultado aqui:
            onSuccess()
        }
    }
    fun addExercise(exercise: Exercise){
        val existentExercises = _addedExercises.value
        val newId = exercise.id
        if(newId==null){
            _status.value = "Erro ao adicionar exercício"
            return
        }
        if(existentExercises.contains(newId)){
            return
        }
        val novaLista = existentExercises + newId
        _addedExercises.value = novaLista
    }
    fun isExerciseAdded(exerciseId: String): Boolean{
        return _addedExercises.value.contains(exerciseId)
    }
    fun deleteExercise(exercise: Exercise){
        val exerciseId = exercise.id
        _addedExercises.value = _addedExercises.value.filter { it != exerciseId }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return CreateWorkoutViewmodel(savedStateHandle) as T
            }
        }
    }

}
