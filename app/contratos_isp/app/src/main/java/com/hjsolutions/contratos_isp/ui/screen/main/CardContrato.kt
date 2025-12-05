package com.hjsolutions.contratos_isp.ui.screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.data.models.ContratoModels

@Composable
fun CardContrato(
    contrato: ContratoModels ,
    onViewPdf:()-> Unit ,
    onFirma:()-> Unit ,
    onDoc: () -> Unit){

    Card(modifier = Modifier
        .fillMaxWidth()
        .drawWithContent {
            drawContent()
            // Dibuja solo el borde izquierdo con esquinas redondeadas
            val borderWidth = 12.dp.toPx()
            drawRoundRect(
                color = Color.Blue,
                topLeft = Offset.Zero,
                size = Size(borderWidth, size.height),
                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            contrato.contratoClass?.let { detalle ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = detalle.cliente.nombre_cliente ,
                        style = MaterialTheme.typography.titleMedium , color = Color.DarkGray)
                    Text(text = "# ${detalle.contrato.id_contrato}")
                }

                Row {
                    Text(text = "No. Documento: ${detalle.cliente.documento_cliente}", color = Color.Black)
                }

                Row(modifier = Modifier.fillMaxWidth() , Arrangement.Start) {
                    Text(text = "Tarifa: $ ${detalle.tarifa.valor} ", color = Color.Black)
                    Text(text = "Tecnologia: ${detalle.tarifa.tecnologia}", color = Color.Black)
                }

                Row(modifier = Modifier.fillMaxWidth() , Arrangement.Start) {

                    Text(text = "Velocidad: ${detalle.tarifa.velocidad} Mbp", color = Color.Black)
                }

                Row (modifier = Modifier.fillMaxWidth() , Arrangement.Start){
                    Text(text = "Dirección: ${detalle.contrato.direccionResidencia.nomenclatura}", color = Color.Black)
                }

                HorizontalDivider(modifier = Modifier.fillMaxWidth(),
                    thickness = DividerDefaults.Thickness , color = Color.Black)

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
                    onClick = {
                        onDoc()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE)

                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Doc",
                            tint = Color.White
                        )
                        Text("Doc" , style = MaterialTheme.typography.bodyLarge, color = Color.White)
                    }
                }

            }


            ////
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ElevatedButton(
                    onClick = {
                        onFirma()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)

                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "edit",
                            tint = Color.White
                        )
                        Text("Firmar" , style = MaterialTheme.typography.bodyLarge , color = Color.White)
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
                            contentDescription = "estado",
                            tint = Color.White
                        )
                        Text("Estado" , style = MaterialTheme.typography.bodyLarge , color = Color.White)
                    }
                }

            }
            //
        }
    }
}
