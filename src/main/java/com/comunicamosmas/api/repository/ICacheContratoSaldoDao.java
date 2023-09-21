package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.CacheContratoSaldo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ICacheContratoSaldoDao extends CrudRepository<CacheContratoSaldo, Long> {

    @Query(value="SELECT \n" + //
            "sav.id_log_saldo_favor,\n" + //
            "sav.marca,\n" + //
            "sav.id_contrato,\n" + //
            "case WHEN cli.tipo_cliente = 'J' THEN cli.razon_social\n" + //
            "\tWHEN cli.tipo_cliente = 'N' THEN CONCAT(cli.apellido_paterno,' ',cli.apellido_materno,' ',cli.nombre_primer,' ',cli.nombre_segundo) END as nombrecliente,\n" + //
            "co.estrato,\n" + //
            "cli.documento,\n" + //
            "ser.nombre as nombreServicio,\n" + //
            "sav.valor,\n" + //
            "med.nombre as medio_pago,\n" + //
            "med.comprobante,\n" + //
            "concat(usu.nombre , ' ', usu.apellidos) as cajero,\n" + //
            "sav.lugar,\n" + //
            "dir.barrio\n" + //
            "             FROM contratos_saldo_favor_log  sav\n" + //
            "             inner join contratos co on co.id_contrato = sav.id_contrato \n" + //
            "             inner join clientes cli on cli.id_cliente = co.id_cliente\n" + //
            "             inner join servicios ser on ser.id_servicio = co.id_servicio\n" + //
            "             inner join direcciones dir on co.id_direccion_servicio = dir.id_direccion\n" + //
            "             inner join medios_pago med on med.id_medio_pago = sav.id_medio_pago\n" + //
            "             inner join usuarios usu on usu.id_usuario = sav.id_cajero\n" + //
            "            WHERE sav.id_ciudad IN (:ciudad) AND sav.fechaf BETWEEN :inicio AND :final AND sav.tipo = '1' AND sav.lugar IN (:origen)" ,  nativeQuery = true)
    public Optional<List<Object[]>> reporteReciboCaja(@Param("ciudad") List<Integer> ciudad, @Param("inicio") Integer fecha_inicio , @Param("final") Integer fecha_final , @Param("origen") List<String> origen);
}
