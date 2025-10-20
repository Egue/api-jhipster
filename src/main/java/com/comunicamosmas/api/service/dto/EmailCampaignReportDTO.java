package com.comunicamosmas.api.service.dto;

import java.time.LocalDateTime;

/**
 * DTO para reportes persistidos en la base de datos.
 * Guarda el historial completo de ejecuciones de procesamiento batch.
 * 
 * Este DTO se utiliza para:
 * - Guardar reportes de ejecución en la tabla email_campaign_report
 * - Consultar historial de ejecuciones previas
 * - Análisis y auditoría de envíos
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public class EmailCampaignReportDTO {
    
    /** ID único del reporte */
    private Long id;
    
    /** ID de la campaña asociada */
    private Long campaignId;
    
    /** Nombre de la campaña (desnormalizado para consultas rápidas) */
    private String campaignName;
    
    /** Fecha y hora de inicio del procesamiento */
    private LocalDateTime startTime;
    
    /** Fecha y hora de finalización del procesamiento */
    private LocalDateTime endTime;
    
    /** Total de emails procesados */
    private Integer totalProcessed;
    
    /** Cantidad de emails enviados exitosamente */
    private Integer successCount;
    
    /** Cantidad de emails fallidos */
    private Integer failedCount;
    
    /** Duración en segundos */
    private Double durationSeconds;
    
    /** Detalles de errores en formato JSON o texto */
    private String errorDetails;
    
    /** Fecha de creación del registro */
    private LocalDateTime createdDate;

    // Constructores
    
    /**
     * Constructor por defecto
     */
    public EmailCampaignReportDTO() {
    }

    /**
     * Constructor desde EmailCampaignProcessResultDTO
     * Convierte el resultado de procesamiento en un reporte persistible
     * 
     * @param result Resultado del procesamiento
     */
    public EmailCampaignReportDTO(EmailCampaignProcessResultDTO result) {
        this.campaignId = result.getCampaignId();
        this.startTime = result.getStartTime();
        this.endTime = result.getEndTime();
        this.totalProcessed = result.getTotalProcessed();
        this.successCount = result.getSuccessCount();
        this.failedCount = result.getFailedCount();
        this.durationSeconds = result.getDurationSeconds();
        
        // Serializar errores a JSON simple
        if (result.getErrors() != null && !result.getErrors().isEmpty()) {
            StringBuilder errors = new StringBuilder();
            result.getErrors().forEach((email, error) -> 
                errors.append(email).append(": ").append(error).append("\n")
            );
            this.errorDetails = errors.toString();
        }
        
        if (result.getGeneralError() != null) {
            this.errorDetails = (this.errorDetails != null ? this.errorDetails + "\n" : "") 
                + "ERROR GENERAL: " + result.getGeneralError();
        }
        
        this.createdDate = LocalDateTime.now();
    }

    // Getters y Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalProcessed() {
        return totalProcessed;
    }

    public void setTotalProcessed(Integer totalProcessed) {
        this.totalProcessed = totalProcessed;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public Double getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Double durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // Métodos helper
    
    /**
     * Calcula la tasa de éxito en porcentaje
     * @return Porcentaje de éxito (0-100)
     */
    public double getSuccessRate() {
        if (totalProcessed == null || totalProcessed == 0) {
            return 0.0;
        }
        return (successCount * 100.0) / totalProcessed;
    }

    /**
     * Verifica si hubo errores en el procesamiento
     * @return true si hubo al menos un error
     */
    public boolean hasErrors() {
        return failedCount != null && failedCount > 0;
    }

    @Override
    public String toString() {
        return "EmailCampaignReportDTO{" +
                "id=" + id +
                ", campaignId=" + campaignId +
                ", campaignName='" + campaignName + '\'' +
                ", totalProcessed=" + totalProcessed +
                ", successCount=" + successCount +
                ", failedCount=" + failedCount +
                ", successRate=" + String.format("%.2f", getSuccessRate()) + "%" +
                ", createdDate=" + createdDate +
                '}';
    }
}
