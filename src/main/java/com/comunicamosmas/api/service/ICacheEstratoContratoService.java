package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheEstratoContrato;
import java.util.List;

public interface ICacheEstratoContratoService {
    //listar todos
    public List<CacheEstratoContrato> findAll();

    //guardar
    public CacheEstratoContrato save(CacheEstratoContrato cacheEstratoContrato);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public CacheEstratoContrato findById(Long id);
}
