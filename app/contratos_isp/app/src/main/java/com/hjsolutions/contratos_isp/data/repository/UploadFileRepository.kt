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

    suspend fun uploadFile(filePath:String):String?{
        return withContext(Dispatchers.IO){
            try {
                val file = File(filePath)
                if(!file.exists()){
                    return@withContext null
                }

                val inputFile = InputFile.fromFile(file)

                val fileId = ID.unique()
                val uploadedFile = storage.createFile(
                    bucketId = bunked,
                    fileId = fileId,
                    file = inputFile
                )
                uploadedFile.id
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun uploadMultipleFiles(filePaths: List<String>):List<String>{
        return withContext(Dispatchers.IO){
            filePaths.mapNotNull { path ->
                uploadFile(path)
               }
            }
        }
    }

