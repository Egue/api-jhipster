package com.hjsolutions.isp_api.service

import org.springframework.stereotype.Service
import com.hjsolutions.isp_api.service.dto.ProrrogaDTO
import com.hjsolutions.isp_api.service.dto.ProrrogaInfoDTO
import com.hjsolutions.isp_api.service.dto.DataProrrogaByServiceDTO
import com.hjsolutions.isp_api.service.dto.ListProrrogaDTO
import com.hjsolutions.isp_api.service.mapper.toEntity
import com.hjsolutions.isp_api.repository.ProrrogaRepository
import com.hjsolutions.isp_api.domain.Prorroga 
import java.util.Optional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.comunicamosmas.api.repository.IContratoDao

@Service
class ProrrogaService(val prorogaRepository : ProrrogaRepository , val globalService:GlobalService ,  val contratoRepository:IContratoDao)
{
    fun create_prorroga(prorrogaDTO: ProrrogaDTO) :Prorroga
    {
        /*if ( prorrogaDTO.cantidad > 1)
        {
            var fechaBase = prorrogaDTO.fecha_prorroga
            val entity = prorrogaDTO.toEntity()
            prorogaRepository.save(entity)

             for(i in 2..prorrogaDTO.cantidad)
             {
                val nuevaFecha = fechaBase?.plusMonths(i.toLong() - 1)
                val nuevaDTO = prorrogaDTO.copy(fecha_prorroga = nuevaFecha)
                val entity2 = nuevaDTO.toEntity()
                prorogaRepository.save(entity2)
             } 

        }else{
            
            val entity = prorrogaDTO.toEntity()

            prorogaRepository.save(entity) 
        }*/
        find_by_exist(prorrogaDTO)
       
        val addCreate = prorrogaDTO.copy(createBy = globalService.getCurrentUserID())

        val entity = addCreate.toEntity()

        return prorogaRepository.save(entity)
        
    }

    fun get_prorroga_by_contrato(idContrato: Long):List<Prorroga>{

        return prorogaRepository.findAllByIdContrato(idContrato)
    }

    fun get_prorroga_find_all():List<Prorroga>
    {
            return prorogaRepository.findAll()
    }

    fun updated_prorroga(prorrogaDTO: ProrrogaDTO , id:String)
    {
        var findOne:Optional<Prorroga?> = prorogaRepository.findById(id);

        var updated = findOne.get()

        updated.let { it -> 
            it.fechaProrroga = prorrogaDTO.fechaProrroga
            it.state = prorrogaDTO.state

            var exist = prorogaRepository.findAllByIdContratoAndFechaProrroga(it.idContrato ?:0, it.fechaProrroga ?:"")
            if(exist.isNotEmpty())
            {
                throw Exception("Ya existe una prorroga con esa fecha ${it.fechaProrroga}")
            }
            prorogaRepository.save(it)
        }

    
    }

    private fun find_by_exist(prorrogaDTO:ProrrogaDTO){
         var fecha = prorrogaDTO.fechaProrroga?.dropLast(3) 
        var prorroga: List<Prorroga?> = prorogaRepository.findAllByIdContratoAndFechaProrrogaContaining(prorrogaDTO.idContrato ?: 0L, fecha ?: "")
        // If you want to throw an exception only when prorroga is not null, check for that condition:
        if (prorroga.isNotEmpty()) {
            throw Exception("Ya existe una prorroga para este mes")
        }
    }

    fun find_all_by_state_fecha():ListProrrogaDTO
    {
        var state : String  ="A"
        var fecha : String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        var listProrrogas : List<Prorroga> = prorogaRepository.findAllByStateAndFechaProrrogaContaining(state, fecha)

        var contratoLong:List<Long> = listProrrogas.mapNotNull {it.idContrato}

        val fechasporContrato = listProrrogas.associate { it.idContrato to it.fechaProrroga }

        var listNameCliente  = contratoRepository.findClienteByListContrato(contratoLong)
        .map{ row -> 
            val idContrato = (row[0]as Int).toLong()
            val fechaProrroga = fechasporContrato[idContrato] ?:""

            mapperToProrrogaInfoDTO(row, fechaProrroga)
        }.toList()

       /*val conteoPorServicio:List<DataProrrogaByServiceDTO> = listNameCliente
        .groupingBy { it.servicio }
        .eachCount()
        .map{(servicio , amount) -> DataProrrogaByServiceDTO(servicio , amount)}*/
        val conteoPorServicio:List<DataProrrogaByServiceDTO> = listNameCliente
        .groupBy { it.servicio }
        .map{ (servicio , items)->
            val cantidad = items.size
            val totalSum = items.sumOf { (it.total as? Number)?.toDouble() ?: 0.0 }
            val parcialSum = items.sumOf { (it.parcial as? Number)?.toDouble() ?: 0.0 }
            val saldo = totalSum - parcialSum
            DataProrrogaByServiceDTO(servicio  = servicio , amount = cantidad , saldo = saldo)
        }

        val dto:ListProrrogaDTO = ListProrrogaDTO(dataProrrogaListService = conteoPorServicio , prorrogaList = listNameCliente ) 

        return dto

    }

    fun mapperToProrrogaInfoDTO(obj: Array<Any>, fechaProrroga: String): ProrrogaInfoDTO {
    return ProrrogaInfoDTO(
        idContrato = obj[0] as Int,
        tipo = obj[1] as String,
        nameCliente = obj[2] as String,
        servicio = obj[3] as String,
        direccion = obj[4] as String,
        parcial = obj[5] as Double,
        total = obj[6] as Double,
        fechaProrroga = fechaProrroga
    )
}

}