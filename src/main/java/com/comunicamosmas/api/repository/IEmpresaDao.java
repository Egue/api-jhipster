package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Empresa;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IEmpresaDao extends CrudRepository<Empresa, Long> {
    @Query(value = "SELECT * FROM empresas m WHERE m.nombre_comercial LIKE %?1%", nativeQuery = true)
    public List<Empresa> findByLikeNombreComercial(String empresa);
}
