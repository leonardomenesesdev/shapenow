package com.example.shapenow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.ui.screen.HomeScreen
import com.example.shapenow.ui.screen.LoginScreen
import com.example.shapenow.ui.theme.ShapeNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                val navController = rememberNavController()
                ShapeNowTheme {
                    NavHost(navController = navController, startDestination = "HomeScreen"){
                        composable ("HomeScreen"){
                            Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                                HomeScreen(innerPadding, navController)
                            }
                        }
                        composable ("LoginScreen") {
                            Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                                LoginScreen(innerPadding)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShapeNowTheme {
        Greeting("Android")
    }
}