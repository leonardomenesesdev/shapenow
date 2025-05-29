package com.example.shapenow.data.repository

import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.datasource.model.User.Student
import com.example.shapenow.data.datasource.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudentRepository {
    private val auth = FirebaseAuth.getInstance()
    private val data = FirebaseFirestore.getInstance()

    suspend fun getStudentLogin(): User.Student?{
        val user = auth.currentUser ?: return null
        val studentDoc = data.collection("students").document(user.uid).get().await()
        if(!studentDoc.exists()) return null
        val student = studentDoc.toObject(Student::class.java)
        return student
    }
//    suspend fun workoutDoneDate(workout: Workout){
//        val user = auth.currentUser ?: return
//        val workoutId = workout.id
//        try{
//            val studentDoc = data.collection("students").document(user.uid).get().await()
//            val snap = studentDoc.get().await()
//            val newWorkout = mapOf(
//                "id" to workoutId,
//                "date" to com.google.firebase.Firebase.TimeStamp.now()
//            )
//        }
//    }
}