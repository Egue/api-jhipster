package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheEstratoTraslado;
import java.util.List;

public interface ICacheEstratoTrasladoService {
    //listar todos
    public List<CacheEstratoTraslado> findAll();

    //guardar
    public void save(CacheEstratoTraslado cacheEstratoTraslado);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public CacheEstratoTraslado findById(Long id);
}
