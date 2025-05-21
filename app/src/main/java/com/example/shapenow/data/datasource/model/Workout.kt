package com.example.shapenow.data.datasource.model

data class Workout(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val coachId: String = "",
    val studentId: String = "",
    val exercises: List<Exercise> = emptyList()
)