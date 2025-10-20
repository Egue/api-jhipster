package com.hjsolutions.msm_send.ui.navigation

sealed class  Screen(val route:String) {
    object Login: Screen("login")
    object Home : Screen("home")
}
