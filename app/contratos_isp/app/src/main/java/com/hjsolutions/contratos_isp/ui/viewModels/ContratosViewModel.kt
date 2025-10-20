package com.hjsolutions.contratos_isp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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

class ContratosViewModel() : ViewModel() {

    private var _uiState = MutableStateFlow<UiStateContrato>(UiStateContrato.Loading)
    var uiState: StateFlow<UiStateContrato> = _uiState.asStateFlow()

    private var _document = MutableStateFlow<UiStateOneContrato>(UiStateOneContrato.Loading)
    var document:StateFlow<UiStateOneContrato> = _document.asStateFlow()


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

    fun refreshContrato(implementacion :String){
        loadContratos(implementacion = implementacion)
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


}
