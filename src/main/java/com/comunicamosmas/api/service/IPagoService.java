package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Pago;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

import java.util.List;

public interface IPagoService {
    //listar todos
    public List<Pago> findAll();

    //guardar
    public Pago save(Pago pago);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Pago findById(Long id);

    //reporte rc
    public List<ReciboCajaDTO> reporteReciboCaja(List<Integer> ciudades , Integer fecha_inicial , Integer fecha_final);
}
