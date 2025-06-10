package com.example.shapenow.data.datasource.model

import LastWorkout

sealed class User (
    open val uid: String? = "",
    open val name: String = "",
    open val email: String = "",
    open val tipo: String = "",
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

    ): User(
        uid = uid,
        name = name,
        email = email,
        tipo = "student",
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

    ): User(
        uid = uid,
        name = name,
        email = email,
        tipo = "coach"
    )
}