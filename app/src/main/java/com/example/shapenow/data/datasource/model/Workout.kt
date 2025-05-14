package com.example.shapenow.data.datasource.model

data class Workout(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val repetitions: String = "", //String mesmo ou vamos manipular algo envolvendo as repeticoes?
    val rest: String =""
)
