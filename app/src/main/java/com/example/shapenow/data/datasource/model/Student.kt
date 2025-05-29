package com.example.shapenow.data.datasource.model

data class Student (
    val uid: String,
    val name: String,
    val email: String,
    val lastWorkoutDay: String = "",
    val workouts: List<String> = emptyList()
)