package com.comunicamosmas.api.service.mapper;

import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailCampaignApi} and its DTO {@link EmailCampaignApiDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailCampaignApiMapper extends EntityMapper<EmailCampaignApiDTO, EmailCampaignApi> {}
