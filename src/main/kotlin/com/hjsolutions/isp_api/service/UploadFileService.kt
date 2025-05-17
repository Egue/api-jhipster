package com.hjsolutions.isp_api.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import io.minio.MinioClient
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;

@Service
class UploadFileService(private val globalService: GlobalService) {
    // This service class can be used to implement business logic related to file uploads
    // For example, you can add methods to handle file processing, storage, etc.
    // Currently, it's empty and can be filled in as needed.

    val url = "http://minio.server.cableytv.com:9000"
    val accessKey = "MUTmSGgHV5YsUe09"
    val secretKey = "qIC42Co3Qi6DZz4ERU2fkEZ4Eh10CrS0"

    fun uploadFile(file: MultipartFile , bucket:String , width : Int , height:Int): String {
        // Handle the file upload logic here
        // For example, save the file to a specific location or process it as needed
        try {
            val minioClient = MinioClient.builder()
            .endpoint(url)
            .credentials("MUTmSGgHV5YsUe09" , "qIC42Co3Qi6DZz4ERU2fkEZ4Eh10CrS0")
            .build()

         if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build()
            )
        }
        val resizedImage = globalService.resizeImage(file, width, height)
        val originalName = file.originalFilename ?: "unnamed"
        val timestamp = System.currentTimeMillis()
        val sanitizedFilename = originalName.replace("\\s+".toRegex(), "_")
        val newFileName = "${timestamp}_$sanitizedFilename"

        minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucket)
                    .`object`(newFileName)
                    .stream(resizedImage, resizedImage.available().toLong(), -1)
                    .contentType(file.contentType)
                    .build()
            )
        

        return "$url/$bucket/$newFileName"
        }
        catch(e : MinioException) {
            throw RuntimeException("Error uploading file: ${e.message}")
        }
    }
}