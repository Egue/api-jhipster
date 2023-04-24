package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;

import java.util.List;

public interface IDeudaService {
    //listar todos
    public List<Deuda> findAll();

    //guardar
    public Deuda save(Deuda deuda);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Deuda findById(Long id);
    
    //buscar por contrado
    public List<EstadoCuentaDeudasDTO> findByIdContrato(Long contrato);
}
