package com.hjsolutions.contratos_isp.ui.screen.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.File


@Composable
fun DocumentItem(
    imagePath: String?,
    onRemove: () -> Unit
) {
    Box() {
        Image(
            painter = rememberAsyncImagePainter(File(imagePath)),
            contentDescription = "Documento",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        IconButton (
            onClick = onRemove,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(16.dp)
                .offset(x = (-4).dp, y = 4.dp)

        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Eliminar",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
