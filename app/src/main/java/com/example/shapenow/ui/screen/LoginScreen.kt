package com.example.shapenow.ui.screen

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shapenow.R


@Composable
fun LoginScreen(innerPadding: PaddingValues){
    var text by remember { mutableStateOf("") }
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
                    modifier = Modifier.padding(top = 100.dp)
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
                    modifier = Modifier.padding(bottom = 250.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Acesse sua conta",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                        text = "Matrícula ou CPF",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        value = text,
                        onValueChange = {text = it},
                        label = {Text("Insira a sua matrícula ou CPF")},
                        singleLine = true,

                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                        text = "Senha",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        value = text,
                        onValueChange = {text = it},
                        label = {Text("Insira a sua senha")},
                        singleLine = true,
                        )
                    Text(
                        text = "Esqueci minha senha",
                        color = Color(0xFF4F44D6),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clickable {
                                // Ação ao clicar (exemplo: navegar para a tela de recuperação de senha)
                            }
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 30.dp)
                        ,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F44D6))
                    ) {
                        Text("Entrar", fontSize = 24.sp)
                    }
                }
            }

        }
    }
}
@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(PaddingValues(start = 16.dp))
}