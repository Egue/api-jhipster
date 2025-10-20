package com.hjsolutions.msm_send.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hjsolutions.msm_send.ui.theme.Purple40
import com.hjsolutions.msm_send.ui.theme.PurpleGrey80
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun CardLoginScreen(onClickLogin : (username:String , password:String) -> Unit){

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(contentColor = Purple40 , containerColor = Color.White)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().height(400.dp).padding(16.dp)) {

            Text(text = "Iniciar Sesión" , fontSize = 20.sp , fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text(text = "Accede a tu cuenta para continuar", color = PurpleGrey80)
            Spacer(Modifier.height(10.dp))

            //usuario
            OutlinedTextField(
                value = username,
                onValueChange = {username = it},
                label = { Text("Usuario") } ,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountBox , contentDescription = null , tint = Purple40)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor  = Purple40 ,  unfocusedTextColor  = Purple40)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Contraseña") } ,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock , contentDescription = null , tint = Purple40)
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Purple40 ,  unfocusedTextColor = Purple40)

            )

            Spacer(Modifier.height(20.dp))
            ElevatedButton(
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClickLogin(username , password)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple40 , contentColor = Color.White)
            ) {
                Text("Iniciar Sesión" , fontWeight = FontWeight.Bold)
            }

        }

    }
}
