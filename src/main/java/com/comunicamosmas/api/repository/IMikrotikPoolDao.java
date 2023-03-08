package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikPool;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IMikrotikPoolDao extends CrudRepository<MikrotikPool, Long> {
    @Query(value = "SELECT * FROM mikrotik_pool mp WHERE mp.id_estacion =  :idEstacion", nativeQuery = true)
    public List<MikrotikPool> findByIdEstacion(@Param("idEstacion")Long idEstacion);
}
