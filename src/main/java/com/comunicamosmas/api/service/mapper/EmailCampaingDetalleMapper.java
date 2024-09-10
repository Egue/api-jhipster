package com.comunicamosmas.api.service.mapper;

import org.mapstruct.*;

import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;

@Mapper(componentModel = "spring", uses = {})
public interface EmailCampaingDetalleMapper extends EntityMapper<EmailCampaignDetalleDTO , EmailCampaignDetalle>{}
