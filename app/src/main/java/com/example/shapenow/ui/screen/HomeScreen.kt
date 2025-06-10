package com.example.shapenow.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shapenow.R
import com.example.shapenow.ui.theme.buttonColor

val rowdies = FontFamily(Font(R.font.rowdies_bold))
@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.bg_photo),
            contentDescription = "Foto de um homem treinando",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
           modifier = Modifier
               .fillMaxSize()
               .background(Color(0x88000000))
        ) {
            Box(
                modifier = Modifier.width(40.dp).fillMaxSize()
                    .background(buttonColor)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
            Column (
                modifier = Modifier.padding(top = 130.dp)
            ){
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 46.sp,
                                fontFamily = rowdies,
                                color = Color.White
                            )
                        ) { append("SHAPE") }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = rowdies,
                                fontSize = 46.sp,
                                color = Color(0xFF4F44D6)
                            )
                        ) { append("NOW!") }
                    }
                )

            }

                Column(
                    modifier = Modifier.padding(bottom = 400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Clique aqui para entrar",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {                            Log.i("RegisterScreen", "Botão de registrar clicado")
                            navController.navigate("LoginScreen")},
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F44D6))
                    ) {
                        Text("Acessar", fontSize = 24.sp)
                    }

                }
            }

        }
    }
}
@Preview
@Composable
fun HomePreview(){
    val navController = rememberNavController()
    HomeScreen(PaddingValues(16.dp), navController)
}