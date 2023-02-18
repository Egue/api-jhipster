package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.EstacionAux;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IEstacionAuxDao extends CrudRepository<EstacionAux, Long> {
    @Query(value = "SELECT * FROM estaciones_aux ea WHERE ea.id_estacion = :idEstacion", nativeQuery = true)
    public List<EstacionAux> findByIdEstacion(@Param("idEstacion") Long idEstacion);
}
