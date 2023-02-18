package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.JustificaAnulacion;
import java.util.List;

public interface IJustificaAnulacionService {
    //listar todos
    public List<JustificaAnulacion> findAll();

    //guardar
    public JustificaAnulacion save(JustificaAnulacion justificaAnulacion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public JustificaAnulacion findById(Long id);
}
