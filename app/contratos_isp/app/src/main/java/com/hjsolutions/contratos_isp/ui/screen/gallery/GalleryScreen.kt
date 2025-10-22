package com.hjsolutions.contratos_isp.ui.screen.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.data.models.UiStateOneContrato
import com.hjsolutions.contratos_isp.ui.components.AlertDialogP
import com.hjsolutions.contratos_isp.ui.viewModels.UploadFileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen( documentId:String  ,
                   atras:()->Unit,
                   contratosViewModel: ContratosViewModel ,
                   uploadFileViewModel: UploadFileViewModel){


    val uIstate by contratosViewModel.document.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loaginUplosf by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }


    LaunchedEffect(documentId) {
        contratosViewModel.getContratoByDocumentId(documentId)
    }

    uploadFileViewModel.response.data?.let { data ->
        LaunchedEffect(data) {
            success = true
        }
    }

    uploadFileViewModel.response.isLoading.let { loagin->
        LaunchedEffect(loagin) {
            if(loagin){
                loaginUplosf = true
            }else{
                loaginUplosf = false
            }
        }

    }

    uploadFileViewModel.response.errorMessage?.let { error ->
        LaunchedEffect(error) {
            showDialog = true
            errorMessage = error
        }
    }

    if(showDialog && errorMessage != ""){
        AlertDialogP(text = errorMessage, onDismissRequest = {
            showDialog = false
            errorMessage = ""
        } , onConfirmation = {})
    }

    if(success){
        AlertDialogP(text = "Imagenes cargadas" , onDismissRequest = {
            success = false
            uploadFileViewModel.resetear()
            atras()

        }, onConfirmation = {})
    }

    Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)) {
        TopAppBar(
            title = {
                Text(text = "Selfie y documentos" , style = MaterialTheme.typography.titleMedium , color = Color.White)
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

        /**
         * */
        when(val state = uIstate){
            is UiStateOneContrato.Success ->{
                DocumentsImages(state.contrato.documentos , onDocumentosUpdated = {} , onSaveToAppwrite = {documents ->
                     uploadFileViewModel.saveDocuments(
                        documentos = documents,
                        idDocument = documentId
                    )


                })
            }
            is UiStateOneContrato.Empty -> {

            }
            is UiStateOneContrato.Loading -> {

            }
            is UiStateOneContrato.Error -> {

            }
        }

        if(loaginUplosf){
            BasicAlertDialog(
                onDismissRequest = {
                    loaginUplosf = false
                },

            ) {
                Surface(modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text("Subiendo..")
                        CircularProgressIndicator()
                    }
                }
            }
        }



    }


}


