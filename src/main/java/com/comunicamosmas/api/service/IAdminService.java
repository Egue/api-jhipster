package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import java.util.List;

public interface IAdminService {
    //listar todos
    public List<Admin> findAll();

    //guardar
    public Admin save(Admin admin);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Admin findById(Long id);
}
