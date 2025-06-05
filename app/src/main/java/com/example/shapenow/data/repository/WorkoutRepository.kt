package com.example.shapenow.data.repository

import android.util.Log
import android.util.Log.e
import com.example.shapenow.data.datasource.model.Exercise
import com.example.shapenow.data.datasource.model.Student
import com.example.shapenow.data.datasource.model.Workout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val auth = Firebase.auth
    private val data = FirebaseFirestore.getInstance()
    private val workoutsCollection = data.collection("workouts")
    private val usersCollection = data.collection("users")

    suspend fun addWorkout(workout: Workout): Result<Void?> {
        return try {
            val db = FirebaseFirestore.getInstance()
            db.collection("workouts")
                .document(workout.id)
                .set(workout)
                .await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getWorkoutById(workoutId: String): Workout? {
        return try {
            val workoutDoc = workoutsCollection.document(workoutId).get().await()
            if(!workoutDoc.exists()) return null
            workoutDoc.toObject(Workout::class.java)
        } catch (e:Exception) {
            Log.e("WorkoutRepository", "Erro ao obter o treino por ID", e)
            null
        }
    }
    suspend fun getWorkoutsForStudent(): List<Workout> {
        val user = auth.currentUser
        Log.d("WorkoutRepository", "User ID: ${user?.email}")
        if(user == null) return emptyList()
        return try{
            val doc = data.collection("students").document(user.uid).get().await()
            if(!doc.exists()) return emptyList()
            val student = doc.toObject(Student::class.java)
            val workoutIds = student?.workouts ?: return emptyList()
            val workouts = mutableListOf<Workout>()
            for(workoutId in workoutIds){
                val workoutDoc = workoutsCollection.document(workoutId).get().await()
                if(workoutDoc.exists()){
                    val workout = workoutDoc.toObject(Workout::class.java)
                    if(workout != null){
                        workouts.add(workout)
                    } else{
                        Log.e("WorkoutRepository", "Treino não encontrado: $workoutId")
                    }
                }
                }
            workouts
            } catch (e: Exception){
                Log.e("WorkoutRepository", "Erro ao obter os treinos", e)
            emptyList()
        }
    }
    suspend fun updateWorkout(workoutId:String, name: String, description: String, exercises: List<String>){
        try{
            val workoutMap = mapOf(
                "name" to name,
                "description" to description,
                "exercises" to exercises
            )
            workoutsCollection.document(workoutId).update(workoutMap).await()
        } catch (e: Exception){
            Log.e("WorkoutRepository", "Erro ao atualizar o treino", e)

        }
    }
    suspend fun deleteWorkout(workoutId: String) {
        val user = auth.currentUser ?: return
        try {
            workoutsCollection.document(workoutId).delete().await()
            data.collection("students").document(user.uid)
                .update("workouts", FieldValue.arrayRemove(workoutId))
                .await()
            Log.d("WorkoutRepository", "Treino deletado com sucesso")
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Erro ao deletar o treino", e)
        }
    }
    suspend fun getWorkoutsByCoach(coachId: String): List<Workout>{
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
                        coachId = doc.getString("coachId") ?: "",
                        studentId = doc.getString("studentId") ?: "",
                        exercises = doc.get("exercises") as List<String>
                    )

                }
        }
        catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getWorkoutsByStudent(studentId: String): List<Workout>{
        return try {
            data.collection("workouts")
                .whereEqualTo("studentId", studentId)
                .get()
                .await()
                .map{ doc ->
                    Workout(
                        id=doc.id,
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        coachId = doc.getString("coachId") ?: "",
                        studentId = doc.getString("studentId") ?: "",
                        exercises = doc.get("exercises") as List<String>
                    )

                }
        }
        catch (e: Exception) {
            emptyList()
        }
    }
    // ... (dentro da classe WorkoutRepository)
    suspend fun getExercisesFromWorkout(workoutId: String): List<Exercise> {
        return try {
            val workoutDoc = workoutsCollection.document(workoutId).get().await()
            if (!workoutDoc.exists()) return emptyList()
            val workout = workoutDoc.toObject(Workout::class.java) ?: return emptyList()
            val exerciseIds = workout.exercises

            val exercisesCollection = data.collection("exercises")
            val exerciseList = mutableListOf<Exercise>()

            for (exerciseId in exerciseIds) {
                val doc = exercisesCollection.document(exerciseId).get().await()
                if (doc.exists()) {
                    doc.toObject(Exercise::class.java)?.let { exercise ->
                        exercise.id = doc.id
                        exerciseList.add(exercise)
                    }
                }
            }

            exerciseList
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Erro ao buscar exercícios do treino", e)
            emptyList()
        }
    }

}


