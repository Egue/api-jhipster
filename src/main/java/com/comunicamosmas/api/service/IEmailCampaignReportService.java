package com.comunicamosmas.api.service;

import com.comunicamosmas.api.service.dto.EmailCampaignReportDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz de servicio para gestión de reportes de campañas de email.
 * 
 * Proporciona operaciones para:
 * - Guardar reportes de ejecución
 * - Consultar historial de reportes
 * - Obtener estadísticas agregadas
 * - Limpieza de reportes antiguos
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public interface IEmailCampaignReportService {

    /**
     * Guarda un nuevo reporte de procesamiento
     * 
     * @param reportDTO Datos del reporte a guardar
     * @return Reporte guardado con ID generado
     */
    EmailCampaignReportDTO saveReport(EmailCampaignReportDTO reportDTO);

    /**
     * Busca todos los reportes de una campaña específica
     * 
     * @param campaignId ID de la campaña
     * @return Lista de reportes ordenados por fecha descendente
     */
    List<EmailCampaignReportDTO> findReportsByCampaign(Long campaignId);

    /**
     * Busca reportes de una campaña en un rango de días
     * 
     * @param campaignId ID de la campaña
     * @param days Cantidad de días hacia atrás desde hoy
     * @return Lista de reportes en el rango especificado
     */
    List<EmailCampaignReportDTO> findReportsByCampaignAndDays(Long campaignId, int days);

    /**
     * Busca el último reporte de una campaña
     * 
     * @param campaignId ID de la campaña
     * @return Último reporte o null si no existe
     */
    EmailCampaignReportDTO findLastReportByCampaign(Long campaignId);

    /**
     * Elimina reportes más antiguos que la cantidad de días especificada
     * Utilizado para limpieza de datos históricos
     * 
     * @param days Cantidad de días de retención
     * @return Cantidad de reportes eliminados
     */
    int deleteOldReports(int days);

    /**
     * Busca reportes recientes (últimos N días) de todas las campañas
     * 
     * @param days Cantidad de días hacia atrás
     * @return Lista de reportes recientes
     */
    List<EmailCampaignReportDTO> findRecentReports(int days);

    /**
     * Busca reportes que tuvieron errores
     * 
     * @return Lista de reportes con al menos un error
     */
    List<EmailCampaignReportDTO> findReportsWithErrors();

    /**
     * Obtiene estadísticas agregadas de una campaña
     * Suma total de procesados, exitosos y fallidos de todos los reportes
     * 
     * @param campaignId ID de la campaña
     * @return Array con [totalProcessed, successCount, failedCount]
     */
    Long[] getAggregatedStatsByCampaign(Long campaignId);
}
