package com.example.shapenow.data.datasource.model

data class Exercise(
    val id: String? = "",
    val name: String = "",
    val weight: String? = "",
    val repetitions: String = "",
    val rest: String? = "",
    val img: String? = "",
    val obs: String? = null//antes era val obs: String = ""
)
