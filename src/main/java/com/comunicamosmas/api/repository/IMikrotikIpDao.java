package com.comunicamosmas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.MikrotikIp;
import com.comunicamosmas.api.service.dto.MikrotikSegmentoIPDTO;

public interface IMikrotikIpDao extends CrudRepository<MikrotikIp, Long> {
    @Query(value = "SELECT * FROM mikrotik_ip mp WHERE mp.id_segmento_ip = ?", nativeQuery = true)
    public List<MikrotikIp> findAllBySegmentoIp(Long idSegmentoIp);

    @Query(value = "SELECT * FROM mikrotik_ip mp WHERE mp.id_segmento_ip =:idSegmentoIp AND mp.estado <> 1", nativeQuery = true)
    public List<MikrotikIp> findAllBySegmentoIpStatus(@Param("idSegmentoIp")Long idSegmentoIp);

    @Query(value = "SELECT count(id_ip) as ip FROM mikrotik_ip mp WHERE mp.estado=1  AND mp.id_segmento_ip = :idSegmento", nativeQuery = true)
    public int countActiveByIdSegmento(@Param("idSegmento")Long idSegmento);

    /*@Query(
        value = "SELECT mi.id_ip , mi.id_segmento_ip , mi.ip, mi.estado FROM mikrotik_ip mi\r\n" +
        "inner join mikrotik_segmento_ip ms on ms.id_segmento_ip = mi.id_segmento_ip\r\n" +
        "inner join mikrotik_pool mp on mp.id_pool = ms.id_pool\r\n" +
        "where ms.id_pool = :idPool and mi.estado != 1 limit 0,1",
        nativeQuery = true
    )*/
    @Query(value="SELECT * FROM mikrotik_segmento_ip seg \r\n" + 
        " WHERE seg.id_pool = :id \r\n"    ,nativeQuery = true)
    public Optional<List<Object[]>> findByIdPool(@Param("id")Long idPool);
}
