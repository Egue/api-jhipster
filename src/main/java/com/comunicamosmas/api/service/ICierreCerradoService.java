package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CierreCerrado;
import java.util.List;

public interface ICierreCerradoService {
    //listar todos
    public List<CierreCerrado> findAll();

    //guardar
    public CierreCerrado save(CierreCerrado cierreCerrado);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public CierreCerrado findById(Long id);
}
