package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IMikrotikTarifaReusoDao extends CrudRepository<MikrotikTarifaReuso, Long> {
    @Query(value = "SELECT * FROM mikrotik_tarifas_reuso mk WHERE mk.id_estacion = :idEstacion", nativeQuery = true)
    public List<MikrotikTarifaReuso> findByIdEstacion(@Param("idEstacion")Long IdEstacion);

    @Query(value = "SELECT * FROM mikrotik_tarifas_reuso mk WHERE mk.estado = :estado", nativeQuery = true)
    public List<MikrotikTarifaReuso> findByEstado(@Param("estado")String estado);

    @Query(
        value = "SELECT * FROM mikrotik_tarifas_reuso mk WHERE mk.nombre_padre LIKE CONCAT('%' , :nombre , '%') \n" +
        "AND mk.estado = 'Activo' AND mk.id_estacion = :idEstacion",
        nativeQuery = true
    )
    public List<MikrotikTarifaReuso> findByLikeEstado(@Param("nombre") String nombre, @Param("idEstacion") Long idEstacion);

    @Query(
        value = "SELECT * FROM mikrotik_tarifas_reuso mt \n" +
        "where mt.tipo = :tipo and mt.id_estacion= :idEstacion   and mt.nombre_padre LIKE concat('%' , :name , '%')",
        nativeQuery = true
    )
    public List<MikrotikTarifaReuso> findByidTipoAndIdEstacionAndName(@Param("tipo")Long tipo, @Param("idEstacion")Long idEstacion, @Param("name")String name);
}
