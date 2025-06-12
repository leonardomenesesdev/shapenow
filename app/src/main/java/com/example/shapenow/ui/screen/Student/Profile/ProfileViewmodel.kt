package com.example.shapenow.ui.screen.Student.Profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapenow.data.repository.StudentRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols // <<< IMPORT ADICIONADO
import java.util.Locale             // <<< IMPORT ADICIONADO

class ProfileViewmodel: ViewModel() {
    private val studentRepository = StudentRepository()
    private val auth = FirebaseAuth.getInstance()
    private val _nome = MutableStateFlow("")
    val nome = _nome.asStateFlow()
    private val _mail = MutableStateFlow("")
    val mail = _mail.asStateFlow()
    private val _peso = MutableStateFlow("")
    val peso = _peso.asStateFlow()
    private val _altura = MutableStateFlow("")
    val altura = _altura.asStateFlow()
    private val _objetivo = MutableStateFlow("")
    val objetivo = _objetivo.asStateFlow()
    // Inicializa o IMC com uma string que pode ser convertida para float
    private val _imc = MutableStateFlow("0.0")
    val imc = _imc.asStateFlow()
    private val _status = MutableStateFlow("")
    val status = _status.asStateFlow()

    init{
        loadStudent()
        getChanges()
    }

    fun loadStudent(){
        val studentId = auth.currentUser?.uid
        if(studentId != null){
            viewModelScope.launch {
                studentRepository.getStudentById(studentId)?.let{ student ->
                    _nome.value = student.name
                    _mail.value = student.email
                    _peso.value = student.peso
                    _altura.value = student.altura
                    _objetivo.value = student.objetivo
                    _imc.value = student.imc.replace(",",".") // Garante que o IMC carregado também use ponto
                }
            }
        }
        else{
            _status.value = "Erro ao carregar o aluno"
            Log.i("ProfileAluno", "Erro ao carregar dados do aluno")
        }
    }
    private fun getChanges(){
        viewModelScope.launch {
            combine(_peso, _altura){ p, a ->
                calcImc(p, a)
            }.collect { newImc ->
                _imc.value = newImc
            }
        }
    }
    fun onPeso(peso: String){
        _peso.value = peso
    }
    fun onAltura(altura: String){
        _altura.value = altura
    }
    fun onObjetivo(objetivo: String){
        _objetivo.value = objetivo
    }
    fun save(){
        val studentId = auth.currentUser?.uid
        if(studentId == null){
            _status.value = "Erro ao salvar o aluno"
            return
        }
        val profileData = mapOf(
            "peso" to _peso.value,
            "altura" to _altura.value,
            "objetivo" to _objetivo.value,
            "imc" to _imc.value
        )
        viewModelScope.launch {
            val result = studentRepository.updateStudentProfile(studentId, profileData)
            if(result.isSuccess){
                _status.value = "Perfil atualizado com sucesso"
            }
            else{
                _status.value = "Erro ao atualizar o perfil"
                Log.i("ProfileAluno", "Erro ao atualizar o perfil ${result.exceptionOrNull()}")
            }
        }
    }

    // <<< FUNÇÃO calcImc CORRIGIDA >>>
    private fun calcImc(p: String, a: String): String{
        val peso = p.replace(",", ".").toFloatOrNull()
        val altura = a.replace(",", ".").toFloatOrNull()
        if(peso == null || altura == null || peso <= 0 || altura <= 0){
            return "0.0" // Retorna uma string numérica válida
        }
        val alturaMetros = if(altura > 3) altura / 100 else altura
        val imcValue = peso / (alturaMetros * alturaMetros)

        // Cria um DecimalFormat que sempre usa '.' como separador, independente do celular
        val symbols = DecimalFormatSymbols(Locale.US)
        val df = DecimalFormat("#.0", symbols) // Usando uma casa decimal para o IMC é suficiente

        return df.format(imcValue)
    }

    fun clear(){
        _status.value=""
    }
}