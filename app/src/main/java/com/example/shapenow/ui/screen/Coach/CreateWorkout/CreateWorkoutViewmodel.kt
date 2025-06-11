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
import com.example.shapenow.data.repository.StudentRepository // Importe seu StudentRepository
import com.example.shapenow.data.repository.WorkoutRepository
import com.google.firebase.auth.FirebaseAuth // Para obter o coachId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CreateWorkoutViewmodel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val workoutRepository = WorkoutRepository()
    private val exerciseRepository = ExerciseRepository()
    private val studentRepository = StudentRepository() // Instancie o StudentRepository

    // private val studentIdFromNav: String = savedStateHandle.get<String>("studentId") ?: "" // Pode não ser mais usado

    private var _status = MutableStateFlow("")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    // Obter o ID do coach logado
    private val _coachId = MutableStateFlow(FirebaseAuth.getInstance().currentUser?.uid ?: "")
    val coachId: StateFlow<String> = _coachId.asStateFlow()

    // MUDANÇA: Agora vamos guardar o email do aluno
    private val _studentEmail = MutableStateFlow("")
    val studentEmail: StateFlow<String> = _studentEmail.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList()) // Não usado diretamente para salvar, mas pode ser para UI
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search.asStateFlow()

    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    private val _addedExercises = MutableStateFlow<List<String>>(emptyList()) // Lista de IDs dos exercícios adicionados
    val addedExercises: StateFlow<List<String>> = _addedExercises.asStateFlow()

    private val _filteredExercises = mutableStateOf<List<Exercise>>(emptyList())
    val filteredExercises: State<List<Exercise>> = _filteredExercises

    init{
        viewModelScope.launch {
            val exercisesList = exerciseRepository.getExercises()
            _allExercises.value = exercisesList
            _filteredExercises.value = exercisesList
        }
        if (_coachId.value.isBlank()) {
            Log.e("CreateWorkoutVM", "Coach ID não está definido! O usuário precisa estar logado como coach.")
            _status.value = "Erro: Identificação do treinador não encontrada. Faça login novamente."
        }
    }

    fun filterExercises(query: String){ // Renomeado para query para clareza
        _filteredExercises.value = if(query.isBlank()){
            _allExercises.value
        } else {
            _allExercises.value.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    fun onSearch(newSearch: String){ // Renomeado para newSearch
        _search.value = newSearch
        filterExercises(newSearch) // Filtra ao digitar
    }

    fun onTitleChange(newTitle: String){
        _title.value = newTitle
    }

    fun onDescriptionChange(newDescription: String){
        _description.value = newDescription
    }

    // MUDANÇA: Função para atualizar o email do aluno
    fun onStudentEmailChange(email: String){
        _studentEmail.value = email
    }

    fun createWorkout(onSuccess: () -> Unit) {
        if (_title.value.isBlank() || _addedExercises.value.isEmpty() || _studentEmail.value.isBlank()) {
            _status.value = "Preencha: Título, Descrição, Email do Aluno e adicione ao menos um exercício."
            return
        }
        if (_coachId.value.isBlank()) {
            _status.value = "Não foi possível identificar o treinador. Por favor, tente logar novamente."
            return
        }

        viewModelScope.launch {
            _status.value = "Procurando aluno..." // Feedback para o usuário
            val targetStudentId = studentRepository.getStudentIdByEmail(_studentEmail.value.trim())

            if (targetStudentId == null) {
                _status.value = "Aluno com o email '${_studentEmail.value}' não encontrado ou não é um aluno."
                Log.w("CreateWorkoutVM", "Aluno não encontrado para o email: ${_studentEmail.value}")
                return@launch
            }

            _status.value = "Salvando treino..." // Feedback para o usuário
            val workoutToSave = Workout(
                id = UUID.randomUUID().toString(),
                title = _title.value,
                description = _description.value,
                coachId = _coachId.value,
                studentId = targetStudentId, // Usa o ID do aluno encontrado
                exercises = _addedExercises.value
            )

            Log.d("CreateWorkoutVM", "Salvando Workout: $workoutToSave")
            val result = workoutRepository.addWorkout(workout = workoutToSave)

            if (result.isSuccess) {
                Log.d("CreateWorkoutVM", "Workout salvo com sucesso.")
                _status.value = "Treino criado com sucesso!"
                onSuccess()
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Erro desconhecido."
                Log.e("CreateWorkoutVM", "Erro ao salvar workout: $errorMsg")
                _status.value = "Erro ao salvar o treino: $errorMsg"
            }
        }
    }

    fun addExercise(exercise: Exercise){
        val existentIds = _addedExercises.value.toMutableList()
        if (exercise.id.isNotBlank() && !existentIds.contains(exercise.id)) {
            existentIds.add(exercise.id)
            _addedExercises.value = existentIds
        } else if (exercise.id.isBlank()){
            _status.value = "Erro: Exercício sem ID não pode ser adicionado."
        }
    }

    fun isExerciseAdded(exerciseId: String): Boolean{
        return _addedExercises.value.contains(exerciseId)
    }

    fun deleteExercise(exercise: Exercise){
        _addedExercises.value = _addedExercises.value.filter { it != exercise.id }
    }

    fun clearStatus() {
        _status.value = ""
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