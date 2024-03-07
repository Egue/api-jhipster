package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.ContratoSaldoFavorLog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IContratoSaldoFavorLogDao extends CrudRepository<ContratoSaldoFavorLog, Long> {

     @Query(value="SELECT \n" + //
                  "csfl.id_log_saldo_favor ,\n" + //
                  "csfl.id_log_saldo_favor as code ,\n" + //
                  "c.nombre  as nombre_ciudad,\n" + //
                  "s.nombre  as nombre_servicio,\n" + //
                  "CASE\n" + //
                  "\tWHEN c3.tipo_cliente = \"N\" THEN CONCAT(c3.apellido_paterno, \" \", c3.apellido_materno, \" \", c3.nombre_primer, \" \", c3.nombre_segundo, \" / \", c3.documento)\n" + //
                  "\tWHEN c3.tipo_cliente = \"J\" THEN CONCAT(c3.razon_social , \" / \", c3.documento)\n" + //
                  "END AS cliente,\n" + //
                  "CONCAT(u.nombre, \" \", u.apellidos)as usuarios,\n" + //
                  "csfl.valor ,\n" + //
                  "csfl.marca ,\n" + //
                  "csfl.lugar ,\n" + //
                  "csfl.id_contrato ,\n" + //
                  "mp.nombre as medio_pago\n" + //
                  "FROM contratos_saldo_favor_log csfl \n" + //
                  "inner join ciudades c on c.id_ciudad =csfl.id_ciudad \n" + //
                  "inner join servicios s on s.id_servicio =csfl.id_servicio \n" + //
                  "inner join contratos c2 on c2.id_contrato = csfl.id_contrato \n" + //
                  "inner join clientes c3 on c3.id_cliente  = c2.id_cliente \n" + //
                  "inner join usuarios u on u.id_usuario  = csfl.id_cajero \n" + //
                  "inner join medios_pago mp on mp.id_medio_pago = csfl.id_medio_pago \n" + //
                  "WHERE csfl.id_medio_pago IN :payments AND csfl.tipo  = 1 AND csfl.fechaf BETWEEN :first AND :last" ,nativeQuery = true)
     public Optional<List<Object[]>> findByMedioPago(@Param("payments") List<Integer> pyments , @Param("first") String firts , @Param("last") String last);
}
