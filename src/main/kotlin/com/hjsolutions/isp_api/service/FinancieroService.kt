package com.hjsolutions.isp_api.service
import org.springframework.stereotype.Service
import com.comunicamosmas.api.repository.IFinancieroNcDao
import com.hjsolutions.isp_api.service.dto.InfoFinancieroDTO
@Service
class FinancieroService(
    private val finanacieroRepository:IFinancieroNcDao
)
{


    fun get_info_nc(idNc:Long):List<InfoFinancieroDTO>{

        val info = finanacieroRepository.findInfoNc(idNc)
            .stream()
            .map(this::mapperToInfoFinancieroDTO)
            .toList()
        return info
    }

    fun mapperToInfoFinancieroDTO(obj: Array<Any>): InfoFinancieroDTO {
        // Replace with actual mapping as per InfoFinancieroDTO constructor
        val info = InfoFinancieroDTO(id = obj[4].toString(), 
        usuario = obj[5] as String ,  fecha = obj[2].toString(), comentario = obj[7] as String, valor_base = obj[0].toString(),
        valor_iva = obj[1].toString(), nc = obj[3].toString() as String, text = obj[6] as String
         /*, add other fields here */)
        // TODO: Map additional fields from obj to info as needed
        return info
    }





    

}