package com.example.shapenow.data.datasource

import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout

object Mock {
    val exercise1 = Exercise(
        id = "e1",
        name = "Supino Reto",
        weight = "40kg",
        repetitions = "4x10",
        rest = "60s",
        obs = "Manter postura correta"
    )

    val exercise2 = Exercise(
        id = "e2",
        name = "Remada Curvada",
        weight = "30kg",
        repetitions = "4x12",
        rest = "60s"
    )

    val workout = Workout(
        id = "w1",
        name = "Treino A",
        description = "Treino de peito e costas",
        coachId = "c123",
        studentId = "s456",
        exercises = listOf(exercise1, exercise2)
    )

}