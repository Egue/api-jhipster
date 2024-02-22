package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Pago;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

import java.util.List;
import java.util.Optional;

public interface IPagoService {
    //listar todos
    public List<Pago> findAll();

    //guardar
    public Pago save(Pago pago);

    public List<Pago> saveAll(List<Pago> pagos);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Pago findById(Long id);

    //reporte rc
    public List<ReciboCajaDTO> reporteReciboCaja(List<Integer> ciudades , Integer fecha_inicial , Integer fecha_final);

    public void registerPagoSupergiros(Contrato idContrato , int valorTotal , String comprobante);

    public int findLastRc(Long idServicio , String origen);
}
