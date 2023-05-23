package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.SystemConfig;

public interface ISystemConfigService {
    
    public void save(SystemConfig system);

    public List<SystemConfig> findAll();

    public void delete(Integer id);

    public SystemConfig findByOrigen(String origen);
}
