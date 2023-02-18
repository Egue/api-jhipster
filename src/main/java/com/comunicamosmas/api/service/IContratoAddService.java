package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoAdd;
import java.util.List;

public interface IContratoAddService {
    //listar todos
    public List<ContratoAdd> findAll();

    //guardar
    public ContratoAdd save(ContratoAdd contratoAdd);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoAdd findById(Long id);
}
