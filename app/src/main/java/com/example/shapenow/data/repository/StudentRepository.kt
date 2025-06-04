package com.example.shapenow.data.repository

import LastWorkout
import android.util.Log
import com.example.shapenow.data.datasource.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val TAG = "StudentRepository"

class StudentRepository {
    private val data = FirebaseFirestore.getInstance()

    suspend fun getStudentById(studentId: String): User.Student? {
        Log.d(TAG, "Iniciando busca pelo studentId: $studentId")

        // Esta verificação é ESSENCIAL e está funcionando corretamente.
        // Ela te protegeu de um erro mais grave no Firestore.
        if (studentId.isBlank()) {
            Log.w(TAG, "studentId está em branco ou nulo. Retornando null.")
            return null
        }

        return try {
            val studentDoc = data.collection("users").document(studentId).get().await()

            if (studentDoc.exists()) {
                Log.d(TAG, "Documento encontrado para o ID: $studentId")
                val student = studentDoc.toObject(User.Student::class.java)
                Log.d(TAG, "Objeto Student convertido: $student")
                student
            } else {
                Log.w(TAG, "DOCUMENTO NÃO ENCONTRADO no Firestore para o ID: $studentId")
                null
            }
        } catch (e: Exception) {
            // Esta captura de erro vai te salvar quando as regras do Firestore bloquearem um acesso.
            Log.e(TAG, "Erro ao buscar estudante no Firestore para o ID: $studentId", e)
            null
        }
    }
    // Dentro da classe StudentRepository ou UserRepository
    suspend fun updateLastWorkout(studentId: String, workoutId: String) {
        try {
            val lastWorkoutData = LastWorkout(
                workoutId = workoutId,
                completedAt = Timestamp.now()
            )
            // Assumindo que sua coleção de usuários/alunos se chama "users"
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(studentId)
                .update("lastWorkout", lastWorkoutData)
                .await()
            Log.d("StudentRepository", "Último treino atualizado com sucesso.")
        } catch (e: Exception) {
            Log.e("StudentRepository", "Erro ao atualizar último treino", e)
        }
    }
}