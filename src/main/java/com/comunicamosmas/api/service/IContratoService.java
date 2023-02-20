package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO; 
import java.util.List;

public interface IContratoService {
    //listar todos
    public List<Contrato> findAll();

    //guardar
    public Contrato save(Contrato contrato);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public Contrato findById(Long id);

    public List<ListContratoDTO> findByIdCliente(Long idCliente);
    
    public DatosClienteDTO datosContactoByIdContrato(Long idContrato);
}
