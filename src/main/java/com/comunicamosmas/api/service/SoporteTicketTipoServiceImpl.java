package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.SoporteTicketTipo; 
import com.comunicamosmas.api.repository.ISoporteTicketTipoDao;
import com.comunicamosmas.api.service.dto.TicketTipoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoporteTicketTipoServiceImpl implements ISoporteTicketTipoService {

    @Autowired
    ISoporteTicketTipoDao tipoDao;

    @Override
    public List<SoporteTicketTipo> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public SoporteTicketTipo save(SoporteTicketTipo soporteTicketTipo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public SoporteTicketTipo findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<TicketTipoDTO> findByIdServicio(Long idServicio) {
         
        Optional<List<Object[]>> result = tipoDao.findByIdServicio(idServicio);

        List<TicketTipoDTO> tipo = result.map(resp -> {
            List<TicketTipoDTO> tip = new ArrayList<>();

            for(Object[] rs : resp)
            {
                TicketTipoDTO obj = new TicketTipoDTO();

                obj.setId((Integer) rs[0]);
                obj.setNombre((String) rs[1]);

                tip.add(obj);
            }

            return tip;
        }).orElse(new ArrayList<>());

        return tipo;

    }

    
}
