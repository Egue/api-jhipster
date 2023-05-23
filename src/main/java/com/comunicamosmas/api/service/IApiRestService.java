package com.comunicamosmas.api.service;
 

import com.comunicamosmas.api.service.dto.FacturaElectronicaResponseDTO;

public interface IApiRestService {
	
	public void pagosSupergiros();

	public FacturaElectronicaResponseDTO unSerializablePHP(String factura , Integer mesServicio , Long idEmpresa);

}
