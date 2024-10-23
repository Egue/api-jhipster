package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.ListaMunicipio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 

public interface IListaMunicipioDao extends JpaRepository<ListaMunicipio, Long> {

    public List<ListaMunicipio> findByDepartamentoid(Long departamentoid);
}
