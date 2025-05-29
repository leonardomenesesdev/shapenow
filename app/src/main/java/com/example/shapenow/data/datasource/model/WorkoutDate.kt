package com.example.shapenow.data.datasource.model

import java.util.Date

data class WorkoutDate (
    val workout: Workout = Workout(),
    val date: Date = Date()
)