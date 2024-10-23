package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.FinancieroNc;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IFinancieroNcDao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancieroNcServiceImpl implements IFinancieroNcService {

    private final IFinancieroNcDao financieroNcDao;

    public FinancieroNcServiceImpl(IFinancieroNcDao financieroNcDao)
    {
        this.financieroNcDao = financieroNcDao;
    }

    @Override
    public List<FinancieroNc> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public FinancieroNc save(FinancieroNc financieroNc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public FinancieroNc findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<FinancieroNc> findByIdDeuda(Long idDeuda) {
        // TODO Auto-generated method stub
        return financieroNcDao.findByIdDeuda(idDeuda);
    }

    
}
