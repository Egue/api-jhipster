package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.ISystemConfigService;

@Service
public class SystemConfigServiceImpl implements ISystemConfigService{

    @Autowired
    ISystemConfigDao systemDao;

    @Override
    public void save(SystemConfig system) {
        // TODO Auto-generated method stub
       systemDao.save(system);
    }

    @Override
    public List<SystemConfig> findAll() {
        // TODO Auto-generated method stub
         return (List<SystemConfig>)systemDao.findAll();    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        systemDao.deleteById(id);
    }

    @Override
    public SystemConfig findByOrigen(String origen) {
        // TODO Auto-generated method stub
        return (SystemConfig) systemDao.findByOrigen(origen);
    }
    
}
