package com.example.shapenow.data.repository

import android.util.Log
import com.example.shapenow.data.datasource.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExerciseRepository {
    private val data = FirebaseFirestore.getInstance()
    private val exercisesCollection = data.collection("exercises")
    private val workoutRepository: WorkoutRepository = WorkoutRepository()
    suspend fun getExercise(exerciseId: String): Exercise? {
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
            Log.i("ExerciseRepository", "Erro ao buscar exercício")
            null
        }
    }

    suspend fun addExercise(exercise: Exercise): Result<Unit> {
        return try {
            exercisesCollection.document(exercise.id).set(exercise).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}