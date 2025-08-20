package com.hjsolutions.msm_send.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hjsolutions.msm_send.ui.screen.login.LoginScreen
import com.hjsolutions.msm_send.ui.screen.mainApp.MainApp

@Composable
fun NavGraph( )
{

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        composable(Screen.Home.route) { MainApp()  }
        composable(Screen.Login.route) {
                LoginScreen(navController)
        }
    }
}
