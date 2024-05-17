package com.comunicamosmas.api.repository;

 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.comunicamosmas.api.domain.TipoTecnologia;

@Repository
public interface ITipoTecnologiaDao extends CrudRepository<TipoTecnologia, Long> {

    @Query(value="SELECT * FROM tipos_tecnologia WHERE servicio = :servicio AND estado  = 1 ", nativeQuery = true)
    public Optional<List<TipoTecnologia>> findByServicio(@Param("servicio") Long servicio);
}
