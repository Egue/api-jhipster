package com.comunicamosmas.api.service;

 
import com.comunicamosmas.api.domain.Servicio;
 
import com.comunicamosmas.api.repository.IServicioDao;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioServiceImpl implements IServicioService {

    @Autowired
    IServicioDao servicioDao;

    @Override
    public List<Servicio> findAll() {
        
        return (List<Servicio>)servicioDao.findAll();
    }

    @Override
    public Servicio save(Servicio servicio) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Servicio findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Servicio> findByName(String name) {
        // TODO Auto-generated method stub
        return (List<Servicio>) servicioDao.findByName(name);
    }

    @Override
    public Optional<List<Servicio>> findByIdEmpresa(Long idEmpresa) {
        // TODO Auto-generated method stub
        return servicioDao.findByIdEmpresa(idEmpresa);
    }

    
}
