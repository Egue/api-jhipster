package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheEstratoTraslado;
import com.comunicamosmas.api.repository.ICacheEstratoTrasladoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheEstratoTrasladoServiceImpl implements ICacheEstratoTrasladoService {

    @Autowired
    ICacheEstratoTrasladoDao cacheEstratoTrasladoDao;

    @Override
    public List<CacheEstratoTraslado> findAll() {
        // TODO Auto-generated method stub
        return (List<CacheEstratoTraslado>) cacheEstratoTrasladoDao.findAll();
    }

    @Override
    public void save(CacheEstratoTraslado cacheEstratoTraslado) {
        // TODO Auto-generated method stub
        cacheEstratoTrasladoDao.save(cacheEstratoTraslado);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public CacheEstratoTraslado findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
