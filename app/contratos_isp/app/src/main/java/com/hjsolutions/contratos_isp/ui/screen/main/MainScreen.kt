package com.hjsolutions.contratos_isp.ui.screen.main

import android.net.Uri
import android.util.Log
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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel
import com.hjsolutions.contratos_isp.ui.viewModels.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
    loginViewModel: LoginViewModel ,
    contratosViewModel: ContratosViewModel) {
    val uIstate = loginViewModel.uiState
    var user : String by remember { mutableStateOf("") }
    LaunchedEffect(uIstate.user) {
        uIstate.user?.let { userObj ->
            user = userObj.name
            contratosViewModel.initializeWithUser(user = userObj)
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x0FF8C0C0))) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF86030E), Color(0xFF3D0215))
                            ),
                            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)
                        )


                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth().padding(20.dp),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "User Account",
                                tint = Color(0xFF86030E),
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Text(text = uIstate.user!!.name, fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.White)
                        Spacer(Modifier.height(10.dp))
                        IconButton (
                           colors = IconButtonDefaults.iconButtonColors(
                               contentColor = Color.Transparent                           )

                           ,
                            onClick = {
                            loginViewModel.logout()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout ,
                                contentDescription = "Logout",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp))
                        }
                    }
                }


            ListContratos(
                contratosViewModel = contratosViewModel ,
                implementacion = user ,
                onViewPdf = {path ->
                    Log.d("MainScreen", "${path}")
                    //contratosViewModel.navigateToPdf(url = path , title = "Contrato ISP")
                    navController.navigate("pdf_viewer/${Uri.encode(path)}")
                }
                )
        }

    }

}
