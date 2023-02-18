package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheEstratoContrato;
import com.comunicamosmas.api.repository.ICacheEstratoContratoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheEstratoContratoServiceImpl implements ICacheEstratoContratoService {

    @Autowired
    ICacheEstratoContratoDao cacheEstratoContratoDao;

    @Override
    public List<CacheEstratoContrato> findAll() {
        // TODO Auto-generated method stub
        return (List<CacheEstratoContrato>) cacheEstratoContratoDao.findAll();
    }

    @Override
    public CacheEstratoContrato save(CacheEstratoContrato cacheEstratoContrato) {
        // TODO Auto-generated method stub
        cacheEstratoContratoDao.save(cacheEstratoContrato);
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CacheEstratoContrato findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
