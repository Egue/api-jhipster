package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.ClausulaPermanencia;
import com.comunicamosmas.api.repository.IClausulaPermanenciaDao;
import com.comunicamosmas.api.service.IClausulaPermanenciaService;

@Service
public class ClausulaPermanenciaServiceImpl implements IClausulaPermanenciaService{

    private final IClausulaPermanenciaDao clausulaPermanenciaDao;

    public ClausulaPermanenciaServiceImpl(IClausulaPermanenciaDao clausulaPermanenciaDao)
    {
        this.clausulaPermanenciaDao = clausulaPermanenciaDao;
    }

    @Override
    public List<ClausulaPermanencia> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ClausulaPermanencia save(ClausulaPermanencia clausulaPermanencia) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public ClausulaPermanencia findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ClausulaPermanencia findByIdContrato(Long idContrato) {
        // TODO Auto-generated method stub
        return clausulaPermanenciaDao.findByIdContrato(idContrato);
    }
    
}
