package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Empresa;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IEmpresaDao extends CrudRepository<Empresa, Long> {
	
    @Query(value = "SELECT * FROM empresas m WHERE m.nombre_comercial LIKE %?1%", nativeQuery = true)
    public List<Empresa> findByLikeNombreComercial(String empresa);
    
    @Query(value= "SELECT * FROM empresas m "
    		+ "INNER JOIN contratos co on co.id_empresa = m.id_empresa"
    		+ " WHERE co.id_contrato = :idContrato", nativeQuery=true)
    public Empresa findEmpresaByContrato(@Param("idContrato") Long IdContrato);

    @Query(value="SELECT * FROM empresas m WHERE m.estado = 1" , nativeQuery = true)
    public Optional<List<Empresa>> findAllByStatus();
}
