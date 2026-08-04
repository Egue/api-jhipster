package com.hjsolutions.contratos_isp.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hjsolutions.contratos_isp.ui.viewModels.LoginViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.hjsolutions.contratos_isp.R
import com.hjsolutions.contratos_isp.ui.components.AlertDialogP

@Composable
fun LoginScreen(loginViewModel: LoginViewModel){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>("") }

loginViewModel.uiState.errorMessage?.let { error->
    LaunchedEffect(error) {
        showDialog = true
        errorMessage = error
    }
}


    if(showDialog && errorMessage != null){
        AlertDialogP(text = errorMessage!! , onDismissRequest = {
            showDialog = false
            errorMessage = null
            loginViewModel.clearError()
        } , onConfirmation = {
            showDialog = false
        })
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.navigationBars)

    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF86030E), Color(0xFF3D0215))
            ))) {
            Column (
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 20.dp, top = 50.dp)
            ) {
                Text("Hola" , fontSize = 35.sp , color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Text("Inicia Sesión !!" , fontSize = 35.sp, color = Color.White , fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(80.dp))
            Card (
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp).fillMaxSize()) {

                    Spacer(Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.logohj),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Usuario") },
                        singleLine=true,
                        modifier = Modifier.fillMaxWidth().background(Color.Transparent),
                        shape = RoundedCornerShape(15.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6200EE), // Morado cuando está enfocado
                            unfocusedBorderColor = Color(0xFF757575), // Gris cuando no está enfocado
                            focusedLabelColor = Color(0xFF6200EE), // Color del label cuando está enfocado
                            unfocusedLabelColor = Color(0xFF757575), // Color del label cuando no está enfocado
                            cursorColor = Color(0xFF6200EE), // Color del cursor
                            focusedTextColor = Color(0xFF000000), // Color del texto cuando está enfocado
                            unfocusedTextColor = Color(0xFF424242), // Color del texto cuando no está enfocado
                            focusedPlaceholderColor = Color(0xFF9E9E9E), // Color del placeholder cuando está enfocado
                            unfocusedPlaceholderColor = Color(0xFFBDBDBD) // Color del placeholder cuando no está enfocado
                        )

                    )
                    Spacer(Modifier.height(20.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text("Contraseña") },
                        singleLine=true,
                        modifier = Modifier.fillMaxWidth().background(Color.Transparent),
                        shape = RoundedCornerShape(15.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6200EE), // Morado cuando está enfocado
                            unfocusedBorderColor = Color(0xFF757575), // Gris cuando no está enfocado
                            focusedLabelColor = Color(0xFF6200EE), // Color del label cuando está enfocado
                            unfocusedLabelColor = Color(0xFF757575), // Color del label cuando no está enfocado
                            cursorColor = Color(0xFF6200EE), // Color del cursor
                            focusedTextColor = Color(0xFF000000), // Color del texto cuando está enfocado
                            unfocusedTextColor = Color(0xFF424242), // Color del texto cuando no está enfocado
                            focusedPlaceholderColor = Color(0xFF9E9E9E), // Color del placeholder cuando está enfocado
                            unfocusedPlaceholderColor = Color(0xFFBDBDBD) // Color del placeholder cuando no está enfocado
                        )

                        )
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End) {
                        Text("Olvido su contraseña?" , fontSize = 20.sp ,  fontWeight = FontWeight.Bold, color = Color(0xFF3D0215))
                    }
                    Spacer(Modifier.height(30.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {

                        ElevatedButton(modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                loginViewModel.login(email = email , password = password)
                                //loginViewModel.ping()
                            }, colors = ButtonDefaults.buttonColors(Color(0xFF86030E))) {
                            if (loginViewModel.uiState.isLoading){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Transparent),  // Centrado en el Box
                                    color = Color.White,
                                    trackColor = Color.Transparent)
                            }else {
                                Text("Iniciar Sesión", color = Color.White)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End , verticalAlignment = Alignment.Bottom) {
                        Text("Desarrollado por HJSolutions",color = Color(0xFF86030E))
                    }

                }
            }
        }

    }
}
