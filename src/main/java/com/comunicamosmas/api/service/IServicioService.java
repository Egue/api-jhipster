package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Servicio;
import java.util.List;

public interface IServicioService {
    //listar todos
    public List<Servicio> findAll();

    //guardar
    public Servicio save(Servicio servicio);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Servicio findById(Long id);

    public List<Servicio> findByName(String name);
     
}
