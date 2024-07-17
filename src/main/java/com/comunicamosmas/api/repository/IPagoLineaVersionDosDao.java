package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.PagoLineaVersionDos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface IPagoLineaVersionDosDao extends CrudRepository<PagoLineaVersionDos, Long> {
    
    public Optional<List<PagoLineaVersionDos>> findByEjecutadoAndGroupByIdContrato(Long ejecutado);
}
