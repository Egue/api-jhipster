package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Documento;
import java.util.List;

public interface IDocumentoService {
    //listar todos
    public List<Documento> findAll();

    //guardar
    public Documento save(Documento documento);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Documento findById(Long id);
}
