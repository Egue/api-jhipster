package com.hjsolutions.isp_api.repositoryMysql

import com.comunicamosmas.api.domain.Contrato
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ContratoRepository : JpaRepository<Contrato,Long>{

    @Query(
        value = """
        SELECT
            co.*,
            COUNT(DISTINCT de.mes_servicio) AS cantidad_meses_servicio
        FROM contratos co
        INNER JOIN deudas de ON de.id_contrato = co.id_contrato
        WHERE
            co.estado = 1
            AND co.id_servicio = :service
            AND de.estado IN (1,3)
            AND de.instalacion = ''
            AND de.reconexion = ''
            AND de.materiales = ''
            AND de.traslado = ''
            AND de.otros = ''
        GROUP BY co.id_contrato
        ORDER BY co.id_contrato

        """, nativeQuery = true
    )
    fun listContratoByCorteMasivamente(@Param("service") service: Long?): MutableList<Array<Any?>?>?
}
