package com.hjsolutions.contratos_isp.ui.screen.signature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignatureScreen(){

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()
        .padding(16.dp)) {
        SignatureComponent(
            onSignatureSaved = {

            }
        )

        Spacer(modifier = Modifier.height(16.dp))



    }
}
