package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Estacion;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IEstacionDao extends CrudRepository<Estacion, Long> {
    @Query(
        value = "SELECT * FROM estaciones est WHERE est.nombre LIKE CONCAT('%', :nombre ,'%') AND est.id_servicio= :idServicio",
        nativeQuery = true
    )
    public List<Estacion> findByNombreAndIdServicio(@Param("nombre") String nombre, @Param("idServicio") Long idServicio);
    
    @Query(value = "select \n"
    		+ "est.id_estacion as id,\n"
    		+ "est.nombre as nombreEstacion,\n"
    		+ "case\n"
    		+ "	when est.tipo = 1 then 'Internet'\n"
    		+ "    when est.tipo = 2 then 'Televisión'\n"
    		+ "end as tipo,\n"
    		+ "em.nombre_comercial as nombreComercial,\n"
    		+ "ser.nombre as nombreServicio,\n"
    		+ "ci.nombre as nombreCiudad,\n"
    		+ "zm.nombre as nombreZona,\n"
    		+ "est.codigo,\n"
    		+ "est.api_ip as ip \n"
    		+ "FROM estaciones est\n"
    		+ "INNER JOIN empresas em ON em.id_empresa = est.id_empresa\n"
    		+ "INNER JOIN ciudades ci ON ci.id_ciudad = est.id_ciudad\n"
    		+ "INNER JOIN zonas zm ON zm.id_zona = est.id_zona\n"
    		+ "INNER JOIN servicios ser on ser.id_servicio = est.id_servicio\n"
    		+ "where est.estado = :estado",nativeQuery = true)
    public List<Object[]> findAllDTO(@Param("estado") Long estado);

	@Query(value="select \n"
    		+ "est.id_estacion as id,\n"
    		+ "est.nombre as nombreEstacion,\n"
    		+ "case\n"
    		+ "	when est.tipo = 1 then 'Internet'\n"
    		+ "    when est.tipo = 2 then 'Televisión'\n"
    		+ "end as tipo,\n"
    		+ "em.nombre_comercial as nombreComercial,\n"
    		+ "ser.nombre as nombreServicio,\n"
    		+ "ci.nombre as nombreCiudad,\n"
    		+ "zm.nombre as nombreZona,\n"
    		+ "est.codigo,\n"
    		+ "est.api_ip as ip \n"
    		+ "FROM estaciones est\n"
    		+ "INNER JOIN empresas em ON em.id_empresa = est.id_empresa\n"
    		+ "INNER JOIN ciudades ci ON ci.id_ciudad = est.id_ciudad\n"
    		+ "INNER JOIN zonas zm ON zm.id_zona = est.id_zona\n"
    		+ "INNER JOIN servicios ser on ser.id_servicio = est.id_servicio\n"
    		+ "where est.id_servicio = :idServicio" , nativeQuery = true)
	public Optional<List<Object[]>> findByIdServicio(@Param("idServicio") Long idServicio);
}
