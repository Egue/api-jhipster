package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheContratoSaldo;
import com.comunicamosmas.api.repository.ICacheContratoSaldoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheContratoSaldoServiceImpl implements ICacheContratoSaldoService {

    @Autowired
    ICacheContratoSaldoDao cacheContratoSaldoDao;

    @Override
    public List<CacheContratoSaldo> findAll() {
        // TODO Auto-generated method stub
        return (List<CacheContratoSaldo>) cacheContratoSaldoDao.findAll();
    }

    @Override
    public void save(CacheContratoSaldo cacheContratoSaldo) {
        // TODO Auto-generated method stub
        cacheContratoSaldoDao.save(cacheContratoSaldo);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public CacheContratoSaldo findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
