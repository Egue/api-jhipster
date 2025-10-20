package com.hjsolutions.contratos_isp.ui.screen.signature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.ui.viewModels.SignatureViewModel
import androidx.compose.runtime.getValue
import  androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.hjsolutions.contratos_isp.data.models.UiStateOneContrato
import com.hjsolutions.contratos_isp.ui.components.AlertDialogP
import com.hjsolutions.contratos_isp.ui.components.ImageCard
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignatureScreen(
    signatureViewModel: SignatureViewModel,
    documentId:String,
    atras:()->Unit ,
    contratosViewModel: ContratosViewModel){

    val scope = rememberCoroutineScope()

    var showDialog by remember  { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>("") }

    /**
     * delete*/
    var showDeletingDialog by remember { mutableStateOf(false) }
    var fileToDelete by remember { mutableStateOf<String?>(null) }

    /**/
    val uIstate by contratosViewModel.document.collectAsState()

    LaunchedEffect(documentId) {
        contratosViewModel.getContratoByDocumentId(documentId)
    }


    signatureViewModel.response.errorMessage?.let { error ->
        LaunchedEffect(error) {
            showDialog = true
            errorMessage = error
        }
    }

    if(showDialog && errorMessage != null){
        AlertDialogP(text =errorMessage!! , onDismissRequest = {
            showDialog = false
            errorMessage = null
        }, onConfirmation = {} )
    }

    Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)
        ) {

        TopAppBar(
            title = {
                Text(text = "Firma" , style = MaterialTheme.typography.titleMedium , color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = atras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Atras",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF6200EE)
            )
        )

        SignatureComponent(
            signatureViewModel = signatureViewModel,
            onSignatureSaved = { file ->

                signatureViewModel.save(documentId = documentId, file = file)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
         when (val state = uIstate) {
             is UiStateOneContrato.Loading ->{
                 CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                 Spacer(modifier = Modifier.height(16.dp))
                 Text("Cargando contrato..." , modifier = Modifier.align(Alignment.CenterHorizontally))
             }
             is UiStateOneContrato.Error -> {
                Card(modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Error al cargar la firma",
                        style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(state.message ,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
                 Spacer(modifier = Modifier.height(16.dp))
                 Button(onClick = {contratosViewModel.getContratoByDocumentId(documentId)}) {
                     Text("Reintentar")
                 }
             }
             is UiStateOneContrato.Success -> {

                 ImageCard(  onDelect = {
                     showDeletingDialog = true
                     fileToDelete = state.contrato.firma
                 }, filedId = state.contrato.firma)
             }

             is UiStateOneContrato.Empty -> {

             }

         }


        if(showDeletingDialog && fileToDelete != null){
            AlertDialog(
                onDismissRequest = {showDeletingDialog = false},
                title = { Text("Eliminar imagen") },
                text = { Text("¿Estás seguro de que deseas eliminar esta imagen?") },
                confirmButton = {
                    TextButton(
                        onClick = {

                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {showDeletingDialog = false}) {
                        Text("Cancelar")
                    }
                }
            )
        }


    }
}
