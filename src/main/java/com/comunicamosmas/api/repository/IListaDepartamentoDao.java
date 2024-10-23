package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.ListaDepartamento;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 

public interface IListaDepartamentoDao extends JpaRepository<ListaDepartamento, Long> {

    /*@Query(value="SELECT * FROM lista_departamentos ld INNER JOIN lista_municipios lm ON lm.departamento_id = ld.id_departamento WHERE ld.estado = :estado", nativeQuery = true)
    public Optional<List<Object[]>> findByEstado(@Param("estado") Long estado);*/

    public List<ListaDepartamento> findByEstado(Long estado);
}
