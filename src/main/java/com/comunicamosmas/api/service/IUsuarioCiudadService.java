package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.UsuarioCiudad;
import java.util.List;

public interface IUsuarioCiudadService {
    //listar todos
    public List<UsuarioCiudad> findAll();

    //guardar
    public UsuarioCiudad save(UsuarioCiudad usuarioCiudad);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public UsuarioCiudad findById(Long id);
}
