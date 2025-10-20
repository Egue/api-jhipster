package com.hjsolutions.contratos_isp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hjsolutions.contratos_isp.constants.APPWRITE_BUNKET_ID
import com.hjsolutions.contratos_isp.constants.APPWRITE_PROJECT_ID
import com.hjsolutions.contratos_isp.constants.APPWRITE_PUBLIC_ENDPOINT
import com.hjsolutions.contratos_isp.ui.viewModels.SignatureViewModel

@Composable
fun ImageCard(
    onDelect: () -> Unit,
    filedId:String?,
    modifier: Modifier = Modifier
){

    val bunkendId = APPWRITE_BUNKET_ID
    val imagenUlr = remember(filedId , bunkendId) {
        "${APPWRITE_PUBLIC_ENDPOINT}/storage/buckets/$bunkendId/files/$filedId/view?project=${APPWRITE_PROJECT_ID}"
    }

    Card(modifier = modifier.fillMaxWidth().height(200.dp).padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){

            AsyncImage(
                model = imagenUlr,
                contentDescription = "Imagen",
                modifier = Modifier.fillMaxSize().padding(4.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onDelect,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Eliminar Imagen",
                        tint = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}
