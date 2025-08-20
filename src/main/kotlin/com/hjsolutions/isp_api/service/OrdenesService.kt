package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.Orden
import com.comunicamosmas.api.repository.IContratoDao
import com.comunicamosmas.api.repository.IOrdenDao
import com.hjsolutions.isp_api.repositoryMysql.ContratoRepository
import com.hjsolutions.isp_api.service.dto.OrdenesDTO
import liquibase.repackaged.org.apache.commons.lang3.mutable.Mutable
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class OrdenesService(private val ordenesRepository: IOrdenDao , private val contratosRepository: IContratoDao) {


    fun cortesMasivamente(idServicio:Long):  List<Array<Any>> {
        //buscar listado de contratos con deudas superiores a
        val listContrato = contratosRepository.listContratoByCorteMasivamente(idServicio)
        //buscar convertir a idContrato
        val listContratoLong:List<Long> = listContrato.map{
            (it[0] as Number).toLong()
        }
        //lista de contrato con reconexiones u orden de cortes
        val listContratoWhitOrdenExist:List<Orden> = ordenesRepository.findOrdenCorteAndReconexionExist(listContratoLong)
        //quitar los contratos existentes de la listContratos
        //obtener solo los idContratos de las ordenes
        val contratosConOrdenExiste: Set<Long> = listContratoWhitOrdenExist.map { it.idContrato }.toSet()
        //
        val contratoWithCorte:List<Array<Any>> = listContrato.filter { it[0] !in contratosConOrdenExiste }
        //crear orden de corte

        return contratoWithCorte
    }

    fun ordenes(servicio:Long , tipo:Long):List<OrdenesDTO>{

        //recuperar el lot
        val listOrdenes:List<Array<Any>> = ordenesRepository.findOrdenes(servicio , tipo)
        val ordenes :List<OrdenesDTO> = listOrdenes.map {
            OrdenesDTO(
                id = (it[0] as Number).toLong(),
                causa = it[1] as String,
                cliente = it[2] as String,
                direccion = it[3] as String,
                registroFecha = (it[4] as Number).toString(),
                asignaFecha = (it[5] as Number).toString(),
                asisteFecha = (it[6] as Number).toString(),
                usuarioEjecuta = (it[7] as Number).toLong(),
                nota = it[8] as String,
                contrato = (it[9] as Number).toLong()
                )
        }

        return ordenes

    }

    fun anularOrde(id:Long , comment :String){
        var orden: Optional<Orden>  = ordenesRepository.findById(id);
        orden.map { it.estado = 3 }
        ordenesRepository.save(orden.get())
    }

}
