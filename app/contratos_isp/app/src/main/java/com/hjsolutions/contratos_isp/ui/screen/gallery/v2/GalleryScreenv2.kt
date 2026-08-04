package com.hjsolutions.contratos_isp.ui.screen.gallery.v2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.hjsolutions.contratos_isp.constants.APPWRITE_BUNKET_ID
import com.hjsolutions.contratos_isp.constants.APPWRITE_PROJECT_ID
import com.hjsolutions.contratos_isp.constants.APPWRITE_PUBLIC_ENDPOINT
import com.hjsolutions.contratos_isp.data.models.UiStateOneContrato

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreenv2(
    documentId: String,
    viewModel: ContratosViewModel,
    atras: () -> Unit
) {
    val state by viewModel.document.collectAsState()
    val primaryRed = Color(0xFF86030E)

    viewModel.getContratoByDocumentId(documentId)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Documentos y Anexos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = atras) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryRed)
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp)) {

            when (val uiState = state) {
                is UiStateOneContrato.Success -> {
                    val contrato = uiState.contrato

                    // SECCIÓN DOCUMENTOS
                    Text("Documentos de Identidad", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    contrato.documentos?.document?.let {
                        FileGrid(
                            files = it,
                            onDelete = { fileId -> viewModel.deleteFile(documentId, fileId, "document") }
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    // SECCIÓN ANEXOS
                    Text("Anexos del Contrato", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    contrato.documentos?.anexos?.let {
                        FileGrid(
                            files = it,
                            onDelete = { fileId -> viewModel.deleteFile(documentId, fileId, "anexos") }
                        )
                    }
                }
                is UiStateOneContrato.Loading -> CircularProgressIndicator(modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ))
                is UiStateOneContrato.Error -> Text("Error: Vuelve a iniciar sesión", color = primaryRed)
                else -> {}
            }
        }
    }
}

@Composable
fun FileGrid(files: List<String>, onDelete: (String) -> Unit) {
    if (files.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Text("No hay archivos cargados", modifier = Modifier.padding(16.dp), color = Color.Gray)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(vertical = 8.dp)
        ) {
            items(files) { fileId ->
                // Reutilizamos tu ImageCard o uno similar
                ImageCardGallery(
                    fileId = fileId,
                    onDelete = { onDelete(fileId) }
                )
            }
        }
    }
}


@Composable
fun ImageCardGallery(fileId: String, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)) {
            // Aquí usas tu componente que carga la imagen de Appwrite
            // Ejemplo: AsyncImage o tu ImageCard actual
            ImageFromAppwrite(fileId)

            // Botón eliminar flotante
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    .size(30.dp)
            ) {
                Icon(Icons.Default.Delete, "Eliminar", tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun ImageFromAppwrite(fileId: String) {
    // Reemplaza con tus IDs reales o pásalos por parámetros



    // Construcción de la URL de previsualización (Preview)
    val imageUrl = "$APPWRITE_PUBLIC_ENDPOINT/storage/buckets/$APPWRITE_BUNKET_ID/files/$fileId/preview?project=$APPWRITE_PROJECT_ID"

    AsyncImage(
        model = imageUrl,
        contentDescription = "Imagen de Appwrite",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop, // Para que la imagen llene el cuadro de la galería
        error = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_report_image), // Icono si falla
        placeholder = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_gallery) // Mientras carga
    )
}
