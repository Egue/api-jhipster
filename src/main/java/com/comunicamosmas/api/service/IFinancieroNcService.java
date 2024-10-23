package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.FinancieroNc;
import java.util.List;

public interface IFinancieroNcService {
    //listar todos
    public List<FinancieroNc> findAll();

    //guardar
    public FinancieroNc save(FinancieroNc financieroNc);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public FinancieroNc findById(Long id);

    public List<FinancieroNc> findByIdDeuda(Long idDeuda);
}
