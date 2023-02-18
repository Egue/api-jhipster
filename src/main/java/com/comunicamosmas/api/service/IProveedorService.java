package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Proveedor;
import java.util.List;

public interface IProveedorService {
    //listar todos
    public List<Proveedor> findAll();

    //guardar
    public void save(Proveedor proveedor);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public Proveedor findById(Long id);

    public List<Proveedor> findByEstadoActivo(int estado);

    public List<Proveedor> findByLikeRazonSocial(String RazonSocial);
}
