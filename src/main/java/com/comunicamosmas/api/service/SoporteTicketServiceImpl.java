package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.SoporteTicket; 
import com.comunicamosmas.api.repository.ISoporteTicketDao;
import com.comunicamosmas.api.service.dto.SoporteTicketDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoporteTicketServiceImpl implements ISoporteTicketService {

    @Autowired
    ISoporteTicketDao ticketService;

    @Override
    public List<SoporteTicket> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public SoporteTicket save(SoporteTicket soporteTicket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public SoporteTicket findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<SoporteTicketDTO> reporteTicket(Long tipo, Long inicio, Long finalf) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = ticketService.findTicketByTipo(tipo, inicio, finalf);

        List<SoporteTicketDTO> reporte = result.map(resp-> {

            List<SoporteTicketDTO> repo = new ArrayList<>();
            for(Object[] rs: resp)
            {
                SoporteTicketDTO obj = new SoporteTicketDTO();
                obj.setId((Integer) rs[0]);
                obj.setServicio((String) rs[1]);
                obj.setContrato((Integer) rs[2]);
                obj.setCausa((String) rs[3]);
                obj.setCreatedAt((String) rs[4].toString());
                obj.setCliente((String) rs[5]);
                obj.setDocumento((String) rs[6].toString());
                obj.setCreado_por((String) rs[7]);
                obj.setRespuesta((String)rs[8]);

                repo.add(obj);
            }

            return repo;
            
        }).orElse(new ArrayList<>());

        return reporte ; 
    }

   
     
}
