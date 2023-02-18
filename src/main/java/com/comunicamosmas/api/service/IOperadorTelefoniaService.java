package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OperadorTelefonia;
import java.util.List;

public interface IOperadorTelefoniaService {
    //listar todos
    public List<OperadorTelefonia> findAll();

    //guardar
    public OperadorTelefonia save(OperadorTelefonia operadorTelefonia);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OperadorTelefonia findById(Long id);
}
