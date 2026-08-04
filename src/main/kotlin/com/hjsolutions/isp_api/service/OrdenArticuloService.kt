package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.repository.IOrdenArticuloDao
import com.hjsolutions.isp_api.service.dto.OrdenArticuloDTO
import org.springframework.stereotype.Service

@Service
class OrdenArticuloService(
    private val ordenArticuloRepository : IOrdenArticuloDao
) {

    fun reporteConsumo(inicial:String, last:String): List<OrdenArticuloDTO>{

        val list = ordenArticuloRepository.consumoByBetween(inicial , last).map{ row ->
            OrdenArticuloDTO(
                idUsuario = row[0] as? Integer ?: 0,
                empleado = row[1] as? String ?: "",
                articulo = row[2] as? String ?: "",
                nota = row[3] as? String ?: "",
                nota_final = row[4] as? String ?: "",
                fechaRegistro = row[5] as? Int ?: 0,
                fechaAsiste = row[6] as? Int ?: 0,
                cliente = row[7] as? String ?: "",
                estacion = row[8] as? String ?: "",
                idContrato = row[9] as? Int ?: 0,
                idEstacion = row[10] as? Int ?: 0,
                cantidadEmpresa = row[11] as? Int ?: 0,
                cantidadUsuario = row[12] as? Int ?: 0
            )
        }

        return list;
    }
}
