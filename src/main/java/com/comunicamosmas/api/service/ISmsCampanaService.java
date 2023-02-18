package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SmsCampana;
import java.util.List;

public interface ISmsCampanaService {
    //listar todos
    public List<SmsCampana> findAll();

    //guardar
    public SmsCampana save(SmsCampana smsCampana);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SmsCampana findById(Long id);
}
