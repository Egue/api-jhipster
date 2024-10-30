package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import com.comunicamosmas.api.repository.IMikrotikTarifaReusoDao;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MikrotikTarifaReusoServiceImpl implements IMikrotikTarifaReusoService {

    @Autowired
    IMikrotikTarifaReusoDao mikrotikTarifaReusoDao;

    @Override
    public void save(MikrotikTarifaReuso mikrotikTarifaReuso) {
        mikrotikTarifaReusoDao.save(mikrotikTarifaReuso);
    }

    @Override
    public MikrotikTarifaReuso findById(Long id) {
        return mikrotikTarifaReusoDao.findById(id).orElse(null);
    }

    @Override
    public List<MikrotikTarifaReuso> findAll() {
        // TODO Auto-generated method stub
        return (List<MikrotikTarifaReuso>) mikrotikTarifaReusoDao.findAll();
    }

    @Override
    public void delete(Long id) {
        mikrotikTarifaReusoDao.deleteById(id);
    }

    @Override
    public List<MikrotikTarifaReuso> findByIdEstacion(Long idEstacion) {
        // TODO Auto-generated method stub
        return (List<MikrotikTarifaReuso>) mikrotikTarifaReusoDao.findByIdEstacion(idEstacion);
    }

    @Override
    public List<MikrotikTarifaReuso> findByEstado(String estado) {
        // TODO Auto-generated method stub
        return (List<MikrotikTarifaReuso>) mikrotikTarifaReusoDao.findByEstado(estado);
    }

    @Override
    public List<MikrotikTarifaReuso> findByLikeEstado(String name, Long idEstacion) {
        // TODO Auto-generated method stub
        return (List<MikrotikTarifaReuso>) mikrotikTarifaReusoDao.findByLikeEstado(name, idEstacion);
    }

    @Override
    public List<MikrotikTarifaReuso> findByidTipoAndIdEstacionAndName(Long tipo, Long idEstacion, String name) {
        // TODO Auto-generated method stub
        return (List<MikrotikTarifaReuso>) mikrotikTarifaReusoDao.findByidTipoAndIdEstacionAndName(tipo, idEstacion, name);
    }

    @Override
    public MikrotikTarifaReuso updated(MikrotikTarifaReuso tarifaReuso) {
        // TODO Auto-generated method stub
        Optional<MikrotikTarifaReuso> reuso = mikrotikTarifaReusoDao
                .findById(tarifaReuso.getId()).map(exist -> {
                    exist.setBajada(tarifaReuso.getBajada());
                    exist.setComment(tarifaReuso.getComment());
                    exist.setIdEstacion(tarifaReuso.getIdEstacion());
                    exist.setNombrePadre(tarifaReuso.getNombrePadre());
                    exist.setPriority(tarifaReuso.getPriority());
                    exist.setReuso(tarifaReuso.getReuso());
                    exist.setSubida(tarifaReuso.getSubida());
                    exist.setTipo(tarifaReuso.getTipo());
                    mikrotikTarifaReusoDao.save(exist);
                    return exist;
                });
        return reuso.get();
    }
}
