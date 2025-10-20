package com.hjsolutions.contratos_isp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.Color

@Composable
fun AlertDialogP(text:String , onDismissRequest: () -> Unit , onConfirmation : () -> Unit){

    Dialog(onDismissRequest = {onDismissRequest()}) {

        Card(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = text , modifier = Modifier.padding(16.dp))
                Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Center) {
                    ElevatedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF86030E))
                    ) {
                        Text("Cerrar")
                    }
                    /*TextButton(
                      onClick = { onConfirmation() },
                      modifier = Modifier.padding(8.dp),
                    ) {
                      Text("Confirm")
                    }*/
                }
            }
        }
    }
}
