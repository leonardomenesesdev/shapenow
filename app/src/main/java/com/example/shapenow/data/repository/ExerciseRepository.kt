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

    suspend fun addExercise(exercise: Exercise): String? {
        return try {
            val newExerciseDocRef = exercisesCollection.document()
            val exerciseWithId = exercise.copy(id = newExerciseDocRef.id)

            newExerciseDocRef.set(exerciseWithId).await()


            Log.i("ExerciseRepository", "Exercício adicionado com sucesso - id = ${newExerciseDocRef.id}")
            newExerciseDocRef.id
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