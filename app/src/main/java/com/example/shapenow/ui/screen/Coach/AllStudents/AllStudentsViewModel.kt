package com.example.shapenow.ui.screen.Coach.AllStudents


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AllStudentsViewModel : ViewModel() {

    private val studentRepository = StudentRepository()

    private val _allStudents = MutableStateFlow<List<User.Student>>(emptyList())

    // Guarda o texto atual da barra de busca
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    val filteredStudents = combine(_allStudents, _search) { students, query ->
        if (query.isBlank()) {
            students
        } else {
            students.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }.stateIn( // Converte o Flow combinado em um StateFlow
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Começa a observar 5s depois que a UI para
        initialValue = emptyList() // Valor inicial é uma lista vazia
    )

    init {
        loadAllStudents()
    }

    private fun loadAllStudents() {
        viewModelScope.launch {
            _allStudents.value = studentRepository.getAllStudents()
        }
    }

    fun onsearchChange(query: String) {
        _search.value = query
    }
}