package com.hjsolutions.msm_send.ui.screen.mainApp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hjsolutions.msm_send.ui.componets.BottomNavItem
import com.hjsolutions.msm_send.ui.componets.TopBarHome
import com.hjsolutions.msm_send.ui.screen.home.HomeScreen
import com.hjsolutions.msm_send.ui.theme.Purple40
import com.hjsolutions.msm_send.ui.theme.Purple80

@Composable
fun MainApp(){
    val navController = rememberNavController()

    val bottomNavItem = listOf(
        BottomNavItem("Inicio" , Icons.Default.Home , "home"),
        BottomNavItem("Estadisticas" , Icons.Default.BarChart , "estadistica"),
        BottomNavItem("Historial" , Icons.Default.History , "historial")
    )
    Scaffold(
        topBar = {
            TopBarHome()
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                bottomNavItem.forEach{ item ->
                    NavigationBarItem(
                        selected = navController.currentDestination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route){
                                popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                                launchSingleTop = true
                                restoreState= true
                            }
                        },
                        icon = { Icon(item.icon , contentDescription = item.title , tint = Purple80 , modifier = Modifier.size(30.dp)) },
                        label = { Text(item.title , color = Purple40) }
                    )
                }
            }
        },
        content = {innerPaddin ->
            NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(innerPaddin)){
                composable("home") { HomeScreen() }
            }
        }
    )
}
