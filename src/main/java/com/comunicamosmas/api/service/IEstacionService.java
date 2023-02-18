package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Estacion;
import java.util.List;

public interface IEstacionService {
    //listar todos
    public List<Estacion> findAll();

    //guardar
    public Estacion save(Estacion estacion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Estacion findById(Long id);

    public List<Estacion> findByNombreAndIdServicio(String nombreEstacion, Long idServicio);
}
