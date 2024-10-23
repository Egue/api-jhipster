package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.FinancieroNc;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IFinancieroNcDao extends CrudRepository<FinancieroNc, Long> {

    public List<FinancieroNc> findByIdDeuda(Long idDeuda);
}
