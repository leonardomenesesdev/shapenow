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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shapenow.data.datasource.model.User.Coach
import com.example.shapenow.data.datasource.model.User.Student
import com.example.shapenow.ui.screen.Coach.CreateExercise.CreateExerciseScreen
import com.example.shapenow.ui.screen.Coach.CreateWorkoutScreen
import com.example.shapenow.ui.screen.Coach.EditWorkout.EditWorkoutScreen
import com.example.shapenow.ui.screen.Coach.EditWorkoutExercise.EditWorkoutExerciseScreen
import com.example.shapenow.ui.screen.Coach.HomeCoach
import com.example.shapenow.ui.screen.Coach.HomeCoachViewModel
import com.example.shapenow.ui.screen.Login.LoginScreen
import com.example.shapenow.ui.screen.register.RegisterScreen
import com.example.shapenow.ui.theme.ShapeNowTheme
import com.example.shapenow.viewmodel.LoginViewModel
import com.example.shapenow.ui.screen.register.RegisterViewModel
import com.example.shapenow.ui.screen.Coach.WorkoutDetail.WorkoutDetailScreen
import com.example.shapenow.ui.screen.Coach.WorkoutDetail.WorkoutDetailViewmodel
import com.example.shapenow.ui.screen.Student.HomeAluno.HomeAluno
import com.example.shapenow.ui.screen.Student.EditProfile.EditProfileAlunoScreen
import com.example.shapenow.ui.screen.Student.Profile.ProfileScreen
import com.example.shapenow.ui.screen.Student.WorkoutDetail.StudentWorkoutDetailScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val registerViewModel: RegisterViewModel = viewModel()
            val homeCoachViewModel: HomeCoachViewModel = viewModel()
//            val createWorkoutViewModel: CreateWorkoutViewmodel = viewModel()
//            val createExerciseViewmodel: CreateExerciseViewmodel = viewModel()

            ShapeNowTheme {
                NavHost(navController = navController, startDestination = "LoginScreen") {
                    composable("LoginScreen") {
                        val loginViewModel: LoginViewModel = viewModel()

                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            LoginScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                loginViewModel = loginViewModel,
                                onLoginSucess = { user ->
                                    when (user) {
                                        is Coach -> {
                                            navController.navigate("HomeCoach/${user.uid}")
                                        }

                                        is Student -> {
                                            navController.navigate("HomeAluno/${user.uid}")
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
                                navController = navController, // <<< ADICIONE ESTA LINHA
                                onCreateWorkout = {
                                    navController.navigate("CreateWorkout")
                                },
                                onCreateExercise = {navController.navigate("CreateExerciseScreen")},
                                onWorkoutClick = { workoutId ->
                                    navController.navigate("WorkoutDetailsScreen/$workoutId")
                                }
                            )
                        }
                    }
                    composable("CreateWorkout") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            CreateWorkoutScreen(
                                paddingValues = innerPadding,
                                onWorkoutCreated = {
                                    navController.popBackStack()
                                },


                                )
                        }
                    }
                    composable("WorkoutDetailsScreen/{workoutId}") { backStackEntry ->
                        val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""

                        // <<< MOVA A CRIAÇÃO DO workoutDetailViewmodel PARA CÁ >>>
                        val workoutDetailViewmodel: WorkoutDetailViewmodel = viewModel()

                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            WorkoutDetailScreen(
                                innerPadding = innerPadding,
                                viewModel = workoutDetailViewmodel, // Passe a nova instância
                                workoutId = workoutId,
                                onNavigateBack = {
                                    // Considerar uma navegação mais segura, talvez popBackStack
                                    // ou passar o coachId de volta para HomeCoach se necessário
                                    navController.popBackStack()
                                },
                                navController
                            )
                        }
                    }
                    composable(("CreateExerciseScreen")) { backStackEntry ->
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            CreateExerciseScreen(
                                innerPadding = innerPadding,
                                onCreate = { (navController.popBackStack()) }
                            )
                        }
                    }

                    //TELAS DE ALUNOS
                    composable ("HomeAluno/{studentId}") { backStackEntry ->
                        val studentId = backStackEntry.arguments?.getString("studentId") ?: ""
                            HomeAluno(
                                navController = navController,
                                studentId = studentId
                            )
                        }
                    composable(
                        route = "workout_detail/{workoutId}",
                        arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""

                        StudentWorkoutDetailScreen(
                            navController = navController,
                            workoutId = workoutId
                        )
                    }
                    composable(
                        route = "edit_workout_exercise_screen/{workoutId}/{exerciseIdToEdit}",
                        arguments = listOf(
                            navArgument("workoutId") { type = NavType.StringType },
                            navArgument("exerciseIdToEdit") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""
                        val exerciseIdToEdit = backStackEntry.arguments?.getString("exerciseIdToEdit") ?: ""


                        EditWorkoutExerciseScreen(
                            navController = navController,
                            workoutId = workoutId,
                            exerciseIdToEdit = exerciseIdToEdit,
                            onExerciseEdited = { navController.popBackStack() } // Volta após salvar
                        )
                    }
                    composable(
                        route = "edit_workout_screen/{workoutId}",
                        arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""
                        EditWorkoutScreen(
                            workoutId = workoutId,
                            onWorkoutUpdated = { navController.popBackStack() } // Volta após salvar
                        )
                    }
                    composable("ProfileScreen") {
                        ProfileScreen(navController = navController)
                    }

                    // <<< ROTA PARA A TELA DE EDIÇÃO >>>
                    composable("EditProfileScreen") {
                        EditProfileAlunoScreen(
                            navController = navController,
                            onProfileUpdated = {
                                // Navega para a tela de perfil e limpa a tela de edição do histórico
                                navController.navigate("ProfileScreen") {
                                    popUpTo("ProfileScreen") { inclusive = true }
                                }
                            })
                }
            }
        }
    }
}
}
