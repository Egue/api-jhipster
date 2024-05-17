package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.TarifaInstalacion;
import com.comunicamosmas.api.domain.TipoTecnologia;
import java.util.List;
import java.util.Optional;

public interface ITarifaInstalacionService {
    //listar todos
    public List<TipoTecnologia> findAll();

    //guardar
    public TipoTecnologia save(TipoTecnologia tarifaInstalacion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public TipoTecnologia findById(Long id);

    public Optional<List<TarifaInstalacion>> findByIdServicio(Long idServicio , Long valor);
}
