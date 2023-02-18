package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OrdenArticuloRetira;
import java.util.List;

public interface IOrdenArticuloRetiraService {
    //listar todos
    public List<OrdenArticuloRetira> findAll();

    //guardar
    public OrdenArticuloRetira save(OrdenArticuloRetira ordenArticuloRetira);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OrdenArticuloRetira findById(Long id);
}
