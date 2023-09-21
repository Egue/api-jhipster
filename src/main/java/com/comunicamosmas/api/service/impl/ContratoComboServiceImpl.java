package com.comunicamosmas.api.service.impl;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.ContratoCombo;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IContratoComboDao;
import com.comunicamosmas.api.service.IAdminService;
import com.comunicamosmas.api.service.IContratoComboService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoComboServiceImpl implements IContratoComboService {

    @Autowired
    IContratoComboDao comboDao;

    @Override
    public List<ContratoCombo> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ContratoCombo save(ContratoCombo contratoCombo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public ContratoCombo findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<List<Object[]>> findByEmpresaAndIdCombo(Long empresa, Integer idCombo) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = comboDao.findByEmpresaAndIdCombo(empresa , idCombo);

        return result;
    }

     
}
