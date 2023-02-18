package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.TarifaInstalacion;
import java.util.List;

public interface ITarifaInstalacionService {
    //listar todos
    public List<TarifaInstalacion> findAll();

    //guardar
    public TarifaInstalacion save(TarifaInstalacion tarifaInstalacion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public TarifaInstalacion findById(Long id);
}
