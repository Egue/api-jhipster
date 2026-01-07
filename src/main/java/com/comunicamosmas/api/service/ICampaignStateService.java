package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.EmailCampaign;

import java.util.List;

/**
 * Servicio para gestionar estados de campañas de email.
 * 
 * Responsabilidades:
 * - Buscar campañas por estado específico
 * - Validar elegibilidad de campañas para procesamiento
 * - Gestión de estados de campañas
 * 
 * Este servicio se enfoca exclusivamente en la gestión de estados,
 * no en el procesamiento de emails.
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public interface ICampaignStateService {

    /**
     * Busca todas las campañas con estado "Abierto".
     * 
     * Solo las campañas en estado "Abierto" son elegibles
     * para procesamiento automático.
     * 
     * Estados excluidos:
     * - PortalWeb
     * - Finalizado
     * - Inactivo
     * - Cualquier otro estado diferente a "Abierto"
     * 
     * @return Lista de campañas en estado "Abierto"
     */
    List<EmailCampaign> findOpenCampaigns();

    /**
     * Valida si una campaña es elegible para procesamiento.
     * 
     * Criterios de elegibilidad:
     * - Estado = "Abierto"
     * - Campaña no nula
     * - ID válido
     * 
     * @param campaign Campaña a validar
     * @return true si la campaña es elegible, false en caso contrario
     */
    boolean isCampaignEligible(EmailCampaign campaign);
}
