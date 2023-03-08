package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikSegmentoIp;
import com.comunicamosmas.api.service.dto.SegmentoWithPoolDTO; 
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IMikrotikSegmentoIpDao extends CrudRepository<MikrotikSegmentoIp, Long> {
    @Query(value = "SELECT * FROM mikrotik_segmento_ip m WHERE m.id_estacion = :id", nativeQuery = true)
    public List<MikrotikSegmentoIp> findByIdEstacion(@Param("id")Long id);

    @Query(
        value = "SELECT count(segmento) FROM mikrotik_segmento_ip ms WHERE ms.id_estacion = :idEstacion AND ms.segmento = :segmento",
        nativeQuery = true
    )
    public int countFindByIdEstacionAndName(@Param("idEstacion") Long idEstacion, @Param("segmento") String segmento);

    @Query(value = "SELECT * FROM mikrotik_segmento_ip ms WHERE ms.id_pool = :idPool", nativeQuery = true)
    public List<MikrotikSegmentoIp> findByIdPool(@Param("idPool")Long idPool);

    @Query(
        value = "SELECT\n" +
        "ms.id_segmento_ip as id, ms.id_estacion as idEstacion , ms.segmento , \n" +
        "ms.estado  , mikrotik_pool.nombre_pool as nombrePool FROM mikrotik_segmento_ip ms \n" +
        "inner join mikrotik_pool on mikrotik_pool.id_pool = ms.id_pool\n" +
        "where mikrotik_pool.id_pool = :idPool AND ms.estado = 'vacio'",
        nativeQuery = true
    )
    public List<SegmentoWithPoolDTO> findByidPoolAndEstado(@Param("idPool")Long idPool);

    @Query(
        value = "SELECT count(ms.id_estacion) as ip FROM mikrotik_segmento_ip ms\r\n" +
        "inner join estaciones on estaciones.id_estacion = ms.id_estacion\n" +
        "where estaciones.api_ip = :ip and  ms.segmento = :segmento",
        nativeQuery = true
    )
    public int countDFindIpByPublic(@Param("ip")String ip, @Param("segmento")String segmento);
}
