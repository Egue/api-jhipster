package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Cliente;
import java.util.List;
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
    public List<Cliente> findByDocumento(@Param(value = "documento") String documento);
}
