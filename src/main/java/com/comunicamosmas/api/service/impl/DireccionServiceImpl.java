package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Direccion; 
import com.comunicamosmas.api.repository.IDireccionDao;
import com.comunicamosmas.api.service.IDireccionService;
import com.comunicamosmas.api.service.dto.DireccionDTO;

@Service
public class DireccionServiceImpl implements IDireccionService{

    private final IDireccionDao direccionDao;

    DireccionServiceImpl(IDireccionDao direccionDao)
    {
        this.direccionDao = direccionDao;
    }

    @Override
    public List<Direccion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Direccion save(Direccion direccion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Direccion findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<DireccionDTO> findByIdCliente(Long idCliente) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = direccionDao.findByIdCliente(idCliente);

        List<DireccionDTO> direcciones = result.map(resp-> resp.stream().map(rs ->{
            DireccionDTO obj = new DireccionDTO();
            obj.setId((Integer) rs[0]);
            obj.setIdCliente((Integer) rs[1]);
            obj.setBarrio((String) rs[2]);
            obj.setLatitud((String) rs[3]);
            obj.setLongitud((String) rs[4]);
            obj.setNota((String) rs[5]);
            obj.setEstado((Integer) rs[6]);
            obj.setRara((Integer) rs[7]);
            obj.setTipo((String) rs[8]);
            obj.setDireccion((String) rs[9]);          

            return obj;
        }).collect(Collectors.toList())).orElse(new ArrayList<>());
        
        return direcciones; 
    }
    
}
