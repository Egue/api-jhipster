package com.hjsolutions.contratos_isp.data.repository

import com.hjsolutions.contratos_isp.api.client.AppWriteClient
import com.hjsolutions.contratos_isp.constants.APPWRITE_BUNKET_ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class SignatureRepository{

    private val client = AppWriteClient.client
    private val bunked = APPWRITE_BUNKET_ID

    private val storage = Storage(client)

    suspend fun uploadSignature(file:File):Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = storage.createFile(
                    bucketId = bunked,
                    fileId = "signature_${System.currentTimeMillis()}",
                    file = InputFile.fromFile(file)
                )

                Result.success(response.id)

            }catch (e:AppwriteException){
                  Result.failure(Exception("Error subiendo ${e.message}"))
            }
        }
    }

    suspend fun getFileUrl(url :String):Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = storage.getFileView(
                    bucketId = bunked,
                    fileId = url
                ).toString()

                Result.success(response)
            }catch (e: AppwriteException){

                Result.failure(Exception("Error buscando archivo ${e.message}"))
            }
        }
    }
}
