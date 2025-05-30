package com.example.shapenow.data.datasource.model

sealed class User (
    open val uid: String?,
    open val name: String?,
    open val email: String?,
    open val tipo: String
){
    data class Student(
        override val uid: String?,
        override val name: String?,
        override val email: String?,
        val workouts: List<String>? = null //pode dar ruim aq
    ): User(uid, name, email, "student")
    data class Coach(
        override val uid: String?,
        override val name: String?,
        override val email: String?
    ): User(uid, name, email, "coach")
}