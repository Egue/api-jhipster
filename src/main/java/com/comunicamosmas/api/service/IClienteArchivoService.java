package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ClienteArchivo;
import java.util.List;

public interface IClienteArchivoService {
    //listar todos
    public List<ClienteArchivo> findAll();

    //guardar
    public ClienteArchivo save(ClienteArchivo clienteArchivo);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ClienteArchivo findById(Long id);
}
