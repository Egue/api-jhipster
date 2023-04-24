package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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
        "    WHEN co.estado = 2 THEN 'Cortado'\n" +
        "    WHEN co.estado = 3 THEN 'Anulado'\n" +
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
        nativeQuery = true )
	
    public List<Object[]> findByIdCliente(@Param("idCliente") Long idCliente);
    
    /**
     * consultar para cargar los datos de conctacto del cliente*/
    @Query(value="SELECT \n"
    		+ "co.id_contrato idContrato, \n"
    		+ "co.estrato,\n"
    		+ "case\n"
    		+ "	when cl.tipo_cliente = 'J' then cl.razon_social\n"
    		+ "    when cl.tipo_cliente = 'N' then CONCAT(cl.apellido_paterno , ' ',cl.apellido_materno , ' ',cl.nombre_primer , ' ',cl.nombre_segundo)\n"
    		+ "    end  as nombreCliente,\n"
    		+ "cl.documento,\n"
    		+ "cl.celular_a as celularA,\n"
    		+ "cl.celular_b as celularB,\n"
    		+ "cl.mail,\n"
    		+ "ta.nombre nombreTarifa, \n"
    		+ "ta.velocidad,\n"
    		+ "FORMAT(ta.valor , 2)as  valor ,\n"
    		+ "dir.longitud as longDireccion,\n"
    		+ "dir.latitud as latDireccion,\n"
    		+ "es.latitud as latEstacion,\n"
    		+ "es.longitud as longEstacion,\n"
    		+ "concat(dir.tipo,' / ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo,' ',dir.b_numero,' ',dir.b_letra,' ',dir.numero,' / ',dir.nota,' / ',dir.barrio) as direccionServicio\n"
    		+ "\n"
    		+ "FROM contratos co\n"
    		+ "\n"
    		+ "left join clientes cl on cl.id_cliente = co.id_cliente\n"
    		+ "left join tarifas ta on ta.id_tarifa = co.id_tarifa_promo\n"
    		+ "left join direcciones dir on dir.id_direccion = co.id_direccion_servicio\n"
    		+ "left join estaciones es on es.id_estacion = co.id_estacion\n"
    		+ "where co.id_contrato = :idContrato" , nativeQuery=true)
    public List<Object[]> datosClienteByIdContrato(@Param(value="idContrato") Long idContrato);
}
