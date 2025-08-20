package com.hjsolutions.msm_send.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hjsolutions.msm_send.ui.theme.Purple40
import com.hjsolutions.msm_send.ui.theme.PurpleGrey80
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController ,  viewModel: LoginViewModel = viewModel())
{
    val state by viewModel.loginState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = Purple40) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp , start = 16.dp , end = 16.dp)) {

            Box(modifier = Modifier.size(80.dp).clip(RoundedCornerShape(20.dp)).background(Color.White),
                contentAlignment = Alignment.Center
                ){
                Icon(imageVector = Icons.Default.Sms , contentDescription = null , tint = Purple40)
            }
            Spacer(Modifier.height(10.dp))
            Text(text = "AutoSMS" , fontSize = 30.sp , color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text(text = "Mensajeria Automatizada" , color =  PurpleGrey80 )

            CardLoginScreen(onClickLogin = { username , password ->
                viewModel.login(username , password)
            })

            when(state){
                is LoginState.Loading -> CircularProgressIndicator()
                is LoginState.Error -> Text((state as LoginState.Error).message, color = MaterialTheme.colorScheme.error)
                is LoginState.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("home"){
                            popUpTo("login"){inclusive = true}
                        }
                    }
                }
                else -> {}
            }
        }

    }
}
