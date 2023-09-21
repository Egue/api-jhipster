package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.PagoRetencion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IPagoRetencionDao extends CrudRepository<PagoRetencion, Long> {

    @Query(value="SELECT \n" + //
            "rete.indice,\n" + //
            "rete.marca,\n" + //
            "rete.id_contrato,\n" + //
            "CASE WHEN cli.tipo_cliente  = 'J' THEN cli.razon_social\n" + //
            "\tWHEN cli.tipo_cliente = 'N' THEN CONCAT(cli.apellido_paterno,' ',cli.apellido_materno,' ',cli.nombre_primer,' ',cli.nombre_segundo) END as nombre_cliente,\n" + //
            "co.estrato,\n" + //
            "cli.documento,\n" + //
            "ser.nombre as nombre_servicio,\n" + //
            "rete.retefuente,\n" + //
            "rete.reteica,\n" + //
            "rete.bomberil,\n" + //
            "rete.reteiva,\n" + //
            "rete.otros,\n" + //
            "rete.retefuente_t,\n" + //
            "rete.reteica_t,\n" + //
            "rete.bomberil_t,\n" + //
            "rete.reteiva_t,\n" + //
            "rete.otros_t,\n" + //
            "rete.grupo,\n" + //
            "dire.barrio,\n" + //
            "concat(usu.nombre , ' ', usu.apellidos) as cajero,\n" + //
            "med.nombre as mediopago,\n" + //
            "deu.mes_servicio, \n" +//
            "med.comprobante\n"+
            " FROM pagos_retenciones rete\n" + //
            " INNER JOIN contratos co ON co.id_contrato = rete.id_contrato\n" + //
            " INNER JOIN clientes cli ON cli.id_cliente = co.id_cliente\n" + //
            " INNER JOIN servicios ser ON ser.id_servicio  = rete.id_servicio\n" + //
            " INNER JOIN direcciones dire ON dire.id_direccion = co.id_direccion_servicio\n" + //
            " INNER JOIN usuarios usu ON usu.id_usuario = rete.id_cajero\n" + //
            " INNER JOIN medios_pago med ON med.id_medio_pago = rete.id_medio_pago\n" + //
            "LEFT JOIN deudas deu ON deu.id_deuda = rete.id_deuda\n"+//
            "WHERE rete.id_ciudad IN (:ciudad) AND rete.fechaf between :inicio and :final AND rete.grupo IN (:tipo)" , nativeQuery = true)
    public Optional<List<Object[]>> reporte_retenciones(@Param("ciudad") List<Integer> ciudad , @Param("inicio") Integer fecha_inicio , @Param("final") Integer fecha_final , @Param("tipo")List<String> tipo);
}
