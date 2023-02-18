package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ServicioSecundario;
import java.util.List;

public interface IServicioSecundarioService {
    //listar todos
    public List<ServicioSecundario> findAll();

    //guardar
    public ServicioSecundario save(ServicioSecundario servicioSecundario);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ServicioSecundario findById(Long id);
}
