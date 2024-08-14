package com.comunicamosmas.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.service.dto.ClienteDTO;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;
import com.comunicamosmas.api.service.dto.ClientesDeclineClausuraDTO;

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

    public Page<ClientePortalWebDTO> pageClienteSyncronicePortalWeb(Pageable page);

    public void clientesDeclineClausura();
}
