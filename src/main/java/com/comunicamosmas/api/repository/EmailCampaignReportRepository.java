package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.EmailCampaignReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad EmailCampaignReport.
 * 
 * Proporciona operaciones CRUD y queries personalizadas para:
 * - Consultar historial de reportes por campaña
 * - Buscar reportes recientes
 * - Eliminar reportes antiguos (para limpieza de datos)
 * - Análisis de rendimiento histórico
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Repository
public interface EmailCampaignReportRepository extends JpaRepository<EmailCampaignReport, Long> {

    /**
     * Busca todos los reportes de una campaña específica
     * ordenados por fecha de inicio descendente (más recientes primero)
     * 
     * @param campaignId ID de la campaña
     * @return Lista de reportes ordenados por fecha
     */
    List<EmailCampaignReport> findByCampaignIdOrderByStartTimeDesc(Long campaignId);

    /**
     * Busca reportes de una campaña en un rango de fechas
     * Útil para análisis de tendencias y reportes periódicos
     * 
     * @param campaignId ID de la campaña
     * @param fromDate Fecha desde (inclusive)
     * @param toDate Fecha hasta (inclusive)
     * @return Lista de reportes en el rango especificado
     */
    @Query("SELECT r FROM EmailCampaignReport r WHERE r.campaignId = :campaignId " +
           "AND r.startTime >= :fromDate AND r.startTime <= :toDate " +
           "ORDER BY r.startTime DESC")
    List<EmailCampaignReport> findByCampaignIdAndDateRange(
        @Param("campaignId") Long campaignId,
        @Param("fromDate") LocalDateTime fromDate,
        @Param("toDate") LocalDateTime toDate
    );

    /**
     * Busca reportes recientes (desde una fecha de corte hacia adelante)
     * Usado por procesos de limpieza para identificar reportes antiguos
     * 
     * @param cutoffDate Fecha de corte
     * @return Lista de reportes desde la fecha de corte
     */
    @Query("SELECT r FROM EmailCampaignReport r WHERE r.startTime >= :cutoffDate " +
           "ORDER BY r.startTime DESC")
    List<EmailCampaignReport> findRecentReports(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Busca reportes antiguos (antes de una fecha de corte)
     * Usado para limpieza de datos históricos
     * 
     * @param cutoffDate Fecha de corte
     * @return Lista de reportes antiguos
     */
    @Query("SELECT r FROM EmailCampaignReport r WHERE r.startTime < :cutoffDate")
    List<EmailCampaignReport> findOldReports(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Elimina reportes antiguos basados en fecha de corte
     * Útil para mantenimiento y limpieza de datos históricos
     * 
     * @param cutoffDate Fecha de corte (se eliminan reportes anteriores a esta fecha)
     * @return Cantidad de reportes eliminados
     */
    @Query("DELETE FROM EmailCampaignReport r WHERE r.startTime < :cutoffDate")
    int deleteOldReports(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Cuenta la cantidad de reportes de una campaña
     * 
     * @param campaignId ID de la campaña
     * @return Cantidad de reportes
     */
    long countByCampaignId(Long campaignId);

    /**
     * Busca el último reporte de una campaña específica
     * 
     * @param campaignId ID de la campaña
     * @return Último reporte o null si no existe
     */
    @Query("SELECT r FROM EmailCampaignReport r WHERE r.campaignId = :campaignId " +
           "ORDER BY r.startTime DESC")
    List<EmailCampaignReport> findTopByCampaignIdOrderByStartTimeDesc(@Param("campaignId") Long campaignId);

    /**
     * Busca reportes con errores (failedCount > 0)
     * Útil para identificar campañas problemáticas
     * 
     * @return Lista de reportes con errores
     */
    @Query("SELECT r FROM EmailCampaignReport r WHERE r.failedCount > 0 " +
           "ORDER BY r.startTime DESC")
    List<EmailCampaignReport> findReportsWithErrors();

    /**
     * Calcula estadísticas agregadas de una campaña
     * Retorna: total procesados, total exitosos, total fallidos
     * 
     * @param campaignId ID de la campaña
     * @return Array con [totalProcessed, successCount, failedCount]
     */
    @Query("SELECT SUM(r.totalProcessed), SUM(r.successCount), SUM(r.failedCount) " +
           "FROM EmailCampaignReport r WHERE r.campaignId = :campaignId")
    Object[] getAggregatedStatsByCampaign(@Param("campaignId") Long campaignId);

    /**
     * Busca reportes completamente exitosos (failedCount = 0)
     * 
     * @param campaignId ID de la campaña
     * @return Lista de reportes sin errores
     */
    @Query("SELECT r FROM EmailCampaignReport r WHERE r.campaignId = :campaignId " +
           "AND (r.failedCount = 0 OR r.failedCount IS NULL) " +
           "ORDER BY r.startTime DESC")
    List<EmailCampaignReport> findSuccessfulReportsByCampaign(@Param("campaignId") Long campaignId);
}
