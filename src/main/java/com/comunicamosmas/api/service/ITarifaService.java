package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Tarifa;
import com.comunicamosmas.api.service.dto.ContratosFirmasDTO;
import com.comunicamosmas.api.service.dto.DisctVelocidadDTO;
import com.comunicamosmas.api.service.dto.TarifasForCambioDTO;
import com.comunicamosmas.api.service.dto.valorDTO;

import java.util.List;
 

public interface ITarifaService {
    //listar todos
    public List<Tarifa> findAll();

    //guardar
    public Tarifa save(Tarifa tarifa);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Tarifa findById(Long id);
    
    public List<DisctVelocidadDTO> findVelocidadDisctByIdServicio(Long idServicio , Long idTecnologia);
    
    public List<TarifasForCambioDTO> tarifasForCambio( Long idServicio ,  Long velocidad , Long tipoTarifa , Long idTecnologia , Long valor);
    
    public List<valorDTO> findTarifaValor(Long idServicio, Long velocidad, Long tipoTarifa , Long idTecnologia);

    public int findSpeedByIdContrato(Long idContrato);

    public ContratosFirmasDTO.Datos findInfoServicioForFirmasContrato (Long idContrato);
}
