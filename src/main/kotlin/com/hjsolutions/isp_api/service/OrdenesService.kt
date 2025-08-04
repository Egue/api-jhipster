package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.Orden
import com.comunicamosmas.api.repository.IContratoDao
import com.comunicamosmas.api.repository.IOrdenDao
import com.hjsolutions.isp_api.repositoryMysql.ContratoRepository
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

        return contratoWithCorte
    }

}
