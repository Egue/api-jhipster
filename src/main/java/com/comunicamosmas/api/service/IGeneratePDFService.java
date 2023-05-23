package com.comunicamosmas.api.service;
 
 
import java.util.List;

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;

public interface IGeneratePDFService {
	
	public RespuestaGeneracionPDFFactura generateFacturaPDF(EmailCampaignDetalleDTO detalle , List<DeudasForFacturaDTO> deudas , EmailCampaign campaña);

}
