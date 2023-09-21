package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IMikrotikHijoSimpleQueueDao extends CrudRepository<MikrotikHijoSimpleQueue, Long> {
    @Query(value = "SELECT * FROM mikrotik_hijo_simple_queue mh WHERE mh.id_contrato = :idContrato", nativeQuery = true)
    public MikrotikHijoSimpleQueue findByIdContrato(@Param("idContrato")Long idContrato);
    
    @Query(value="SELECT hijo.*  , co.estado FROM mikrotik_hijo_simple_queue hijo \n"+ 
    "left join contratos co on co.id_contrato = hijo.id_contrato  \n" + 
    "WHERE hijo.id_padre = :idPadre", nativeQuery = true)
    public Optional<List<Object[]>> findAllByPadre(@Param("idPadre") Integer idPadre);
}
