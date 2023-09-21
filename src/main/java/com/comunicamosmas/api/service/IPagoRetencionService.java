package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.PagoRetencion;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

import java.util.List;

public interface IPagoRetencionService {
    //listar todos
    public List<PagoRetencion> findAll();

    //guardar
    public PagoRetencion save(PagoRetencion pagoRetencion);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public PagoRetencion findById(Long id);

    //
    public List<ReciboCajaDTO> reporte_recibo_caja(List<Integer> ciudad , Integer inicio , Integer fecha_final , List<String> tipo);
}
