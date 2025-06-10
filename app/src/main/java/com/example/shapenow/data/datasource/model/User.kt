package com.example.shapenow.data.datasource.model

import LastWorkout

// Adicionado o campo 'tipo' ao construtor da classe pai
sealed class User (
    open val uid: String? = "",
    open val name: String = "",
    open val email: String = "",
    open val tipo: String = "", // <<< CAMPO 'tipo' ADICIONADO AQUI
    open val objetivo: String = "",
    open val peso: String = "",
    open val altura: String = "",
    open val imc: String = "",
    open var lastWorkout: LastWorkout? = null
){
    data class Student(
        override val uid: String? = "",
        override val name: String = "",
        override val email: String = "",
        val workouts: List<String>? = null,
        override val objetivo: String = "",
        override val peso: String = "",
        override val altura: String = "",
        override val imc: String = "",
        override var lastWorkout: LastWorkout? = null

        // <<< CONSTRUTOR PAI CHAMADO CORRETAMENTE AQUI >>>
    ): User(
        uid = uid,
        name = name,
        email = email,
        tipo = "student", // Passa a string "student" para o parâmetro 'tipo'
        objetivo = objetivo,
        peso = peso,
        altura = altura,
        imc = imc,
        lastWorkout = lastWorkout
    )

    data class Coach(
        override val uid: String? = "",
        override val name: String = "",
        override val email: String = ""
        // Um 'Coach' pode não ter os outros campos, então os valores padrão da classe pai serão usados.

        // <<< CONSTRUTOR PAI CHAMADO CORRETAMENTE AQUI >>>
    ): User(
        uid = uid,
        name = name,
        email = email,
        tipo = "coach" // Passa a string "coach" para o parâmetro 'tipo'
    )
}