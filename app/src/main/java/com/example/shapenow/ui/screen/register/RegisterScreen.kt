package com.example.shapenow.ui.screen.register

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shapenow.R
import com.example.shapenow.ui.component.DefaultTextField
import com.example.shapenow.ui.screen.rowdies


@Composable
fun RegisterScreen(innerPadding: PaddingValues){

    var text by remember { mutableStateOf("") }
    var email = "email@gmail.com" //trocar para viewmodel
    var errorMsg = "email invalido" // trocar para viewmodel
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
                    .background(Color(0xFF2F0C6D))
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
                    DefaultTextField(
                        label = "email@example.com",
                        value = email,
                        onValueChange = { newValue ->
                            Log.i("TAG", "Novo valor: $newValue")
                            email = newValue
                        },
                        padding = 10,
                        modifier = Modifier
                    )
                }
            }

        }
    }
}