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
                                    // Pega os dados básicos
                                    val name = document.getString("name") ?: "Sem nome"
                                    val tipo = document.getString("tipo")

                                    // <<< ADICIONADO: Pega os dados do perfil >>>
                                    val objetivo = document.getString("objetivo") ?: ""
                                    val peso = document.getString("peso") ?: ""
                                    val altura = document.getString("altura") ?: ""
                                    val imc = document.getString("imc") ?: ""
                                    // Você também poderia pegar 'lastWorkout' e 'workouts' aqui se necessário

                                    val user = when (tipo) {
                                        "coach" -> Coach(uid, name, email)
                                        "student" -> Student(
                                            uid = uid,
                                            name = name,
                                            email = email,
                                            // <<< ADICIONADO: Passa os dados do perfil para o construtor >>>
                                            objetivo = objetivo,
                                            peso = peso,
                                            altura = altura,
                                            imc = imc
                                            // workouts = ...,
                                            // lastWorkout = ...
                                        )
                                        else -> null
                                    }

                                    if (user != null) {
                                        onResult(true, user, null)
                                    } else {
                                        // Este erro agora só acontecerá se o campo 'tipo' estiver realmente ausente
                                        onResult(false, null, "Tipo de usuário desconhecido.")
                                    }
                                } else {
                                    onResult(false, null, "Usuário não encontrado no banco de dados.")
                                }
                            }
                            .addOnFailureListener { e ->
                                onResult(false, null, e.message)
                            }
                    } ?: onResult(false, null, "Erro ao obter UID do usuário.")
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
    fun logout() {
        auth.signOut()
    }
}


