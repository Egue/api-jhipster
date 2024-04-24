package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Empresa;
import java.util.List;
import java.util.Optional;

public interface IEmpresaService {
    //listar todos
    public List<Empresa> findAll();

    //guardar
    public void save(Empresa empresa);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public Empresa findById(Long id);

    public List<Empresa> findByLikeNombreComercial(String empresa);
    
    public Empresa findByIdContrato(Long idContrato);

    public Optional<List<Empresa>> findAllByStatus();
}
