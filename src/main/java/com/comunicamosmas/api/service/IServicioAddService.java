package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ServicioAdd;
import java.util.List;

public interface IServicioAddService {
    //listar todos
    public List<ServicioAdd> findAll();

    //guardar
    public ServicioAdd save(ServicioAdd servicioAdd);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ServicioAdd findById(Long id);
}
