package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.UsuarioServicioFull;
import java.util.List;

public interface IUsuarioServicioFullService {
    //listar todos
    public List<UsuarioServicioFull> findAll();

    //guardar
    public UsuarioServicioFull save(UsuarioServicioFull usuarioServicioFull);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public UsuarioServicioFull findById(Long id);
}
