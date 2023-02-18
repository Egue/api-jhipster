package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CierreAbierto;
import com.comunicamosmas.api.repository.ICierreAbiertoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CierreAbiertoServiceImpl implements ICierreAbiertoService {

    @Autowired
    ICierreAbiertoDao cierreAbiertoDao;

    @Override
    public List<CierreAbierto> findAll() {
        // TODO Auto-generated method stub
        return (List<CierreAbierto>) cierreAbiertoDao.findAll();
    }

    @Override
    public CierreAbierto save(CierreAbierto cierreAbierto) {
        // TODO Auto-generated method stub
        cierreAbiertoDao.save(cierreAbierto);
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CierreAbierto findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
