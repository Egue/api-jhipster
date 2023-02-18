package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.WinmaxPass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IWinmaxPassDao extends CrudRepository<WinmaxPass, Long> {
    @Query(value = "SELECT count(id_contrato) as contrato from winmax_pass wp WHERE wp.id_contrato = ? LIMIT 0,1", nativeQuery = true)
    public Long countFindByIdContrato(Long idContrato);

    @Query(value = "SELECT * FROM winmax_pass wp WHERE wp.id_contrato = ? LIMIT 0,1", nativeQuery = true)
    public WinmaxPass findByIdContrato(Long idContrato);
}
