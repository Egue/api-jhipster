package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.SoporteTicket;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ISoporteTicketDao extends CrudRepository<SoporteTicket, Long> {


    
    @Query(value ="SELECT tik.id_ticket,servicios.nombre ,tik.id_contrato, tik.causa, tik.marca, \n" + //
            "CASE \n" + //
            "WHEN cli.tipo_cliente = 'J' THEN cli.razon_social\n" + //
            "WHEN cli.tipo_cliente = 'N' THEN concat(cli.nombre_primer , ' ', cli.apellido_paterno)\n" + //
            "END AS cliente,\n" + //
            "cli.documento, \n" + //
            "concat(users.nombre,' ', users.apellidos) as creado_por,\n" + //
            "group_concat(resp.respuesta , '/') respuesta\t\n" + //
            "from soporte_tickets tik\n" + //
            "INNER JOIN soporte_tickets_tipo tipo on tipo.id_tipo_soporte = tik.id_tipo_soporte \n" + //
            "INNER JOIN clientes cli on cli.id_cliente = tik.id_cliente \n" + //
            "INNER JOIN usuarios users on users.id_usuario = tik.id_usuario_registra\n" + //
            "INNER JOIN soporte_tickets_resp resp on resp.id_ticket = tik.id_ticket\n" + //
            "inner join servicios on servicios.id_servicio = tik.id_servicio\n" + //
            "WHERE tik.fechaf between :inicio and :final AND tik.id_tipo_soporte = :idTipo \n" + //
            "group by tik.id_ticket" , nativeQuery = true)
    public Optional<List<Object[]>> findTicketByTipo(@Param("idTipo") Long idtipo , @Param("inicio")Long inicio, @Param("final") Long finalf);
}
