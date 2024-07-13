package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDeudaService {
    //listar todos
    public List<Deuda> findAll();

    //guardar
    public Deuda save(Deuda deuda);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public Deuda findById(Long id);
    
    //buscar por contrado
    public Page<EstadoCuentaDeudasDTO> findByIdContrato(Long contrato , Pageable page);
    
    public String findDeudaByIdContrato(Long idContrato);
    
    /**
     * buscar facturas por mes servicio*/
    public List<DeudasForFacturaDTO> findDeudaByFacturaAndMesServiceAndIdEmpresa(Long factura , Long mesServicio, Long idEmpresa , Integer idCliente , String origen);

    /***
     * buscar saldos anteriores
     */
    public Optional<List<Object[]>> findSalgoAnterior(Integer fecha , List<Integer> consulta);

    public List<EmailCampaignDetalleDTO> findDeudaByIdCampaingAndOrigen(String factura , Integer idCampaign);

    //find deudas por id_contrato
    public List<DeudasForFacturaDTO> deudasByIdContrato(Long idContrato);
}
