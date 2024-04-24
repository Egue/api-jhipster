package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Servicio;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IServicioDao extends CrudRepository<Servicio, Long> {
    @Query(value = "SELECT * FROM servicios sr WHERE sr.nombre LIKE CONCAT('%',:name,'%') ", nativeQuery = true)
    public List<Servicio> findByName(@Param("name") String name);

    @Query(value="SELECT * FROM servicios sr WHERE sr.id_empresa = :idEmpresa" , nativeQuery = true)
    public Optional<List<Servicio>> findByIdEmpresa(@Param("idEmpresa") Long idEmpresa);
}
