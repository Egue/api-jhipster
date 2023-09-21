package com.comunicamosmas.api.service;

import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;

public interface IZipFileCreatorService {

    public String zipFileFactura(RespuestaGeneracionPDFFactura respuestaPDF , EmailCampaignDetalleDTO detalle);

    public String codificarBase64(String path);
    
}
