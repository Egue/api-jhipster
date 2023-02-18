package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.web.rest.vm.IListContratoVM;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IContratoDao extends CrudRepository<Contrato, Long> {
    @Query(
        value = "SELECT \n" +
        "mun.municipio as nombreMunicipio,\n" +
        "ser.nombre as nombreServicio,\n" +
        "co.id_contrato as idContrato,\n" +
        "dir.barrio,\n" +
        "concat(dir.tipo,' / ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo,' ',dir.b_numero,' ',dir.b_letra,' ',dir.numero,' / ',dir.nota) as direccion,\n" +
        "CASE  \n" +
        "	WHEN co.estado =  0 THEN 'Creado'\n" +
        "    WHEN co.estado = 1 THEN 'Activo'\n" +
        "    WHEN co.estado = 2 THEN 'Anulado'\n" +
        "    WHEN co.estado = 3 THEN 'Cortado'\n" +
        "    WHEN co.estado = 4 THEN 'Retirado'\n" +
        "    WHEN co.estado = 5 THEN 'Suspendido'\n" +
        "END as estado\n" +
        " FROM clientes\n" +
        "\n" +
        "INNER JOIN contratos co ON co.id_cliente = clientes.id_cliente\n" +
        "INNER JOIN direcciones dir ON dir.id_direccion = co.id_direccion_servicio\n" +
        "INNER JOIN servicios ser ON ser.id_servicio = co.id_servicio\n" +
        "INNER JOIN lista_municipios mun ON mun.id_municipio = dir.municipio\n" +
        "WHERE clientes.id_cliente = :idCliente",
        nativeQuery = true
    )
    public List<IListContratoVM> findByIdCliente(@Param(value = "idCliente") Long idCliente);
}
