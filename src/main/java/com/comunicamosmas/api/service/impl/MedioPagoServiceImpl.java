package com.comunicamosmas.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.MedioPago;
import com.comunicamosmas.api.repository.IMedioPagoDao;
import com.comunicamosmas.api.service.IMedioPagoService;

@Service
public class MedioPagoServiceImpl implements IMedioPagoService{

    @Autowired
    IMedioPagoDao medioPagoDao; 

    @Override
    public List<MedioPago> findAll() {
        // TODO Auto-generated method stub
        return (List<MedioPago>) medioPagoDao.findAll();
    }

    @Override
    public MedioPago save(MedioPago medioPago) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public MedioPago findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    
}
