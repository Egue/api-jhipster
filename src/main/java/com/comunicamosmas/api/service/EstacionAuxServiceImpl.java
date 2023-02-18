package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.EstacionAux;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IEstacionAuxDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstacionAuxServiceImpl implements IEstacionAuxService {

    @Autowired
    IEstacionAuxDao estacionAuxDao;

    @Override
    public List<EstacionAux> findAll() {
        // TODO Auto-generated method stub
        return (List<EstacionAux>) estacionAuxDao.findAll();
    }

    @Override
    public void save(EstacionAux estacionAux) {
        // TODO Auto-generated method stub
        estacionAuxDao.save(estacionAux);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        estacionAuxDao.deleteById(id);
    }

    @Override
    public EstacionAux findById(Long id) {
        // TODO Auto-generated method stub
        return estacionAuxDao.findById(id).orElse(null);
    }

    @Override
    public List<EstacionAux> findByIdEstacion(Long id) {
        // TODO Auto-generated method stub
        return (List<EstacionAux>) estacionAuxDao.findByIdEstacion(id);
    }
}
