package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IServicioDao extends CrudRepository<Servicio, Long> {
    @Query(value = "SELECT * FROM servicios sr WHERE sr.nombre LIKE CONCAT('%',:name,'%') ", nativeQuery = true)
    public List<Servicio> findByName(String name);
}
