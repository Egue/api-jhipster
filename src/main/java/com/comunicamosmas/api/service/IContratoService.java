package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.web.rest.vm.IListContratoVM;
import java.util.List;

public interface IContratoService {
    //listar todos
    public List<Contrato> findAll();

    //guardar
    public Contrato save(Contrato contrato);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public Contrato findById(Long id);

    public List<IListContratoVM> findByIdCliente(Long idCliente);
}
