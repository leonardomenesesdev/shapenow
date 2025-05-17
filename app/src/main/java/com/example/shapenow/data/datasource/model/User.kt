package com.example.shapenow.data.datasource.model

sealed class User (
    open val uid: String?,
    open val name: String?,
    open val email: String?
){
    data class Student(
        override val uid: String?,
        override val name: String?,
        override val email: String?,
//        val workouts: List<Workouts>? = null TODO
    ): User(uid, name, email)
    data class Coach(
        override val uid: String?,
        override val name: String?,
        override val email: String?
    ): User(uid, name, email)
}