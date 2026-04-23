package co.com.intertnetinalambrico.intinalambrico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.com.intertnetinalambrico.intinalambrico.ui.screen.LoginScreen
import co.com.intertnetinalambrico.intinalambrico.ui.screen.home.HomeScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route // Inicia en el Login
    ) {
        composable(Screen.Login.route) {
            LoginScreen (onLoginSuccess = {role->
                // Al tener éxito, navegamos al Home y borramos el Login del historial
                navController.navigate("home/$role") {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable("home/{role}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "user"
            HomeScreen(
                role = role,
                onLogoutSuccess = {
                    navController.navigate("login") {
                        popUpTo("home/{role}") { inclusive = true }
                    }
                }
            )
        }
    }
}
