package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.PagoLineaVersionDos;
 

import org.springframework.data.domain.Page;

public interface IPagoLineaVersionDosService {
    //listar todos
    public Page<PagoLineaVersionDos> findAll();

    //guardar
    public void save(PagoLineaVersionDos pagoLineaVersionDos);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public PagoLineaVersionDos findById(Long id);

    public void iterarReconexionesAndCorte();
}
