package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;

import java.util.List;

public interface IDeudaService {
    //listar todos
    public List<Deuda> findAll();

    //guardar
    public Deuda save(Deuda deuda);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Deuda findById(Long id);
    
    //buscar por contrado
    public List<EstadoCuentaDeudasDTO> findByIdContrato(Long contrato);
    
    public String findDeudaByIdContrato(Long idContrato);
    
    /**
     * buscar facturas por mes servicio*/
    public List<DeudasForFacturaDTO> findDeudaByFacturaAndMesServiceAndIdEmpresa(Long factura , Long mesServicio, Long idEmpresa);

    /***
     * buscar saldos anteriores
     */
    public List<Object[]> findSalgoAnterior(Integer fecha , String consulta);
}
