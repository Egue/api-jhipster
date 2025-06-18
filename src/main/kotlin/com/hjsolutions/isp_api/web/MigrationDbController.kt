package com.hjsolutions.isp_api.web
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import com.comunicamosmas.api.repository.IClienteDao
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import com.hjsolutions.isp_api.service.dto.ClienteCablemagDTO
import java.io.InputStream
import java.io.InputStreamReader
import com.opencsv.bean.CsvToBeanBuilder

@RestController
@RequestMapping("/api/kt/migrations")
class MigrationDbController(private val clienteRepository:IClienteDao)
{
    
    @PostMapping("clientes" , consumes = ["multipart/form-data"])
    fun cliente(@RequestParam("file") file:MultipartFile):ResponseEntity<String>
    {   
        if(file.isEmpty)
        {
            return ResponseEntity("Archivo vacío", HttpStatus.BAD_REQUEST)
        }
        return try {
            val reader = InputStreamReader(file.InputStream)
            val clientes = CsvToBeanBuilder<ClienteCablemagDTO>(reader)
            .withType(ClienteCablemagDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
            .parse()

            ResponseEntity.ok(clientes)
        }
        catch( e:Exception) {
             e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emptyList())
        }
    }

}