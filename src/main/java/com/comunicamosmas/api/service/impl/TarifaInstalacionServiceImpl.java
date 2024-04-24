package com.comunicamosmas.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.TarifaInstalacion;
import com.comunicamosmas.api.domain.TipoTecnologia;
import com.comunicamosmas.api.repository.ITarifaInstalacionDao;
import com.comunicamosmas.api.service.ITarifaInstalacionService;

@Service
public class TarifaInstalacionServiceImpl implements ITarifaInstalacionService{

    private final ITarifaInstalacionDao tarifaInstalacionDao;

    public TarifaInstalacionServiceImpl(ITarifaInstalacionDao tarifaInstalacionDao)
    {
        this.tarifaInstalacionDao = tarifaInstalacionDao;
    }

    @Override
    public List<TipoTecnologia> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public TipoTecnologia save(TipoTecnologia tarifaInstalacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public TipoTecnologia findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<List<TarifaInstalacion>> findByIdServicio(Long idServicio , Long valor) {
        // TODO Auto-generated method stub
        return tarifaInstalacionDao.findByIdServicio(idServicio ,valor);
    }
    
}
