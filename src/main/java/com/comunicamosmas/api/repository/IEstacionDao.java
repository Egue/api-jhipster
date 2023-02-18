package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Estacion;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IEstacionDao extends CrudRepository<Estacion, Long> {
    @Query(
        value = "SELECT * FROM estaciones est WHERE est.nombre LIKE CONCAT('%', :nombre ,'%') AND est.id_servicio= :idServicio",
        nativeQuery = true
    )
    public List<Estacion> findByNombreAndIdServicio(@Param("nombre") String nombre, @Param("idServicio") Long idServicio);
}
