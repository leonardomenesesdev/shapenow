package com.example.shapenow.data.repository

import com.example.shapenow.data.datasource.model.Workout
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val data = FirebaseFirestore.getInstance()
    suspend fun getWorkouts(coachId: String): List<Workout>{
        return try {
            data.collection("workouts")
                .whereEqualTo("coachId", coachId)
                .get()
                .await()
                .map{ doc ->
                    Workout(
                        id=doc.id,
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        coachId = doc.getString("coachId") ?: ""
                    )

                }
        }
        catch (e: Exception) {
            emptyList()
        }
    }
}