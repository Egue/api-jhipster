package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.SoporteTicketTipo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ISoporteTicketTipoDao extends CrudRepository<SoporteTicketTipo, Long> {
    @Query(value="SELECT tipo.id_tipo_soporte, tipo.nombre FROM soporte_tickets_tipo tipo WHERE tipo.id_servicio = :idServicio"  , nativeQuery = true)
    public Optional<List<Object[]>> findByIdServicio(@Param("idServicio") Long idServicio);

}
