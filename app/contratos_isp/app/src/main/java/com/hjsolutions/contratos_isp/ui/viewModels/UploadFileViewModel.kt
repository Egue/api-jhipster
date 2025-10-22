package com.hjsolutions.contratos_isp.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjsolutions.contratos_isp.data.api.Response
import com.hjsolutions.contratos_isp.data.models.Documentos
import com.hjsolutions.contratos_isp.data.repository.ContratoRepository
import com.hjsolutions.contratos_isp.data.repository.UploadFileRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue


class UploadFileViewModel() : ViewModel(){

    private val  uploaFileRepository = UploadFileRepository()

    private val contratoRepository  =   ContratoRepository()

    var response by  mutableStateOf(Response())

    fun resetear(){
        response = response.copy(data = null)
    }

    fun saveDocuments(documentos: Documentos , idDocument:String){

         viewModelScope.launch {
             try {
                 response = response.copy(isLoading = true)
                 val photoFileId = if(documentos.photo.isNotEmpty()){
                     val result  = uploaFileRepository.uploadFile(documentos.photo)
                     result.getOrNull()
                 }else{null}

                 //documents
                 val documentFieldIds = if(documentos.document.isNotEmpty()){
                     uploaFileRepository.uploadMultipleFiles(documentos.document)
                 }else{
                     emptyList()
                 }

                 val documentFielsId = documentFieldIds.mapNotNull { it.getOrNull() }

                 val anexosFieldIds = if(documentos.anexos.isNotEmpty()){
                     uploaFileRepository.uploadMultipleFiles(documentos.anexos)
                 }else{
                     emptyList()
                 }
                 val anexosId = anexosFieldIds.mapNotNull { it.getOrNull() }

                val documentsData = mapOf(
                    "photo" to photoFileId,
                    "document" to documentFielsId,
                    "anexos" to anexosId
                )

                contratoRepository.updatedDocuments(idDocument , documentsData)
                    .onSuccess { document ->
                        response = response.copy(isLoading = false , errorMessage = null , data = document)
                    }.onFailure { error ->
                        response = response.copy(isLoading = false , errorMessage = error.message , data = null)
                    }


             }catch (e:Exception){
                 response = response.copy(isLoading = false , errorMessage = e.message ?: "Error al subir los documentos")
             }finally {
                 response = response.copy(isLoading = false)
             }


         }

    }


}
