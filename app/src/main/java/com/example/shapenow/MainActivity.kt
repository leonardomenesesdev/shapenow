package com.example.shapenow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.data.datasource.model.User.Coach
import com.example.shapenow.data.datasource.model.User.Student
import com.example.shapenow.ui.screen.Coach.HomeCoach
import com.example.shapenow.ui.screen.Coach.HomeCoachViewModel
import com.example.shapenow.ui.screen.HomeScreen
import com.example.shapenow.ui.screen.Login.LoginScreen
import com.example.shapenow.ui.screen.register.RegisterScreen
import com.example.shapenow.ui.theme.ShapeNowTheme
import com.example.shapenow.viewmodel.LoginViewModel
import com.example.shapenow.ui.screen.register.RegisterViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            // ViewModels criados uma vez e passados para as telas
            val loginViewModel: LoginViewModel = viewModel()
            val registerViewModel: RegisterViewModel = viewModel()
            val homeCoachViewModel: HomeCoachViewModel = viewModel()

            ShapeNowTheme {
                NavHost(navController = navController, startDestination = "HomeScreen") {
                    composable("HomeScreen") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            HomeScreen(innerPadding, navController)
                        }
                    }
                    composable("LoginScreen") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            LoginScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                loginViewModel = loginViewModel,
                                onLoginSucess = { user ->
                                    when (user) {
                                        is Coach -> {
                                            navController.navigate("HomeCoach/{coachId}")
                                        }
                                        is Student -> {
                                            navController.navigate("HomeScreen")
                                        }
                                        else -> {
                                            navController.navigate("HomeScreen")
                                        }
                                    }
                                }

                            )
                        }
                    }
                    composable("RegisterScreen") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            RegisterScreen(
                                innerPadding = innerPadding,
                                registerViewModel = registerViewModel,
                                onRegisterSuccess = {
                                    // Leva para tela de login ou home após sucesso
                                    navController.navigate("LoginScreen") {}
                                }
                            )
                        }
                    }
                    composable("HomeCoach/{coachId}") { backStackEntry ->
                        val coachId = backStackEntry.arguments?.getString("coachId") ?: ""

                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            HomeCoach(
                                innerPadding = innerPadding,
                                viewModel = homeCoachViewModel,
                                coachId = coachId,
                                onCreateWorkout = {
                                    // ação ao criar treino
                                    // exemplo: navController.navigate("CreateWorkoutScreen")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
