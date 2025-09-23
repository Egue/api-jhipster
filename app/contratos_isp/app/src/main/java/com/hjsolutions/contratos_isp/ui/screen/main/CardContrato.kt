package com.hjsolutions.contratos_isp.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.data.models.ContratoModels

@Composable
fun CardContrato(contrato: ContratoModels , onViewPdf:()-> Unit){

    Card(modifier = Modifier
        .fillMaxWidth() ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color(0xFF86030E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {

        Column(modifier = Modifier.padding(16.dp)) {
            contrato.contratoClass?.let { detalle ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = detalle.cliente.nombre_cliente , style = MaterialTheme.typography.titleMedium)
                    Text(text = "# ${detalle.contrato.id_contrato}")
                }

                Row(modifier = Modifier.fillMaxWidth() , Arrangement.Start) {
                    Text(text = "")
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ElevatedButton(
                    onClick = {
                        onViewPdf()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE)

                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.PictureAsPdf,
                            contentDescription = "Pdf",
                            tint = Color.White
                        )
                        Text("Ver Pdf" , style = MaterialTheme.typography.bodyLarge , color = Color.White)
                    }
                }
                Spacer(Modifier.width(8.dp))
                ElevatedButton(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE)

                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Doc"
                        )
                        Text("Doc" , style = MaterialTheme.typography.bodyLarge)
                    }
                }

            }


            ////
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ElevatedButton(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)

                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "edit"
                        )
                        Text("Firmar" , style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(Modifier.width(8.dp))
                ElevatedButton(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)

                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.DriveFileRenameOutline,
                            contentDescription = "estado"
                        )
                        Text("Estado" , style = MaterialTheme.typography.bodyLarge)
                    }
                }

            }
            //
        }
    }
}
