package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CierreCerrado;
import com.comunicamosmas.api.repository.ICierreCerradoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CierreCerradoServiceImpl implements ICierreCerradoService {

    @Autowired
    ICierreCerradoDao cierreCerradoDao;

    @Override
    public List<CierreCerrado> findAll() {
        // TODO Auto-generated method stub
        return (List<CierreCerrado>) cierreCerradoDao.findAll();
    }

    @Override
    public CierreCerrado save(CierreCerrado cierreCerrado) {
        // TODO Auto-generated method stub
        cierreCerradoDao.save(cierreCerrado);
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CierreCerrado findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
