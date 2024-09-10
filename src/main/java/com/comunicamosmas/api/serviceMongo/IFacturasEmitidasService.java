package com.comunicamosmas.api.serviceMongo;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domainMongo.FacturasEmitidas;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;

public interface IFacturasEmitidasService {
    
    public void save(FacturasEmitidas emitidas);

    public void saveAll(List<FacturasEmitidas> emitidas);

    public List<FacturasEmitidas> findByidCampaign(Integer idCampaign);

    public List<FacturasEmitidas> findByIdCliente(Integer idCliente , Pageable page);

    public void sendFactura(EmailCampaignDetalleDTO detalle , EmailCampaign campaign);
}
