package com.hjsolutions.isp_api.web
  
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import com.hjsolutions.isp_api.web.rest.DataResponse
import com.hjsolutions.isp_api.service.ProrrogaService
import com.hjsolutions.isp_api.service.dto.ProrrogaDTO
import com.hjsolutions.isp_api.service.dto.ProrrogaInfoDTO
import com.hjsolutions.isp_api.service.dto.ListProrrogaDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import com.hjsolutions.isp_api.domain.Prorroga 

@RestController
@RequestMapping("/api/kt")
class ProrrogaController(private val prorrogaService:ProrrogaService)
{
    @PostMapping("/prorroga")
    fun create_prorroga(@RequestBody() prorrogaDTO:ProrrogaDTO):ResponseEntity<DataResponse<String>>{

         prorrogaService.create_prorroga(prorrogaDTO)

        return ResponseEntity.ok(DataResponse.success("OK"))
    }

    @GetMapping("/prorroga/by/query")
    fun get_by_query(@RequestParam("idContrato") idContrato: Long): ResponseEntity<List<Prorroga>> {

        return ResponseEntity.ok(prorrogaService.get_prorroga_by_contrato(idContrato))
    }

    @PutMapping("/prorroga/{id}")
    fun updated_prorroga(@RequestBody() prorrogaDTO:ProrrogaDTO , @PathVariable("id") id:String):ResponseEntity<DataResponse<String>>
    {
        prorrogaService.updated_prorroga(prorrogaDTO, id)

        return ResponseEntity.ok(DataResponse.success("Ok"))
    }

    @GetMapping("/prorroga")
    fun get_find_all_by_state_fecha(): ResponseEntity<DataResponse<ListProrrogaDTO>> {

        return ResponseEntity.ok(DataResponse.success(prorrogaService.find_all_by_state_fecha()))

    }
}