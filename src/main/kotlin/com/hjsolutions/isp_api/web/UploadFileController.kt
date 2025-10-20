package com.hjsolutions.isp_api.web

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import com.hjsolutions.isp_api.service.UploadFileService
import com.hjsolutions.isp_api.web.rest.DataResponse

@RestController
@RequestMapping("/api/kt")
class UploadFileController(
    private val uploadFileService: UploadFileService
) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile , 
    @RequestParam("bucket") bucket:String , 
    @RequestParam("width") width:Int , 
    @RequestParam("height") height:Int): ResponseEntity<DataResponse<String>> {
        // Handle the file upload logic here
        // For example, save the file to a specific location or process it as needed
        val url = uploadFileService.uploadFile(file = file, bucket = bucket , width=width , height=height)

        return ResponseEntity.ok(DataResponse.success(url))
    }
}