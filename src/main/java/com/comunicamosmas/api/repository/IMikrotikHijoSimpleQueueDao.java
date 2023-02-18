package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IMikrotikHijoSimpleQueueDao extends CrudRepository<MikrotikHijoSimpleQueue, Long> {
    @Query(value = "SELECT * FROM mikrotik_hijo_simple_queue mh WHERE mh.id_contrato = :idContrato", nativeQuery = true)
    public MikrotikHijoSimpleQueue findByIdContrato(Long idContrato);
}
