package com.comunicamosmas.api.service.impl;

import com.comunicamosmas.api.domain.EmailCampaignReport;
import com.comunicamosmas.api.repository.EmailCampaignReportRepository;
import com.comunicamosmas.api.service.IEmailCampaignReportService;
import com.comunicamosmas.api.service.dto.EmailCampaignReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de reportes de campañas de email.
 * 
 * Responsabilidades:
 * - Conversión entre entidades y DTOs
 * - Lógica de negocio para reportes
 * - Gestión transaccional de operaciones
 * - Logging de operaciones importantes
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Service
@Transactional
public class EmailCampaignReportServiceImpl implements IEmailCampaignReportService {

    private static final Logger log = LoggerFactory.getLogger(EmailCampaignReportServiceImpl.class);

    private final EmailCampaignReportRepository reportRepository;

    /**
     * Constructor con inyección de dependencias
     * 
     * @param reportRepository Repositorio de reportes
     */
    public EmailCampaignReportServiceImpl(EmailCampaignReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailCampaignReportDTO saveReport(EmailCampaignReportDTO reportDTO) {
        log.info("Guardando reporte para campaña ID: {}", reportDTO.getCampaignId());
        
        // Convertir DTO a entidad
        EmailCampaignReport entity = toEntity(reportDTO);
        
        // Guardar en base de datos
        EmailCampaignReport savedEntity = reportRepository.save(entity);
        
        log.info("Reporte guardado con ID: {} - Procesados: {}, Exitosos: {}, Fallidos: {}", 
            savedEntity.getId(), 
            savedEntity.getTotalProcessed(),
            savedEntity.getSuccessCount(),
            savedEntity.getFailedCount());
        
        // Convertir entidad guardada a DTO
        return toDTO(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailCampaignReportDTO> findReportsByCampaign(Long campaignId) {
        log.debug("Buscando reportes para campaña ID: {}", campaignId);
        
        List<EmailCampaignReport> reports = reportRepository.findByCampaignIdOrderByStartTimeDesc(campaignId);
        
        log.debug("Se encontraron {} reportes para campaña {}", reports.size(), campaignId);
        
        return reports.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailCampaignReportDTO> findReportsByCampaignAndDays(Long campaignId, int days) {
        log.debug("Buscando reportes de campaña {} de últimos {} días", campaignId, days);
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        LocalDateTime now = LocalDateTime.now();
        
        List<EmailCampaignReport> reports = reportRepository.findByCampaignIdAndDateRange(
            campaignId, cutoffDate, now
        );
        
        log.debug("Se encontraron {} reportes en el rango especificado", reports.size());
        
        return reports.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmailCampaignReportDTO findLastReportByCampaign(Long campaignId) {
        log.debug("Buscando último reporte de campaña ID: {}", campaignId);
        
        List<EmailCampaignReport> reports = reportRepository
            .findTopByCampaignIdOrderByStartTimeDesc(campaignId);
        
        if (reports.isEmpty()) {
            log.debug("No se encontró ningún reporte para campaña {}", campaignId);
            return null;
        }
        
        EmailCampaignReport lastReport = reports.get(0);
        log.debug("Último reporte encontrado con ID: {}", lastReport.getId());
        
        return toDTO(lastReport);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteOldReports(int days) {
        log.info("Eliminando reportes más antiguos que {} días", days);
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        
        // Primero obtener los reportes a eliminar
        List<EmailCampaignReport> oldReports = reportRepository.findOldReports(cutoffDate);
        int count = oldReports.size();
        
        if (count > 0) {
            // Eliminar usando el repositorio estándar
            reportRepository.deleteAll(oldReports);
            log.info("Se eliminaron {} reportes antiguos", count);
        } else {
            log.info("No se encontraron reportes antiguos para eliminar");
        }
        
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailCampaignReportDTO> findRecentReports(int days) {
        log.debug("Buscando reportes de últimos {} días", days);
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        List<EmailCampaignReport> reports = reportRepository.findRecentReports(cutoffDate);
        
        log.debug("Se encontraron {} reportes recientes", reports.size());
        
        return reports.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailCampaignReportDTO> findReportsWithErrors() {
        log.debug("Buscando reportes con errores");
        
        List<EmailCampaignReport> reports = reportRepository.findReportsWithErrors();
        
        log.debug("Se encontraron {} reportes con errores", reports.size());
        
        return reports.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Long[] getAggregatedStatsByCampaign(Long campaignId) {
        log.debug("Obteniendo estadísticas agregadas de campaña ID: {}", campaignId);
        
        Object[] stats = reportRepository.getAggregatedStatsByCampaign(campaignId);
        
        if (stats == null) {
            return new Long[]{0L, 0L, 0L};
        }
        
        Long totalProcessed = stats[0] != null ? ((Number) stats[0]).longValue() : 0L;
        Long successCount = stats[1] != null ? ((Number) stats[1]).longValue() : 0L;
        Long failedCount = stats[2] != null ? ((Number) stats[2]).longValue() : 0L;
        
        log.debug("Estadísticas: Total={}, Exitosos={}, Fallidos={}", 
            totalProcessed, successCount, failedCount);
        
        return new Long[]{totalProcessed, successCount, failedCount};
    }

    // Métodos privados de conversión

    /**
     * Convierte una entidad EmailCampaignReport a DTO
     * 
     * @param entity Entidad a convertir
     * @return DTO convertido
     */
    private EmailCampaignReportDTO toDTO(EmailCampaignReport entity) {
        if (entity == null) {
            return null;
        }
        
        EmailCampaignReportDTO dto = new EmailCampaignReportDTO();
        dto.setId(entity.getId());
        dto.setCampaignId(entity.getCampaignId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setTotalProcessed(entity.getTotalProcessed());
        dto.setSuccessCount(entity.getSuccessCount());
        dto.setFailedCount(entity.getFailedCount());
        dto.setDurationSeconds(entity.getDurationSeconds());
        dto.setErrorDetails(entity.getErrorDetails());
        dto.setCreatedDate(entity.getCreatedDate());
        
        return dto;
    }

    /**
     * Convierte un DTO EmailCampaignReportDTO a entidad
     * 
     * @param dto DTO a convertir
     * @return Entidad convertida
     */
    private EmailCampaignReport toEntity(EmailCampaignReportDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmailCampaignReport entity = new EmailCampaignReport();
        entity.setId(dto.getId());
        entity.setCampaignId(dto.getCampaignId());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setTotalProcessed(dto.getTotalProcessed());
        entity.setSuccessCount(dto.getSuccessCount());
        entity.setFailedCount(dto.getFailedCount());
        entity.setDurationSeconds(dto.getDurationSeconds());
        entity.setErrorDetails(dto.getErrorDetails());
        
        if (dto.getCreatedDate() != null) {
            entity.setCreatedDate(dto.getCreatedDate());
        }
        
        return entity;
    }
}
