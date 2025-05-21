package com.example.shapenow.data.datasource

import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout

object Mock {
    // Peito
    val exercise1 = Exercise(
        id = "e1",
        name = "Supino Reto",
        weight = "40kg",
        repetitions = "4x10",
        rest = "60s",
        obs = "Manter postura correta"
    )

    val exercise8 = Exercise(
        id = "e8",
        name = "Crucifixo Reto",
        weight = "14kg",
        repetitions = "3x12",
        rest = "45s"
    )

    val exercise11 = Exercise(
        id = "e11",
        name = "Supino Inclinado com Halteres",
        weight = "18kg",
        repetitions = "4x10",
        rest = "60s"
    )

    val exercise12 = Exercise(
        id = "e12",
        name = "Crossover na Polia",
        weight = "12kg",
        repetitions = "3x15",
        rest = "45s",
        obs = "Contração máxima no centro"
    )

    // Costas
    val exercise2 = Exercise(
        id = "e2",
        name = "Remada Curvada",
        weight = "30kg",
        repetitions = "4x12",
        rest = "60s"
    )

    val exercise9 = Exercise(
        id = "e9",
        name = "Pulley Frente",
        weight = "35kg",
        repetitions = "4x10",
        rest = "60s",
        obs = "Não deixar o peso cair"
    )

    val exercise13 = Exercise(
        id = "e13",
        name = "Remada Unilateral com Halter",
        weight = "16kg",
        repetitions = "3x10",
        rest = "60s"
    )

    val exercise14 = Exercise(
        id = "e14",
        name = "Levantamento Terra",
        weight = "60kg",
        repetitions = "4x6",
        rest = "90s"
    )

    // Bíceps
    val exercise3 = Exercise(
        id = "e3",
        name = "Rosca Direta",
        weight = "20kg",
        repetitions = "3x12",
        rest = "45s",
        obs = "Evitar balanço do corpo"
    )

    val exercise10 = Exercise(
        id = "e10",
        name = "Rosca Alternada",
        weight = "10kg",
        repetitions = "3x12",
        rest = "45s"
    )

    val exercise15 = Exercise(
        id = "e15",
        name = "Rosca Concentrada",
        weight = "8kg",
        repetitions = "3x10",
        rest = "45s"
    )

    // Tríceps
    val exercise4 = Exercise(
        id = "e4",
        name = "Tríceps Testa",
        weight = "15kg",
        repetitions = "3x10",
        rest = "45s",
        obs = "Movimento controlado"
    )

    val exercise16 = Exercise(
        id = "e16",
        name = "Tríceps Corda",
        weight = "20kg",
        repetitions = "4x12",
        rest = "60s"
    )

    val exercise17 = Exercise(
        id = "e17",
        name = "Mergulho entre bancos",
        weight = "Peso corporal",
        repetitions = "3x15",
        rest = "60s"
    )

    // Ombro
    val exercise5 = Exercise(
        id = "e5",
        name = "Desenvolvimento com Halteres",
        weight = "16kg",
        repetitions = "4x10",
        rest = "60s",
        obs = "Evitar estender demais os cotovelos"
    )

    val exercise18 = Exercise(
        id = "e18",
        name = "Elevação Lateral",
        weight = "6kg",
        repetitions = "3x15",
        rest = "45s"
    )

    val exercise19 = Exercise(
        id = "e19",
        name = "Elevação Frontal",
        weight = "8kg",
        repetitions = "3x12",
        rest = "45s"
    )

    // Pernas
    val exercise6 = Exercise(
        id = "e6",
        name = "Agachamento Livre",
        weight = "50kg",
        repetitions = "4x8",
        rest = "90s",
        obs = "Descer até 90 graus"
    )

    val exercise7 = Exercise(
        id = "e7",
        name = "Cadeira Extensora",
        weight = "25kg",
        repetitions = "3x15",
        rest = "60s"
    )

    val exercise20 = Exercise(
        id = "e20",
        name = "Mesa Flexora",
        weight = "20kg",
        repetitions = "3x15",
        rest = "60s"
    )

    val exercise21 = Exercise(
        id = "e21",
        name = "Leg Press",
        weight = "80kg",
        repetitions = "4x10",
        rest = "90s"
    )

    val workoutA = Workout(
        id = "w1",
        name = "Treino A ",
        description = "Peito e Biceps",
        coachId = "c123",
        studentId = "s456",
        exercises = listOf(
            //Peito
            exercise1, exercise8, exercise11, exercise12,
            //// Bíceps
            exercise3, exercise10, exercise15
        )
    )
    val workoutB = Workout(
        id = "w1",
        name = "Treino B ",
        description = "Inferiores",
        coachId = "c123",
        studentId = "s456",
        exercises = listOf(
            // Pernas
            exercise6, exercise7, exercise20, exercise21
        )
    )
    val workoutC = Workout(
        id = "w1",
        name = "Treino C ",
        description = "Costas, Tricpes e Ombro",
        coachId = "c123",
        studentId = "s456",
        exercises = listOf(
            // Costas
            exercise2, exercise9, exercise13, exercise14,
            // Tríceps
            exercise4, exercise16, exercise17,
            // Ombro
            exercise5, exercise18, exercise19
        )
    )
}