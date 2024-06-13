package com.comunicamosmas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.Direccion; 

public interface IDireccionDao extends JpaRepository<Direccion, Long> {

    @Query(value="SELECT d.id_direccion as id , d.id_cliente  as idCliente, d.barrio, d.latitud, d.longitud ,\n" + //
                "d.nota, d.estado, d.rara, d.tipo, CONCAT(d.a_tipo , ' ', d.a_numero,d.a_letra, ' ',d.b_tipo,' ',d.b_numero,d.b_letra, ' ' , d.numero ,'/',d.barrio,'/',lm.municipio,'/',ld.departamento) as direccion FROM direcciones d\n" + //
                "INNER JOIN lista_municipios lm ON lm.id_municipio = d.municipio \n" + //
                "INNER JOIN lista_departamentos ld ON ld.id_departamento  = d.departamento \n" + //
                "WHERE d.id_cliente = :idCliente" , nativeQuery = true)
    public Optional<List<Object[]>> findByIdCliente(@Param("idCliente") Long idCliente);

    @Query(value="select d.barrio ,ld.departamento , lm.municipio, \n"+
    "CONCAT(d.a_tipo, ' ' , d.a_numero , ' ' , d.a_letra, ' ',d.b_tipo, ' ' , d.b_numero, ' ',d.a_letra , ' ', d.numero) as direccion, \n"+
    "CONCAT(d2.a_tipo, ' ' , d2.a_numero , ' ' , d2.a_letra, ' ',d2.b_tipo, ' ' , d2.b_numero, ' ',d2.a_letra , ' ', d2.numero  ,' / ',d2.barrio , ' / ', lm2.municipio ,' / ' ,ld2.departamento ) as facturacion from contratos co \n"+
    "inner join direcciones d on d.id_direccion =co.id_direccion_servicio \n"+
    "inner join direcciones d2 on d2.id_direccion =co.id_direccion_factura \n"+
    "inner join lista_departamentos ld on ld.id_departamento  = d.departamento \n"+
    "inner join lista_departamentos ld2 on ld2.id_departamento = d2.departamento \n"+
    "inner join lista_municipios lm2 on lm2.id_municipio = d2.municipio \n" +
    "inner join lista_municipios lm on lm.id_municipio = d.municipio \n"+
    "WHERE co.id_contrato = :idContrato LIMIT 0,1"  , nativeQuery=true)
    public Optional<List<Object[]>> findInfoFirma(@Param("idContrato")  Long idContrato);
}
