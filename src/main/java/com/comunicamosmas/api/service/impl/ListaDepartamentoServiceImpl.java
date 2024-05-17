package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.ListaDepartamento;
import com.comunicamosmas.api.repository.IListaDepartamentoDao;
import com.comunicamosmas.api.service.IListaDepartamentoService;

@Service
public class ListaDepartamentoServiceImpl implements IListaDepartamentoService{

    private final IListaDepartamentoDao listaDepartamentoDao;

    public ListaDepartamentoServiceImpl(IListaDepartamentoDao listaDepartamentoDao)
    {
        this.listaDepartamentoDao = listaDepartamentoDao;
    }

    @Override
    public List<ListaDepartamento> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ListaDepartamento save(ListaDepartamento listaDepartamento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public ListaDepartamento findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ListaDepartamento> findByEstado(Long Estado) {
        // TODO Auto-generated method stub
        return listaDepartamentoDao.findByEstado(Estado);
    }
    
}
