package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioCargaDetalle;
import java.util.List;

public interface IInventarioCargaDetalleService {
    //listar todos
    public List<InventarioCargaDetalle> findAll();

    //guardar
    public InventarioCargaDetalle save(InventarioCargaDetalle inventarioCargaDetalle);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public InventarioCargaDetalle findById(Long id);
}
