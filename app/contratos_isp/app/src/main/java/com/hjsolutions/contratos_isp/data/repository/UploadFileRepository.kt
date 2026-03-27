package com.hjsolutions.contratos_isp.data.repository

import com.hjsolutions.contratos_isp.api.client.AppWriteClient
import com.hjsolutions.contratos_isp.constants.APPWRITE_BUNKET_ID
import io.appwrite.ID
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class UploadFileRepository {

    private val client = AppWriteClient.client
    private val bunked = APPWRITE_BUNKET_ID

    private val storage = Storage(client)

    suspend fun uploadFile(filePath: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(filePath)
                if (!file.exists()) {
                    return@withContext Result.failure(Exception("El archivo no existe: $filePath"))
                }

                val inputFile = InputFile.fromFile(file)

                val fileId = ID.unique()
                val uploadedFile = storage.createFile(
                    bucketId = bunked,
                    fileId = fileId,
                    file = inputFile
                )
                Result.success(uploadedFile.id)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(Exception(e.message))
            }
        }
    }

    suspend fun uploadMultipleFiles(filePaths: List<String>): List<Result<String>> {
        return withContext(Dispatchers.IO) {
            filePaths.map { path ->
                try {
                    uploadFile(path)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.failure(e)
                }
            }
        }
    }

}


