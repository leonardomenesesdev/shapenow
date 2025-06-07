package com.example.shapenow.data.repository

import LastWorkout
import android.util.Log
import com.example.shapenow.data.datasource.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val TAG = "StudentRepository"

class StudentRepository {
    private val db = FirebaseFirestore.getInstance()
    // Certifique-se que "users" é o nome correto da sua coleção de usuários/alunos
    private val usersCollection = db.collection("users")
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
    suspend fun getStudentIdByEmail(email: String): String? {
        return try {
            val querySnapshot = usersCollection
                .whereEqualTo("email", email.trim()) // .trim() para remover espaços extras
                .whereEqualTo("tipo", "student") // Garante que estamos pegando um aluno
                .limit(1) // O email deve ser único para um aluno
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].id // O ID do documento é o UID do usuário
            } else {
                Log.w("StudentRepository", "Nenhum aluno encontrado com o email: $email")
                null
            }
        } catch (e: Exception) {
            Log.e("StudentRepository", "Erro ao buscar aluno por email '$email'", e)
            null
        }
    }
    suspend fun updateStudentProfile(studentId: String, profileData: Map<String, Any>): Result<Unit> {
        if (studentId.isBlank()) {
            Log.e(TAG, "updateStudentProfile falhou: studentId está em branco.")
            return Result.failure(IllegalArgumentException("Student ID cannot be blank."))
        }
        return try {
            usersCollection.document(studentId).update(profileData).await()
            Log.d(TAG, "Perfil do estudante $studentId atualizado com sucesso.")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao atualizar o perfil do estudante $studentId", e)
            Result.failure(e)
        }
    }
}