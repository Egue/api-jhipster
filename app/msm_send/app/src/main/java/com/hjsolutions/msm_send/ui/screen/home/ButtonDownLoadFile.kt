package com.hjsolutions.msm_send.ui.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hjsolutions.msm_send.ui.theme.Purple40

@Composable
fun ButtonDownLoadFile( onClick : () -> Unit ){

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Purple40
        )
    ) {

        Icon(imageVector = Icons.Default.Downloading , contentDescription = null , tint = Color.White)
        Text(text = "Descargar desde API")
    }
}
