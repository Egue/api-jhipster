package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Direccion;
import com.comunicamosmas.api.service.dto.ContratosFirmasDTO;
import com.comunicamosmas.api.service.dto.DireccionDTO;

import java.util.List;

public interface IDireccionService {
    //listar todos
    public List<Direccion> findAll();

    //guardar
    public Direccion save(Direccion direccion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Direccion findById(Long id);

    public List<DireccionDTO> findByIdCliente(Long idCliente);

    public ContratosFirmasDTO.DatosContacto findInfoByFimra(Long idContrato);
}
