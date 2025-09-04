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
import com.comunicamosmas.api.repository.IContratoDao
import com.hjsolutions.isp_api.service.dto.ClientesContratoDTO

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

    fun find_all_by_state_fecha(fecha:String , contrato:String):ListProrrogaDTO
    {
        val state : String  ="A"
        var fechaSearch :String = ""
        var listProrrogas:List<Prorroga> = mutableListOf();
        when {
            fecha.isNotEmpty() && contrato != "0" -> {
                fechaSearch = fecha
                listProrrogas = prorogaRepository.findAllByStateAndFechaProrrogaContainingAndIdContrato(
                    state, fechaSearch, contrato.toLong()
                )
            }
            fecha.isEmpty() && contrato != "0" -> {
                listProrrogas = prorogaRepository.findAllByStateAndIdContrato(state, contrato.toLong())
            }
            fecha.isNotEmpty() && contrato == "0" -> {
                fechaSearch = fecha
                listProrrogas = prorogaRepository.findAllByStateAndFechaProrrogaContaining(state, fechaSearch)
            }
        }

        if (listProrrogas.isEmpty()) {
            return ListProrrogaDTO(
                dataProrrogaListService = emptyList(),
                prorrogaList = emptyList()
            )
        }
        val contratoLong:List<Long> = listProrrogas.mapNotNull {it.idContrato}
        if (contratoLong.isEmpty()) {
            return ListProrrogaDTO(
                dataProrrogaListService = emptyList(),
                prorrogaList = emptyList()
            )
        }
        /*val listName: Map<Long, Array<Any?>> = try {
            contratoRepository.findClienteByListContrato(contratoLong)
                .associateBy(
                    keySelector = { (it[0]as Number).toLong() }, // clave
                    valueTransform = { it }          // valor completo (el array)
                )
        } catch (e: Exception) {
            emptyMap()
        }*/
        val listNameVersion: List<ClientesContratoDTO> = contratoRepository.findClienteByListContrato(contratoLong).map { row-> mapperToClienteContratoDTO(row as Array<Any> )}

        val listName = listNameVersion.associateBy { it.idContrato  }

        val listNameCliente = listProrrogas.mapNotNull { prorroga ->
            val idContrato = prorroga.idContrato
            val fechaProrroga = prorroga.fechaProrroga
            val id = prorroga.id
            if(idContrato != null && fechaProrroga != null && id != null){
                val datosAdicionales = listName[idContrato]
                if(datosAdicionales != null)
                {
                   try {
                       mapperToProrrogaInfoDTO(datosAdicionales as Array<Any>, fechaProrroga , id)
                   }catch (e: Exception)
                   { println("Error mapeando prorroga ${prorroga.id}: ${e.message}")
                       null
                   }
                }else{
                    println("No se encontraron datos adicionales para contrato: $idContrato")
                    null
                }
            }else{
                println("Prorroga con campos nulos: idContrato=$idContrato, fechaProrroga=$fechaProrroga, id=$id")
                null
            }
        }

        val conteoPorServicio:List<DataProrrogaByServiceDTO> = listNameCliente
        .groupBy { it.servicio }
        .map{ (servicio , items)->
            val cantidad = items.size
            val totalSum = items.sumOf { (it.total as? Double) ?: 0.0 }
            val parcialSum = items.sumOf { (it.parcial as? Double) ?: 0.0 }
            val saldo = totalSum - parcialSum
            DataProrrogaByServiceDTO(servicio  = servicio , amount = cantidad , saldo = saldo)
        }

       return ListProrrogaDTO(dataProrrogaListService = conteoPorServicio , prorrogaList = listNameCliente )

    }

    fun mapperToClienteContratoDTO(obj:Array<Any>): ClientesContratoDTO{

        return ClientesContratoDTO(
            idContrato = (obj.getOrNull(0) as? Number)?.toLong() ?: 0L,
            tipoCliente = (obj.getOrNull(1) as? String) ?: "",
            nameCliente = (obj.getOrNull(2) as? String) ?: "",
            nameServicio = (obj.getOrNull(3) as? String) ?: "",
            direccion = (obj.getOrNull(4) as? String) ?: "",
            parcial = (obj.getOrNull(5) as Number)?.toDouble() ?: 0.0,
            total = (obj.getOrNull(6) as Number)?.toDouble() ?: 0.0
        )
    }

    fun mapperToProrrogaInfoDTO(obj: Array<Any>, fechaProrroga: String , id:String): ProrrogaInfoDTO {
    /*return ProrrogaInfoDTO(
        id  = id as String,
        idContrato = obj[0] as Int,
        tipo = obj[1] as String,
        nameCliente = obj[2] as String,
        servicio = obj[3] as String,
        direccion = obj[4] as String,
        parcial = obj[5] as Double,
        total = obj[6] as Double,
        fechaProrroga = fechaProrroga
    )*/
        return try {
            ProrrogaInfoDTO(
                id = id,
                idContrato = (obj.getOrNull(0) as? Number)?.toInt() ?: 0,
                tipo = obj.getOrNull(1)?.toString() ?: "DESCONOCIDO",
                nameCliente = obj.getOrNull(2)?.toString() ?: "CLIENTE DESCONOCIDO",
                servicio = obj.getOrNull(3)?.toString() ?: "SERVICIO DESCONOCIDO",
                direccion = obj.getOrNull(4)?.toString() ?: "",
                parcial = (obj.getOrNull(5) as? Number)?.toDouble() ?: 0.0,
                total = (obj.getOrNull(6) as? Number)?.toDouble() ?: 0.0,
                fechaProrroga = fechaProrroga
            )
        } catch (e: Exception) {
            println("Error crítico en mapper: ${e.message}")
            // Retornar un objeto por defecto
            ProrrogaInfoDTO(
                id = id,
                idContrato = 0,
                tipo = "ERROR",
                nameCliente = "ERROR",
                servicio = "ERROR",
                direccion = "",
                parcial = 0.0,
                total = 0.0,
                fechaProrroga = fechaProrroga
            )
        }
    }

}
