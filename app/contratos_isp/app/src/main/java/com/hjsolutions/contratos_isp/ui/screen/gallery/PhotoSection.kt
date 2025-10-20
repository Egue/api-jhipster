package com.hjsolutions.contratos_isp.ui.screen.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun PhotoSection(
    photoPath :String,
    onAddClick :()->Unit,
    onRemoveClick: () -> Unit
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Foto de Usuario" , style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Box(contentAlignment = Alignment.Center , modifier = Modifier.size(150.dp)){
            if(photoPath.isEmpty()){
                Surface(modifier = Modifier
                    .size(150.dp)
                    .clickable {
                        onAddClick()
                    } ,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    border = ButtonDefaults.outlinedButtonBorder()) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)) {
                        //icon
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Agregar Imagen",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text="Agregar Foto" , style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary)
                    }

                }
            }else{
                //Mostrar foto
                Box{
                    Image(
                        painter = rememberAsyncImagePainter(File(photoPath)),
                        contentDescription = "Foto Cliente",
                        modifier = Modifier.size(150.dp).clip(CircleShape)
                            .border(2.dp , MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    //eliminar

                }
            }
        }
    }

}
