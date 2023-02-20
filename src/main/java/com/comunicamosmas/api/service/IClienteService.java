package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Cliente; 
import java.util.List;

public interface IClienteService {
    //listar todos
    public List<Cliente> findAll();

    //guardar
    public Cliente save(Cliente cliente);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Cliente findById(Long id);

    public Cliente getClientByIdContrato(Long idContrato);

    public List<Cliente> findByDocumento(String documento);
}
