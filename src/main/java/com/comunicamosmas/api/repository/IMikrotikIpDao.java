package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikIp;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IMikrotikIpDao extends CrudRepository<MikrotikIp, Long> {
    @Query(value = "SELECT * FROM mikrotik_ip mp WHERE mp.id_segmento_ip = ?", nativeQuery = true)
    public List<MikrotikIp> findAllBySegmentoIp(Long idSegmentoIp);

    @Query(value = "SELECT * FROM mikrotik_ip mp WHERE mp.id_segmento_ip =:idSegmentoIp AND mp.estado <> 1", nativeQuery = true)
    public List<MikrotikIp> findAllBySegmentoIpStatus(Long idSegmentoIp);

    @Query(value = "SELECT count(id_ip) as ip FROM mikrotik_ip mp WHERE mp.estado=1  AND mp.id_segmento_ip = ?", nativeQuery = true)
    public int countActiveByIdSegmento(Long idSegmento);

    @Query(
        value = "SELECT mi.id_ip , mi.id_segmento_ip , mi.ip, mi.estado FROM mikrotik_ip mi\r\n" +
        "inner join mikrotik_segmento_ip ms on ms.id_segmento_ip = mi.id_segmento_ip\r\n" +
        "inner join mikrotik_pool mp on mp.id_pool = ms.id_pool\r\n" +
        "where ms.id_pool = :idPool and mi.estado != 1 limit 0,1",
        nativeQuery = true
    )
    public List<MikrotikIp> findByIdPool(Long idPool);
}
