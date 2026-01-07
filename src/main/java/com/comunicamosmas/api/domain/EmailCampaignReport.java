package com.comunicamosmas.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entidad JPA para almacenar reportes de ejecución de campañas de email.
 * Cada registro representa una ejecución completa de procesamiento batch.
 * 
 * Tabla: email_campaign_report
 * 
 * Propósito:
 * - Mantener historial de todas las ejecuciones de procesamiento
 * - Auditoría y trazabilidad de envíos
 * - Análisis de rendimiento y tasas de éxito
 * - Debugging de problemas en envíos masivos
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Entity
@Table(name = "email_campaign_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailCampaignReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID único del reporte (generado automáticamente)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID de la campaña procesada (Foreign Key a email_campaign)
     */
    @Column(name = "campaign_id", nullable = false)
    private Long campaignId;

    /**
     * Fecha y hora de inicio del procesamiento
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * Fecha y hora de finalización del procesamiento
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * Total de emails procesados (exitosos + fallidos)
     */
    @Column(name = "total_processed")
    private Integer totalProcessed;

    /**
     * Cantidad de emails enviados exitosamente
     */
    @Column(name = "success_count")
    private Integer successCount;

    /**
     * Cantidad de emails que fallaron en el envío
     */
    @Column(name = "failed_count")
    private Integer failedCount;

    /**
     * Duración total del procesamiento en segundos
     */
    @Column(name = "duration_seconds")
    private Double durationSeconds;

    /**
     * Detalles de errores en formato texto o JSON
     * Almacena información detallada de cada error ocurrido
     */
    @Column(name = "error_details", columnDefinition = "LONGTEXT")
    private String errorDetails;

    /**
     * Fecha de creación del registro (timestamp automático)
     */
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    /**
     * Hora de inicio de la ventana de ejecución (01:00 AM)
     * Para procesos automáticos que se ejecutan en ventana de tiempo
     */
    @Column(name = "execution_window_start")
    private LocalDateTime executionWindowStart;

    /**
     * Hora de fin de la ventana de ejecución (05:00 AM o cuando terminó)
     * Para procesos automáticos que se ejecutan en ventana de tiempo
     */
    @Column(name = "execution_window_end")
    private LocalDateTime executionWindowEnd;

    /**
     * Indica si el proceso fue detenido por el scheduler a las 05:00 AM
     * true = detenido por límite de tiempo
     * false = terminó naturalmente
     */
    @Column(name = "stopped_by_scheduler")
    private Boolean stoppedByScheduler;

    /**
     * Cantidad de campañas procesadas en esta ejecución
     * Para ejecuciones que procesan múltiples campañas
     */
    @Column(name = "campaigns_processed")
    private Integer campaignsProcessed;

    // Constructores

    /**
     * Constructor por defecto requerido por JPA
     */
    public EmailCampaignReport() {
        this.createdDate = LocalDateTime.now();
    }

    /**
     * Constructor con campaignId
     * @param campaignId ID de la campaña
     */
    public EmailCampaignReport(Long campaignId) {
        this.campaignId = campaignId;
        this.startTime = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }

    // Lifecycle callbacks

    /**
     * Callback antes de persistir la entidad
     * Asegura que createdDate esté establecido
     */
    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
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

    public LocalDateTime getExecutionWindowStart() {
        return executionWindowStart;
    }

    public void setExecutionWindowStart(LocalDateTime executionWindowStart) {
        this.executionWindowStart = executionWindowStart;
    }

    public LocalDateTime getExecutionWindowEnd() {
        return executionWindowEnd;
    }

    public void setExecutionWindowEnd(LocalDateTime executionWindowEnd) {
        this.executionWindowEnd = executionWindowEnd;
    }

    public Boolean getStoppedByScheduler() {
        return stoppedByScheduler;
    }

    public void setStoppedByScheduler(Boolean stoppedByScheduler) {
        this.stoppedByScheduler = stoppedByScheduler;
    }

    public Integer getCampaignsProcessed() {
        return campaignsProcessed;
    }

    public void setCampaignsProcessed(Integer campaignsProcessed) {
        this.campaignsProcessed = campaignsProcessed;
    }

    // Métodos helper

    /**
     * Calcula la tasa de éxito en porcentaje
     * @return Porcentaje de emails enviados exitosamente
     */
    public double getSuccessRate() {
        if (totalProcessed == null || totalProcessed == 0) {
            return 0.0;
        }
        return (successCount * 100.0) / totalProcessed;
    }

    /**
     * Verifica si el procesamiento fue completamente exitoso
     * @return true si no hubo errores
     */
    public boolean isFullySuccessful() {
        return failedCount == null || failedCount == 0;
    }

    // equals, hashCode y toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailCampaignReport)) return false;
        EmailCampaignReport that = (EmailCampaignReport) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "EmailCampaignReport{" +
                "id=" + id +
                ", campaignId=" + campaignId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalProcessed=" + totalProcessed +
                ", successCount=" + successCount +
                ", failedCount=" + failedCount +
                ", durationSeconds=" + durationSeconds +
                ", createdDate=" + createdDate +
                '}';
    }
}
