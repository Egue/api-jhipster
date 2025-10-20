package com.hjsolutions.msm_send

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjsolutions.msm_send.ui.navigation.NavGraph
import com.hjsolutions.msm_send.ui.screen.login.LoginViewModel
import com.hjsolutions.msm_send.ui.theme.Msm_sendTheme
import com.hjsolutions.msm_send.ui.viewmodels.SmsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Msm_sendTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                //HomeScreen()
                val loginViewModel : LoginViewModel by viewModels()
                val smsViewModel : SmsViewModel by viewModels()
                NavGraph(loginViewModel = loginViewModel , smsViewModel = smsViewModel )
            }
        }
    }
}

