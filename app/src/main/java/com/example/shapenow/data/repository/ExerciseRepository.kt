package com.example.shapenow.data.repository

import android.util.Log
import com.example.shapenow.data.datasource.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExerciseRepository {
    private val data = FirebaseFirestore.getInstance()
    private val exercisesCollection = data.collection("exercises")
    private val workoutRepository: WorkoutRepository = WorkoutRepository()
    suspend fun getExerciseById(exerciseId: String): Exercise? {
        return try {
            val exerciseDoc = exercisesCollection.document(exerciseId)
                .get()
                .await()
            if (exerciseDoc.exists()) {
                exerciseDoc.toObject(Exercise::class.java)
            } else {
                return null
            }
        } catch (e: Exception) {
            Log.i("ExerciseRepository", "Erro ao buscar exercício - ${e.message}")
            null
        }
    }
    suspend fun getExerciseImg(id: String): String? {
        return try{
            val exerciseDoc = exercisesCollection.document(id).get().await()
            if (exerciseDoc.exists()){
                val exercise = exerciseDoc.toObject(Exercise::class.java)
                 exercise?.img
            }else{
                null
            }
        } catch (e: Exception){
            Log.i("ExerciseRepository", "Erro ao buscar imagem do exercício - ${e.message}")
            null
        }
    }
    //pega todos os exercicios
    suspend fun getExercises(): List<Exercise>{
        return try {
            val exercises = data.collection("exercises").get().await()
            exercises.map{ doc ->
                Exercise(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    weight = doc.getString("weight") ?: "",
                    repetitions = doc.getString("repetitions") ?: "",
                    rest = doc.getString("rest") ?: "",
                    img = doc.getString("img") ?: "",
                    obs = doc.getString("obs") ?: ""
                )
            }
        } catch (e: Exception){
            Log.i("ExerciseRepository", "Erro ao buscar exercícios - ${e.message}")
            emptyList()
        }
    }
    //ORIGINAL Q TAVA DANDO CERTO
//    suspend fun addExercise(exercise: Exercise){
//        try {
//            val doc = exercisesCollection.add(exercise).await()
//            exercisesCollection.document(doc.id).update("id", doc.id).await()
//            Log.i("ExerciseRepository", "Exercício adicionado com sucesso - id = ${doc.id}")
//        } catch (e: Exception){
//            Log.i("ExerciseRepository", "Erro ao adicionar exercício - ${e.message}")
//        }
//    }
    // Em ExerciseRepository.kt
    suspend fun addExercise(exercise: Exercise): String? { // <<< MUDANÇA: Retorna String? (o ID)
        return try {
            val newExerciseDocRef = exercisesCollection.document() // Gera um ID antes de setar
            val exerciseWithId = exercise.copy(id = newExerciseDocRef.id) // Garante que o objeto Exercise tem o ID correto

            newExerciseDocRef.set(exerciseWithId).await() // Usa set com o ID já definido

            // Não é mais necessário o update("id", doc.id) se você setar o objeto Exercise já com o id correto.
            // No entanto, se você quiser manter a lógica de .add() e depois .update(), seria:
            // val doc = exercisesCollection.add(exercise.copy(id = "")).await() // Adiciona sem o ID que você gerou
            // exercisesCollection.document(doc.id).update("id", doc.id).await()
            // Log.i("ExerciseRepository", "Exercício adicionado com sucesso - id = ${doc.id}")
            // return doc.id

            Log.i("ExerciseRepository", "Exercício adicionado com sucesso - id = ${newExerciseDocRef.id}")
            newExerciseDocRef.id // Retorna o ID gerado pelo Firestore e usado no set
        } catch (e: Exception) {
            Log.e("ExerciseRepository", "Erro ao adicionar exercício - ${e.message}")
            null
        }
    }
    suspend fun deleteExercise(exerciseId: String){
        try{
            data.collection("exercises")
                .document(exerciseId)
                .delete()
                .await()
        } catch (e: Exception){
            Log.i("ExerciseRepository", "Erro ao deletar exercício - ${e.message}")
        }
    }
    suspend fun updateExercise(exerciseId: String,
                               newName: String,
                               newWeight: String,
                               newRepetitions: String,
                               newRest: String,
                               newImg: String,
                               newObs: String
    ){
        try{

            val exerciseMap = mapOf(
                "name" to newName,
                "weight" to newWeight,
                "repetitions" to newRepetitions,
                "rest" to newRest,
                "img" to newImg,
                "obs" to newObs
            )
            exercisesCollection.document(exerciseId)
                .update(exerciseMap)
                .await()
        } catch (e: Exception){
            Log.i("ExerciseRepository", "Erro ao atualizar exercício - ${e.message}")
        }
    }
}