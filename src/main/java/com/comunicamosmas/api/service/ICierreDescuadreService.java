package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CierreDescuadre;
import java.util.List;

public interface ICierreDescuadreService {
    //listar todos
    public List<CierreDescuadre> findAll();

    //guardar
    public CierreDescuadre save(CierreDescuadre cierreDescuadre);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public CierreDescuadre findById(Long id);
}
