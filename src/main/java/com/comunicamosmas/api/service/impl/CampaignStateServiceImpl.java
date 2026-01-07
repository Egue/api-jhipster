package com.comunicamosmas.api.service.impl;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.ICampaignStateService;
import com.comunicamosmas.api.service.IEmailCampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de estados de campañas.
 * 
 * Responsabilidades:
 * - Filtrar campañas por estado "Abierto"
 * - Validar elegibilidad de campañas
 * - Proporcionar lógica de negocio relacionada con estados
 * 
 * PATRÓN MVC:
 * - Usa IEmailCampaignService para obtener datos
 * - Aplica lógica de negocio (filtrado, validación)
 * - No accede directamente a DAOs
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Service
public class CampaignStateServiceImpl implements ICampaignStateService {

    private static final Logger log = LoggerFactory.getLogger(CampaignStateServiceImpl.class);

    private static final String ESTADO_ABIERTO = "Abierto";

    private final IEmailCampaignService emailCampaignService;

    /**
     * Constructor con inyección de dependencias
     * 
     * @param emailCampaignService Servicio de campañas
     */
    public CampaignStateServiceImpl(IEmailCampaignService emailCampaignService) {
        this.emailCampaignService = emailCampaignService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmailCampaign> findOpenCampaigns() {
        log.info("🔍 Buscando campañas con estado '{}'", ESTADO_ABIERTO);
        
        try {
            List<EmailCampaign> openCampaigns = emailCampaignService.findByEstado(ESTADO_ABIERTO);
            
            if (openCampaigns == null || openCampaigns.isEmpty()) {
                log.warn("⚠️ No se encontraron campañas en estado '{}'", ESTADO_ABIERTO);
                return List.of();
            }

            // Filtrar campañas elegibles
            List<EmailCampaign> elegibles = openCampaigns.stream()
                .filter(this::isCampaignEligible)
                .collect(Collectors.toList());

            log.info("✅ Se encontraron {} campañas en estado '{}' ({} elegibles)", 
                     openCampaigns.size(), ESTADO_ABIERTO, elegibles.size());
            
            // Log de campañas encontradas
            elegibles.forEach(campaign -> 
                log.info("   📧 Campaña #{} - {}", campaign.getId(), campaign.getNombre())
            );

            return elegibles;

        } catch (Exception e) {
            log.error("💥 Error al buscar campañas abiertas", e);
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCampaignEligible(EmailCampaign campaign) {
        if (campaign == null) {
            log.debug("❌ Campaña nula - No elegible");
            return false;
        }

        if (campaign.getId() == null) {
            log.debug("❌ Campaña sin ID - No elegible");
            return false;
        }

        if (campaign.getEstado() == null || !ESTADO_ABIERTO.equals(campaign.getEstado())) {
            log.debug("❌ Campaña #{} con estado '{}' - No elegible", 
                     campaign.getId(), campaign.getEstado());
            return false;
        }

        log.debug("✅ Campaña #{} - {} es elegible", campaign.getId(), campaign.getNombre());
        return true;
    }
}
