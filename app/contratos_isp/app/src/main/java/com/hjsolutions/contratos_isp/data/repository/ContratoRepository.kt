package com.hjsolutions.contratos_isp.data.repository

import android.util.Log
import com.google.gson.Gson
import com.hjsolutions.contratos_isp.api.client.AppWriteClient
import com.hjsolutions.contratos_isp.constants.APPWRITE_BUNKET_ID
import com.hjsolutions.contratos_isp.constants.APPWRITE_DATABASE_ID
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContratoRepository {

    private val cliente = AppWriteClient.client
    private val databases = Databases(cliente)

    private val storage = Storage(cliente)

    suspend fun deleteFileFromStorage(filedId:String) = try {
        storage.deleteFile(bucketId = APPWRITE_BUNKET_ID ,fileId = filedId)
        Result.success(true)
    }catch (e:Exception){
        Result.failure(e)
    }

    suspend fun updatedDocumentsJson(documentId: String , jsonString : String) = try {
        databases.updateDocument(
            databaseId = APPWRITE_DATABASE_ID,
            collectionId = "contratos",
            documentId = documentId,
            data = mapOf("documentos" to jsonString)
        )
        Result.success(true)
    }catch (e:Exception){
        Result.failure(e)
    }


    suspend fun getAllContratoByImplementation(implementacion: String): Result<DocumentList<Map<String,Any>>>{

        return withContext(Dispatchers.IO) {
            try {
                val response = databases.listDocuments(
                    databaseId = APPWRITE_DATABASE_ID,
                    collectionId = "contratos",
                    queries = listOf(
                        Query.equal("implementacion" , implementacion),
                        Query.equal("estado" , 0)
                    )
                )
                //Log.d("ContratoRepository" , "Response completa: $response")
                Result.success(response)
            }catch (e: Exception){
                Result.failure(Exception("error ${e.message}"))
            }
        }
    }

    suspend fun findByDocumentId(documetId : String):Result<Document<Map<String , Any>>>{
        return withContext(Dispatchers.IO){
            try {
                val response = databases.getDocument(
                    databaseId = APPWRITE_DATABASE_ID,
                    collectionId = "contratos",
                    documentId = documetId
                )
                Result.success(response)
            }catch (e:AppwriteException){
                Result.failure(Exception("Error ${e.message}"))
            }
        }
    }
    suspend fun updatedFirma(id :String , path:String):Result<Document<Map<String,Any>>>{
        return withContext(Dispatchers.IO){
            try {
                val response = databases.updateDocument(
                    databaseId = APPWRITE_DATABASE_ID,
                    collectionId = "contratos",
                    documentId = id,
                    data = mapOf("firma" to path)
                )
                Result.success(response)
            }catch (e: AppwriteException){
                Result.failure(Exception("error ${e.message}"))
            }
        }
    }

    suspend fun updatedDocuments(id:String, documentos: Map<String, Any?>):Result<Document<Map<String,Any>>>{
        return withContext(Dispatchers.IO){
            try {
                val jsonString = Gson()
                val documents:String = jsonString.toJson(documentos)
                val response = databases.updateDocument(
                    databaseId = APPWRITE_DATABASE_ID,
                    collectionId = "contratos",
                    documentId = id,
                    data = mapOf("documentos" to documents )
                )
                Result.success(response)
            }catch (e:AppwriteException){
                e.printStackTrace()
                Result.failure(Exception("error ${e.message}"))
            }
        }
    }

    suspend fun updatedStatus(id:String):Result<Document<Map<String, Any>>>{
        return withContext((Dispatchers.IO)){
            try {
                val response = databases.updateDocument(
                    databaseId =  APPWRITE_DATABASE_ID,
                    collectionId = "contratos",
                    documentId = id,
                    data = mapOf("estado" to 1)
                )
                Result.success(response)
            }catch (e:Exception){
                e.printStackTrace()
                Result.failure(Exception("Error actualizando ${e.message}"))
            }
        }
    }
}
