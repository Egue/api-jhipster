package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.service.dto.ArrayListDTO;
import com.comunicamosmas.api.service.dto.CarteraDTO;
import com.comunicamosmas.api.service.dto.ContratoInfoFacturaDTO;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO; 
import java.util.List;
import java.util.Optional;

public interface IContratoService {
    //listar todos
    public List<Contrato> findAll();

    //guardar
    public Contrato save(Contrato contrato);

    //eliminar
    public void deleteById(Long id);

    //buscar por id
    public Contrato findById(Long id);

    public List<ListContratoDTO> findByIdCliente(Long idCliente);
    
    public DatosClienteDTO datosContactoByIdContrato(Long idContrato);
    
    //buscar informacion de contrato para factura
    public ContratoInfoFacturaDTO contratoFindFactura(Long idContrato);

     

    //
    public List<CarteraDTO> carteraByServicio(ArrayListDTO datos);

    public void updateSaldoFavor(Contrato contrato , Float valor);
 
}
