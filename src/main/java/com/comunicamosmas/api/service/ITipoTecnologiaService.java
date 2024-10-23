package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.TarifaInstalacion;
import com.comunicamosmas.api.domain.TipoTecnologia;

import java.util.List;
import java.util.Optional;

public interface ITipoTecnologiaService {
    //listar todos
    public List<TarifaInstalacion> findAll();

    //guardar
    public TarifaInstalacion save(TarifaInstalacion tipoTecnologia);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public TarifaInstalacion findById(Long id);

    public Optional<List<TipoTecnologia>> findByServicio(Long servicio);
}
