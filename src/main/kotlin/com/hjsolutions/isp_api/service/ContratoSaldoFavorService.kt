package com.hjsolutions.isp_api.service

import org.springframework.stereotype.Service
import com.comunicamosmas.api.repository.IContratoSaldoFavorLogDao
import com.hjsolutions.isp_api.service.dto.InfoSaldoFavorDTO
@Service
class ContratoSaldoFavorService(
    private val saldoFavorRepository:IContratoSaldoFavorLogDao
)
{

    fun get_info_saldo_favor(id:Long):List<InfoSaldoFavorDTO>
    {
        return saldoFavorRepository.findInfoContrato(id).stream().map(this::mapperToInfoSaldoFavor).toList();

    }

    fun mapperToInfoSaldoFavor(objet : Array<Any>):InfoSaldoFavorDTO
    {
        val info = InfoSaldoFavorDTO(
            id = objet[0] as Int,
            cajero = objet[1] as String,
            valor = objet[2].toString(),
            detalle = objet[3].toString(),
            marca = objet[4].toString(),
            medioPago = objet[5].toString(),
            tipo=objet[6] as String

        )

        return info;
    }

}