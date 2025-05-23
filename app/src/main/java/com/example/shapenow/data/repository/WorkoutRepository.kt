package com.example.shapenow.data.repository

import android.util.Log.e
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Workout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
private val data = FirebaseFirestore.getInstance()
    private val workoutsCollection = data.collection("workouts")
    private val usersCollection = data.collection("users")

    suspend fun addWorkout(workout: Workout): Result<Unit> {
        return try {
            val currentUser = Firebase.auth.currentUser
                ?: return Result.failure(Exception("Usuário não autenticado"))

            // Buscar documento do usuário
            val userDoc = usersCollection.document(currentUser.uid).get().await()
            val userType = userDoc.getString("tipo")

            // Validar se é um coach
            if (userType != "coach") {
                return Result.failure(Exception("Apenas coaches podem criar treinos"))
            }

            workoutsCollection.document(workout.id).set(workout).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


        suspend fun getWorkouts(coachId: String): List<Workout>{
        return try {
            data.collection("workouts")
                .get()
                .await()
                .map{ doc ->
                    Workout(
                        id=doc.id,
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        coachId = doc.getString("coachId") ?: "",
                        studentId = doc.getString("studentId") ?: "",
                        exercises = doc.get("exercises") as List<Exercise>
                    )

                }
        }
        catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getExercises(workoutId: String): Result<List<Exercise>>{
        return try{
            val doc = workoutsCollection.document(workoutId).get().await()
            val workout = doc.toObject(Workout::class.java)
            if(workout != null){
                Result.success(workout.exercises)
            } else{
                Result.failure(Exception("Treino não encontrado"))
            }

        } catch (e: Exception){
            Result.failure(e)
        }
    }

}