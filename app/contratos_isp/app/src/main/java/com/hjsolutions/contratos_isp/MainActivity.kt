package com.hjsolutions.contratos_isp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hjsolutions.contratos_isp.api.client.AppWriteClient
import com.hjsolutions.contratos_isp.ui.navigation.NavGraph
import com.hjsolutions.contratos_isp.ui.theme.Contratos_ispTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppWriteClient.init(this)
        setContent {
            Contratos_ispTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
