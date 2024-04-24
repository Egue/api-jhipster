package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.ListaBarrio;
import com.comunicamosmas.api.repository.IListaBarrioDao;
import com.comunicamosmas.api.service.IListaBarrioService;

@Service
public class ListaBarrioServiceImpl implements IListaBarrioService{

    private final IListaBarrioDao listaBarrioDao;

    public ListaBarrioServiceImpl(IListaBarrioDao listaBarrioDao)
    {
        this.listaBarrioDao = listaBarrioDao;
    }

    @Override
    public List<ListaBarrio> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ListaBarrio save(ListaBarrio listaBarrio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public ListaBarrio findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ListaBarrio> findByIdMunicipio(Long idMunicipio) {
        // TODO Auto-generated method stub
        return listaBarrioDao.findByIdMunicipio(idMunicipio);
    }
    
}
