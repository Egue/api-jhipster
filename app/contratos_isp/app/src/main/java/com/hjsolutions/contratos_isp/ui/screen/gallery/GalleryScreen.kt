package com.hjsolutions.contratos_isp.ui.screen.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hjsolutions.contratos_isp.data.models.UiStateOneContrato

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen( documentId:String  ,
                   atras:()->Unit,
                   contratosViewModel: ContratosViewModel){


    val uIstate by contratosViewModel.document.collectAsState()

    LaunchedEffect(documentId) {
        contratosViewModel.getContratoByDocumentId(documentId)
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
                DocumentsImages(state.contrato.documentos , onDocumentosUpdated = {} , onSaveToAppwrite = {})
            }
            is UiStateOneContrato.Empty -> {

            }
            is UiStateOneContrato.Loading -> {

            }
            is UiStateOneContrato.Error -> {

            }
        }
    }


}


