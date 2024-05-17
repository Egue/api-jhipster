package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.TarifaInstalacion;
import com.comunicamosmas.api.domain.TipoTecnologia; 
import com.comunicamosmas.api.repository.ITipoTecnologiaDao;

import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;

@Service
public class TipoTecnologiaServiceImpl implements ITipoTecnologiaService {

   private final ITipoTecnologiaDao tipoTecnologiaDao;

   public TipoTecnologiaServiceImpl(ITipoTecnologiaDao tipoTecnologiaDao)
   {
    this.tipoTecnologiaDao = tipoTecnologiaDao;
   }

    @Override
    public List<TarifaInstalacion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public TarifaInstalacion save(TarifaInstalacion tipoTecnologia) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public TarifaInstalacion findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<List<TipoTecnologia>> findByServicio(Long servicio) {
        // TODO Auto-generated method stub
        return tipoTecnologiaDao.findByServicio(servicio);
    }

     
}
