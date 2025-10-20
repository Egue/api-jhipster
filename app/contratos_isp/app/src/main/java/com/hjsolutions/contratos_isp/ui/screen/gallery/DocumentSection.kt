package com.hjsolutions.contratos_isp.ui.screen.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.ui.components.ImageCard

@Composable
fun DocumentSection(
    title:String,
    documents : List<String?>,
    onAddClik:()->Unit,
    onRemoveClick:()->Unit
){

    Column {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text=title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            IconButton(onClick = onAddClik) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    tint = Color(0xFF6200EE)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if(documents.isEmpty()){
            Text(
                text="No hay $title agregados",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6200EE)

            )
        }else{
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(documents.size){index ->
                    DocumentItem(imagePath = documents[index] , onRemove = onRemoveClick)
                }
            }
        }
    }
}
