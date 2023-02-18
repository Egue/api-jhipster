package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.repository.IEstacionDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstacionServiceImpl implements IEstacionService {

    @Autowired
    IEstacionDao estacionDao;

    @Override
    public List<Estacion> findAll() {
        // TODO Auto-generated method stub
        return (List<Estacion>) estacionDao.findAll();
    }

    @Override
    public Estacion save(Estacion estacion) {
        estacionDao.save(estacion);
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        estacionDao.deleteById(id);
        return null;
    }

    @Override
    public Estacion findById(Long id) {
        return estacionDao.findById(id).orElse(null);
    }

    @Override
    public List<Estacion> findByNombreAndIdServicio(String nombreEstacion, Long idServicio) {
        // TODO Auto-generated method stub
        return (List<Estacion>) estacionDao.findByNombreAndIdServicio(nombreEstacion, idServicio);
    }
}
