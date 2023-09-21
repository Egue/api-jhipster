package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.service.dto.ClienteDTO;

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

    public List<ClienteDTO> findByName(String nombre);

    public List<ClienteDTO> findByCus(Long cus);

    public List<ClienteDTO> validExisteCliente(String documento);
}
