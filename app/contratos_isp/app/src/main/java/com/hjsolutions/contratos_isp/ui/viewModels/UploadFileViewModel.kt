package com.hjsolutions.contratos_isp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjsolutions.contratos_isp.data.models.Documentos
import com.hjsolutions.contratos_isp.data.repository.UploadFileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadFileViewModel() : ViewModel(){

    private val  uploaFileRepository = UploadFileRepository()

    fun saveDocuments(documentos: Documentos){

         viewModelScope.launch {
             try {
                 val photoFileId = if(documentos.photo.isNotEmpty()){
                     uploaFileRepository.uploadFile(documentos.photo)
                 }else{null}

                 //documents
                 val documentFieldIds = if(documentos.document.isEmpty()){
                     uploaFileRepository.uploadMultipleFiles(documentos.document)
                 }else{
                     emptyList()
                 }

                 val anexosFieldIds = if(documentos.anexos.isNotEmpty()){
                     uploaFileRepository.uploadMultipleFiles(documentos.anexos)
                 }else{
                     emptyList()
                 }
             }catch (e:Exception){
                 null
             }
         }

    }
}
