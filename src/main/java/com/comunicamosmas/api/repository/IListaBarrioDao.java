package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.ListaBarrio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 

public interface IListaBarrioDao extends JpaRepository<ListaBarrio, Long> {

    public List<ListaBarrio> findByIdMunicipio(Long idMunicipio);
}
