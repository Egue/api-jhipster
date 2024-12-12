package com.comunicamosmas.api.service;
 

import java.util.List;
 
import org.springframework.data.domain.Pageable;

import com.comunicamosmas.api.domainMongo.FacturasEmitidas;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.FacturasControlmasDTO;
import com.comunicamosmas.api.service.dto.InfoFacturaDTO;

public interface IFacturasServices {
    
    public FacturasControlmasDTO.FacturasPendientes findFacturaByMouth(Long idCliente); 

    public List<EmailCampaignDetalleDTO> findListFacturaByIdCliente(Long idCliente , Pageable page);

    public void sendMailFactura(FacturasControlmasDTO.FacturaSendMail facturaSendMail);

    public InfoFacturaDTO informationInFactura(FacturasEmitidas factura);
}
