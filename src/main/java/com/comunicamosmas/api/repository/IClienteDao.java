package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;

import liquibase.pro.packaged.co;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteDao extends CrudRepository<Cliente, Long> {
    @Query(
        value = "SELECT * FROM clientes cl\n" +
        "INNER JOIN contratos co on cl.id_cliente = co.id_cliente \n" +
        "WHERE  co.id_contrato = ? limit 0,1",
        nativeQuery = true
    )
    public Cliente getClientByIdContrato(Long idContrato);

    @Query(value = "SELECT * FROM clientes cl WHERE cl.documento LIKE CONCAT('%', :documento,'%') LIMIT 0,10", nativeQuery = true)
    public List<Cliente> findByDocumento(@Param("documento") String documento);

    //sql buscar por nombre
    @Query(value="SELECT cl.id_cliente , cl.tipo_cliente, cl.razon_social , cl.apellido_paterno , cl.apellido_materno , cl.nombre_primer , cl.nombre_segundo , cl.documento FROM clientes cl WHERE cl.razon_social LIKE CONCAT('%', :name, '%') OR cl.apellido_paterno LIKE CONCAT('%', :name, '%') OR  cl.nombre_primer LIKE CONCAT('%', :name, '%') LIMIT 0,10", nativeQuery = true)
    public Optional<List<Object[]>> findByName(@Param("name") String name);


    @Query(value="SELECT cl.id_cliente , cl.tipo_cliente, cl.razon_social , cl.apellido_paterno , cl.apellido_materno , cl.nombre_primer , cl.nombre_segundo , cl.documento FROM clientes cl inner join contratos co ON co.id_cliente = cl.id_cliente \n" + 
    " WHERE co.id_contrato = :cus LIMIT 0,10", nativeQuery = true)
    public Optional<List<Object[]>> findByCus(@Param("cus") Long cus);

    @Query(value="SELECT cli.id_cliente FROM clientes cli WHERE cli.documento = :documento" , nativeQuery = true)
    public Optional<List<Object[]>> validExiste(@Param("documento") String documento);

    @Query(value="SELECT cli.*  FROM clientes cli INNER JOIN contratos co  on co.id_cliente = cli.id_cliente \n"
    +" WHERE cli.portalweb  IS NULL AND co.estado = 1 AND co.id_servicio IN (1,2,3,4,5,6,7,8,9) GROUP BY cli.id_cliente " ,
    countQuery = "SELECT count(DISTINCT cli.id_cliente) " +
                    "FROM clientes cli " +
                    "INNER JOIN contratos co ON co.id_cliente = cli.id_cliente " +
                    "WHERE cli.portalweb IS NULL AND co.estado = 1 AND co.id_servicio IN (1,2,3,4,5,6,7,8,9)",
    nativeQuery = true)
    public Page<Cliente> listClientBySincronicePortalWeb(Pageable pageable);

    @Query(value="SELECT c.tipo_cliente  , c.documento  , co.id_contrato, co.marca as registrado, "+
                "case WHEN c.tipo_cliente = 'N' THEN CONCAT(c.nombre_primer , ' ', c.nombre_segundo,' ', c.apellido_paterno, ' ', c.apellido_materno)"+
	"WHEN c.tipo_cliente = 'J' THEN CONCAT(c.razon_social) 	END as name_cliente , se.nombre as nombre_servicio 	FROM clientes c "+ 	
	"INNER JOIN contratos co on co.id_cliente = c.id_cliente  "+
    " INNER JOIN servicios se on se.id_servicio = co.id_servicio "+
	"WHERE co.estado = 1 AND co.id_servicio IN (1,2,3,4,5,6,7,8,9) AND co.marca BETWEEN  CURRENT_TIMESTAMP - INTERVAL 11 MONTH AND CURRENT_TIMESTAMP - INTERVAL 10 MONTH", nativeQuery = true)
    public List<Object[]> findClientesDeclineClausura();

     

}
