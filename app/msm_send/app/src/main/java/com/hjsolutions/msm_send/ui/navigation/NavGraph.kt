package com.hjsolutions.msm_send.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hjsolutions.msm_send.ui.screen.login.LoginScreen
import com.hjsolutions.msm_send.ui.screen.login.LoginViewModel
import com.hjsolutions.msm_send.ui.screen.mainApp.MainApp
import androidx.compose.runtime.setValue
import com.hjsolutions.msm_send.ui.viewmodels.SmsViewModel

@Composable
fun NavGraph(loginViewModel: LoginViewModel , smsViewModel:SmsViewModel )
{

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        composable(Screen.Home.route) {
            MainApp(smsViewModel = smsViewModel)  }
        composable(Screen.Login.route) {
                LoginScreen(navController =  navController , loginViewModel = loginViewModel)
        }
    }
}
