package com.comunicamosmas.api.service.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO para almacenar el resultado del procesamiento batch de una campaña de email.
 * Contiene estadísticas de envío, tiempos de ejecución y detalles de errores.
 * 
 * Este DTO es utilizado por:
 * - EmailCampaignAirflowController para retornar resultados a Airflow
 * - Servicios internos para trackear el progreso de procesamiento
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public class EmailCampaignProcessResultDTO {
    
    /** ID de la campaña procesada */
    private Long campaignId;
    
    /** Fecha y hora de inicio del procesamiento */
    private LocalDateTime startTime;
    
    /** Fecha y hora de finalización del procesamiento */
    private LocalDateTime endTime;
    
    /** Total de emails procesados (exitosos + fallidos) */
    private int totalProcessed = 0;
    
    /** Cantidad de emails enviados exitosamente */
    private int successCount = 0;
    
    /** Cantidad de emails que fallaron en el envío */
    private int failedCount = 0;
    
    /** Duración total del procesamiento en segundos */
    private double durationSeconds = 0.0;
    
    /** Mapa de errores: email -> mensaje de error */
    private Map<String, String> errors = new HashMap<>();
    
    /** Error general si el proceso completo falló */
    private String generalError;

    // Constructores
    
    /**
     * Constructor por defecto
     */
    public EmailCampaignProcessResultDTO() {
    }

    /**
     * Constructor con ID de campaña
     * @param campaignId ID de la campaña a procesar
     */
    public EmailCampaignProcessResultDTO(Long campaignId) {
        this.campaignId = campaignId;
        this.startTime = LocalDateTime.now();
    }

    // Getters y Setters
    
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
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

    public int getTotalProcessed() {
        return totalProcessed;
    }

    public void setTotalProcessed(int totalProcessed) {
        this.totalProcessed = totalProcessed;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public double getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(double durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getGeneralError() {
        return generalError;
    }

    public void setGeneralError(String generalError) {
        this.generalError = generalError;
    }

    // Métodos helper
    
    /**
     * Incrementa el contador de emails procesados
     */
    public void incrementProcessed() {
        this.totalProcessed++;
    }

    /**
     * Incrementa el contador de emails enviados exitosamente
     */
    public void incrementSuccess() {
        this.successCount++;
    }

    /**
     * Incrementa el contador de emails fallidos
     */
    public void incrementFailed() {
        this.failedCount++;
    }

    /**
     * Agrega un error al mapa de errores
     * @param email Email que falló
     * @param errorMsg Mensaje de error
     */
    public void addError(String email, String errorMsg) {
        this.errors.put(email, errorMsg);
    }

    /**
     * Calcula y retorna la tasa de éxito en porcentaje
     * @return Porcentaje de emails enviados exitosamente (0-100)
     */
    public double getSuccessRate() {
        if (totalProcessed == 0) {
            return 0.0;
        }
        return (successCount * 100.0) / totalProcessed;
    }

    @Override
    public String toString() {
        return "EmailCampaignProcessResultDTO{" +
                "campaignId=" + campaignId +
                ", totalProcessed=" + totalProcessed +
                ", successCount=" + successCount +
                ", failedCount=" + failedCount +
                ", successRate=" + String.format("%.2f", getSuccessRate()) + "%" +
                ", durationSeconds=" + durationSeconds +
                '}';
    }
}
