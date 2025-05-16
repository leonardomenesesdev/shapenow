package com.example.shapenow.data.repository
import com.example.shapenow.data.datasource.model.User
import com.example.shapenow.data.datasource.model.User.Coach
import com.example.shapenow.data.datasource.model.User.Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository{
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    fun login(
        email: String,
        senha: String,
        onResult: (Boolean, User?, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    uid?.let {
                        firestore.collection("users").document(it).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val name = document.getString("name")
                                    val user = if (email.endsWith("@unifor.br")) {
                                        Coach(uid, name, email)
                                    } else {
                                        Student(uid, name, email)
                                    }
                                    onResult(true, user, null)
                                } else {
                                    onResult(false, null, "Usuário não encontrado.")
                                }
                            }
                            .addOnFailureListener { e ->
                                onResult(false, null, e.message)
                            }
                    } ?: onResult(false, null, "Erro ao obter UID.")
                } else {
                    onResult(false, null, task.exception?.message)
                }
            }
    }

    fun register(email: String, senha: String, name: String, onResult: (Boolean, String?) -> Unit){
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    val user: User = if (email.endsWith("unifor.br")) {
                        User.Coach(uid, name, email)
                    } else {
                        User.Student(uid, name, email)
                    }
                    uid?.let {
                        firestore.collection("users").document(it)
                            .set(user)
                            .addOnCompleteListener {
                                onResult(true, null)
                            }
                            .addOnFailureListener { e ->
                                onResult(false, e.message)
                            }
                    } ?: onResult(false, "Erro ao obter UID do usuário.")
                } else {
                    onResult(false, task.exception?.message)
                    }
                }
            }
}


