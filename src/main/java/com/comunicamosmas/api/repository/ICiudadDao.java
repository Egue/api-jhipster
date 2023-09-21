package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Ciudad;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ICiudadDao extends CrudRepository<Ciudad, Long> {

    @Query(value="SELECT ci.id_ciudad , ci.nombre FROM ciudades ci \n" 
    +"WHERE ci.id_ciudad IN (SELECT user_ci.id_ciudad FROM usuarios_ciudades user_ci where user_ci.id_usuario = :id)" , nativeQuery = true)
    public Optional<List<Object[]>> findByIdUser(@Param("id") Long id);
}
