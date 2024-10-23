package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.ClausulaPermanencia;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface IClausulaPermanenciaDao extends JpaRepository<ClausulaPermanencia, Long> {

    public ClausulaPermanencia findByIdContrato(Long idContrato);
}
