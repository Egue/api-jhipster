package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.PagoLineaVersionDos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IPagoLineaVersionDosDao extends CrudRepository<PagoLineaVersionDos, Long> {
    
    @Query(value="SELECT * FROM pagos_linea_version_dos p WHERE p.ejecutado = :ejecutado GROUP BY p.id_contrato" , nativeQuery=true)
    public Optional<List<PagoLineaVersionDos>> findByEjecutadoAndGroupByIdContrato(@Param("ejecutado") Long ejecutado);
}
