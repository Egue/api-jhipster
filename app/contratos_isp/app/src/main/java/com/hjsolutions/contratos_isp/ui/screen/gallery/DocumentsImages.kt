package com.hjsolutions.contratos_isp.ui.screen.gallery

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.hjsolutions.contratos_isp.data.models.CaptureType
import com.hjsolutions.contratos_isp.data.models.Documentos
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.Manifest

@Composable
fun DocumentsImages(
    documentos: Documentos?,
    onDocumentosUpdated: (Documentos) -> Unit,
    onSaveToAppwrite: (Documentos) -> Unit
) {

    val context = LocalContext.current

    var currentDocument by remember { mutableStateOf(documentos ?: Documentos()) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var currentCaptureType by remember { mutableStateOf<CaptureType>(CaptureType.PHOTO) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Usar variable local para evitar problemas de smart cast
            val capturedUri = photoUri
            if (capturedUri != null) {
                val compressedPath = compressImage(context, capturedUri)
                if (compressedPath != null) {
                    currentDocument = when (currentCaptureType) {
                        CaptureType.PHOTO -> currentDocument.copy(photo = compressedPath)
                        CaptureType.DOCUMENT -> currentDocument.copy(
                            document = currentDocument.document + compressedPath
                        )

                        CaptureType.ANEXO -> currentDocument.copy(
                            anexos = currentDocument.anexos + compressedPath
                        )
                    }
                    onDocumentosUpdated(currentDocument)
                }
            }
        }
    }

    fun launchCamera(captureType: CaptureType, useFrontCamera: Boolean = false) {
        currentCaptureType = captureType
        val photoFile = createImageFile(context)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
        photoUri = uri
        cameraLauncher.launch(uri)
    }

    //permiso local
    val cameraPermisionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera(CaptureType.PHOTO, true)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        PhotoSection(
            photoPath = currentDocument.photo,
            onAddClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) === PackageManager.PERMISSION_GRANTED
                ) {
                    launchCamera(CaptureType.PHOTO, useFrontCamera = true)
                } else {
                    cameraPermisionLauncher.launch(Manifest.permission.CAMERA)
                }

            },
            onRemoveClick = {
                currentDocument = currentDocument.copy(photo = "")
                onDocumentosUpdated(currentDocument)
            }
        )
        HorizontalDivider()
        //seccion de documentos
        DocumentSection(
            title = "Documentos",
            documents = currentDocument.document,
            onAddClik = {launchCamera(CaptureType.DOCUMENT)},
            onRemoveClick = {}
        )

        HorizontalDivider()
        DocumentSection(
            title = "Anexos",
            documents = currentDocument.anexos,
            onAddClik = {launchCamera(CaptureType.ANEXO)},
            onRemoveClick = {}
        )

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            //enabled = currentDocument.photo.isNotEmpty()
        ) {
            Text("Guardar", style = MaterialTheme.typography.titleMedium)
        }

    }

}

private fun compressImage(context: android.content.Context, uri: Uri): String? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        // Calcular nuevo tamaño manteniendo aspect ratio
        val maxSize = 1024
        val ratio = minOf(
            maxSize.toFloat() / originalBitmap.width,
            maxSize.toFloat() / originalBitmap.height
        )

        val newWidth = (originalBitmap.width * ratio).toInt()
        val newHeight = (originalBitmap.height * ratio).toInt()

        val resizedBitmap = Bitmap.createScaledBitmap(
            originalBitmap,
            newWidth,
            newHeight,
            true
        )

        // Comprimir a JPEG con calidad 80%
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

        // Guardar archivo comprimido
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val compressedFile = File(context.cacheDir, "IMG_${timeStamp}_compressed.jpg")
        val fos = FileOutputStream(compressedFile)
        fos.write(outputStream.toByteArray())
        fos.close()

        originalBitmap.recycle()
        resizedBitmap.recycle()

        return compressedFile.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

private fun createImageFile(context: android.content.Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.cacheDir
    return File.createTempFile("IMG_${timeStamp}_", ".jpg", storageDir)
}
