package com.hjsolutions.contratos_isp.ui.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.hjsolutions.contratos_isp.data.api.Response
import com.hjsolutions.contratos_isp.data.models.ContratoInfo
import com.hjsolutions.contratos_isp.data.models.ContratoModels
import com.hjsolutions.contratos_isp.data.models.Documentos
import com.hjsolutions.contratos_isp.data.models.UiStateContrato
import com.hjsolutions.contratos_isp.data.models.UiStateOneContrato
import com.hjsolutions.contratos_isp.data.repository.ContratoRepository
import com.hjsolutions.contratos_isp.ui.sealed.NavigationEvent
import kotlinx.coroutines.launch
import io.appwrite.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ContratosViewModel() : ViewModel() {

    private var _uiState = MutableStateFlow<UiStateContrato>(UiStateContrato.Loading)
    var uiState: StateFlow<UiStateContrato> = _uiState.asStateFlow()

    private var _document = MutableStateFlow<UiStateOneContrato>(UiStateOneContrato.Loading)
    var document:StateFlow<UiStateOneContrato> = _document.asStateFlow()

    var response by mutableStateOf(Response())

    val contratoRepository = ContratoRepository()

    private val gson = Gson()

    //navigations
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()
    ///

    fun initializeWithUser(user : User<Map<String, Any>>) {
        var implementacion = user.name
        loadContratos(implementacion = implementacion)
    }


    fun getContratoByDocumentId(documentId:String){
        if(documentId.isBlank()){
            _document.value = UiStateOneContrato.Error("El documento debe tener un string")
            return
        }
        viewModelScope.launch {
            _document.value = UiStateOneContrato.Loading
            contratoRepository.findByDocumentId(documentId)
                .onSuccess { document ->
                    if(document.data.isEmpty()){
                        _document.value  = UiStateOneContrato.Empty
                        return@onSuccess
                    }
                    val contratoString = document.data["contrato"] as? String ?: ""
                    val contratoInfo = parseContratoJson(contratoString)
                    val documents = parseDocumetsJson(document.data["documentos"] as? String ?: "")

                    val contrato =  ContratoModels(
                        id = document.id,
                        id_contrato = document.data["id_contrato"] as? String?: "",
                        id_servicio = document.data["id_servicio"] as? String?: "",
                        id_cliente = document.data["id_cliente"] as? String?: "",
                        implementacion = document.data["implementacion"] as? String?:"",
                        path_contrato = document.data["path_contrato"] as? String?: "",
                        photo = document.data["photo"] as? String?: "",
                        estado = document.data["estado"] as? Number?: 0,
                        contratoClass = contratoInfo,
                        contrato = contratoString,
                        firma = document.data["firma"] as? String ?: "",
                        documentos = documents

                    )
                    _document.value = UiStateOneContrato.Success(contrato)
                }
        }
    }

    private fun loadContratos(implementacion: String) {

        if(implementacion.isBlank()){
            _uiState.value = UiStateContrato.Error("la implementacion no puede estar vacias")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiStateContrato.Loading
            contratoRepository.getAllContratoByImplementation(implementacion = implementacion)
                .onSuccess { list ->
                    if(list.documents.isEmpty()){
                        _uiState.value = UiStateContrato.Empty
                        return@onSuccess
                    }
                    val contratos = list.documents.map { document ->
                        Log.d("ContratosViewModel" , "${document}")
                        val contratoString = document.data["contrato"] as? String ?: ""
                        val contratoInfo = parseContratoJson(contratoString)
                        val documents = parseDocumetsJson(document.data["documentos"] as? String ?: "")

                        ContratoModels(
                            id = document.id,
                            id_contrato = document.data["id_contrato"] as? String?: "",
                            id_servicio = document.data["id_servicio"] as? String?: "",
                            id_cliente = document.data["id_cliente"] as? String?: "",
                            implementacion = document.data["implementacion"] as? String?:"",
                            path_contrato = document.data["path_contrato"] as? String?: "",
                            photo = document.data["photo"] as? String?: "",
                            estado = document.data["estado"] as? Number?: 0,
                            contratoClass = contratoInfo,
                            contrato = contratoString,
                            firma = document.data["firma"] as? String ?: "",
                            documentos =  documents

                        )
                    }
                    _uiState.value = UiStateContrato.Success(contratos)
                }
                .onFailure { error ->
                    _uiState.value = UiStateContrato.Error("error al cargar los contratos ${error.message}")
                }
        }
    }

    private fun parseDocumetsJson(document:String):Documentos?{
        return try {
            if(document.isNotBlank()){
                gson.fromJson(document , Documentos::class.java)
            }else{
                null
            }
        }catch (e: JsonSyntaxException) {
            null
        }
    }

    private fun parseContratoJson(contratoString: String): ContratoInfo? {
        return try {
            if (contratoString.isNotBlank()) {
                gson.fromJson(contratoString, ContratoInfo::class.java)
            } else {
                null
            }
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun filterContratos(idDocument :String){
        val currentState = _uiState.value
        if(currentState is UiStateContrato.Success){
            val filtrados = currentState.contratos.filter { contrato ->
                contrato.id != idDocument
            }
            _uiState.value = if(filtrados.isEmpty()){
                UiStateContrato.Empty
            }else{
                UiStateContrato.Success(filtrados)
            }
        }
    }

    fun refreshContrato(implementacion: String){
        loadContratos(implementacion = implementacion)
    }

    fun updatedContrato(id:String){
        viewModelScope.launch {
            response = response.copy(isLoading = true)
            contratoRepository.updatedStatus(id)
                .onSuccess { document ->
                    response = response.copy(isLoading = false , data = document)
                }
                .onFailure { error ->
                    response = response.copy(isLoading = false , errorMessage = error.message)
                }
        }
    }

    fun refreshData(){
        response = response.copy(isLoading = false, data = null , errorMessage = null)
    }


    //navegando al pdf
    fun navigateToPdf(url:String, title:String){
        _navigationEvent.tryEmit(NavigationEvent.NavigateToPdf(url, title))
    }

    //*actualizando
    fun updatedSignature(documentId:String, signature:String){
        viewModelScope.launch {
            contratoRepository.updatedFirma(
                id = documentId,
                path = signature
            )
        }
    }

    fun deleteFile(documentId: String, fileId: String, type: String) {
        viewModelScope.launch {
            response = response.copy(isLoading = true)

            // 1. Borrar el archivo físico del Storage de Appwrite
            contratoRepository.deleteFileFromStorage(fileId)
                .onSuccess {
                    // 2. Si se borró del storage, actualizamos el JSON en la DB
                    val currentState = _document.value
                    if (currentState is UiStateOneContrato.Success) {
                        val currentDocs = currentState.contrato.documentos ?: Documentos()

                        // 3. Modificamos la lista correspondiente (document o anexos)
                        val updatedDocs = when (type) {
                            "document" -> {
                                currentDocs.copy(document = currentDocs.document.filter { it != fileId })
                            }
                            "anexos" -> {
                                currentDocs.copy(anexos = currentDocs.anexos.filter { it != fileId })
                            }
                            else -> currentDocs
                        }

                        // 4. Convertimos el objeto de nuevo a JSON string
                        val jsonString = gson.toJson(updatedDocs)

                        // 5. Guardamos el JSON actualizado en Appwrite
                        contratoRepository.updatedDocumentsJson(documentId, jsonString)
                            .onSuccess {
                                response = response.copy(isLoading = false)
                                // Refrescamos los datos para que la UI se actualice
                                getContratoByDocumentId(documentId)
                            }
                            .onFailure { error ->
                                response = response.copy(
                                    isLoading = false,
                                    errorMessage = "Error al actualizar DB: ${error.message}"
                                )
                            }
                    }
                }
                .onFailure { error ->
                    // Si falla el borrado del archivo (ej. no existe o sesión expirada)
                    val msg = if (error.message?.contains("401") == true) "Vuelve a iniciar sesión" else error.message
                    response = response.copy(isLoading = false, errorMessage = msg)
                }
        }
    }


}
