package com.comunicamosmas.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.SystemConfig;

public interface ISystemConfigDao extends CrudRepository<SystemConfig , Integer>{

    @Query(value="SELECT * FROM system_config WHERE system_config.origen = :origen LIMIT 0,1" ,  nativeQuery = true)
    public SystemConfig findByOrigen(@Param("origen") String origen);
}
