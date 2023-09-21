package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Cliente;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
}
