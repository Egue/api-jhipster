package com.hjsolutions.contratos_isp.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjsolutions.contratos_isp.data.api.Response
import com.hjsolutions.contratos_isp.data.repository.SignatureRepository
import kotlinx.coroutines.launch
import java.io.File
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.hjsolutions.contratos_isp.data.repository.ContratoRepository

/**
 * firmas**/
class SignatureViewModel() : ViewModel(){

    var response by mutableStateOf(Response())

    private val signatureRepository = SignatureRepository()
    private val contratoRepository = ContratoRepository()

    fun save(documentId : String, file :File){
        viewModelScope.launch {
            response = response.copy(isLoading = true)
             signatureRepository.uploadSignature(file)
                 .onSuccess { id ->


                     contratoRepository.updatedFirma(documentId , id)
                         .onSuccess { contrato ->
                             response = response.copy(
                                 isLoading = false,
                                 data = id
                             )
                         }
                         .onFailure { error ->
                             response = response.copy(
                                 isLoading = false,
                                 errorMessage = error.message
                             )
                         }

                 }
                 .onFailure { erro ->
                     response = response.copy(
                         isLoading = false,
                         errorMessage = erro.message
                     )
                 }

        }
    }


    fun getSignature(signature:String){
        viewModelScope.launch {
            response = response.copy(isLoading = true)
            signatureRepository.getFileUrl(signature)
                .onSuccess { file->
                    response = response.copy(
                        isLoading = false,
                        data = file
                    )
                }
                .onFailure {error->
                    response = response.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
        }
    }



}
