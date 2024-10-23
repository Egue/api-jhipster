package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.ListaMunicipio;
import com.comunicamosmas.api.repository.IListaMunicipioDao;
import com.comunicamosmas.api.service.IListaMunicipioService;

@Service
public class ListaMunicipioServiceImpl implements IListaMunicipioService{

    private final IListaMunicipioDao listaMunicipioDao;

    public ListaMunicipioServiceImpl(IListaMunicipioDao listaMunicipioDao)
    {
        this.listaMunicipioDao = listaMunicipioDao;
    }

    @Override
    public List<ListaMunicipio> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ListaMunicipio save(ListaMunicipio listaMunicipio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public ListaMunicipio findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ListaMunicipio> findByDepartamentoid(Long departamentoid) {
        // TODO Auto-generated method stub
        return this.listaMunicipioDao.findByDepartamentoid(departamentoid);
    }
    
}
