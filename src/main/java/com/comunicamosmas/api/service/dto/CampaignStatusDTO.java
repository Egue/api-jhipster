package com.comunicamosmas.api.service.dto;

/**
 * DTO para representar el estado actual de procesamiento de una campaña.
 * Incluye contadores de emails en diferentes estados y cálculo de progreso.
 * 
 * Utilizado por:
 * - Endpoint de status para consultar el estado actual
 * - Airflow para verificar el progreso de una campaña
 * - Dashboard de monitoreo
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public class CampaignStatusDTO {
    
    /** ID de la campaña */
    private Long campaignId;
    
    /** Nombre de la campaña */
    private String campaignName;
    
    /** Estado de la campaña (Activo, PortalWeb, Finalizado, etc.) */
    private String estado;
    
    /** Total de detalles/emails en la campaña */
    private Long total;
    
    /** Cantidad de emails pendientes de envío */
    private Long pendientes;
    
    /** Cantidad de emails enviados exitosamente */
    private Long enviados;
    
    /** Cantidad de emails con errores */
    private Long errores;
    
    /** Porcentaje de progreso (0-100) */
    private Double progreso;

    // Constructores
    
    /**
     * Constructor por defecto
     */
    public CampaignStatusDTO() {
    }

    /**
     * Constructor con todos los campos
     */
    public CampaignStatusDTO(Long campaignId, String campaignName, String estado, 
                           Long total, Long pendientes, Long enviados, Long errores) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.estado = estado;
        this.total = total;
        this.pendientes = pendientes;
        this.enviados = enviados;
        this.errores = errores;
        this.progreso = calculateProgress();
    }

    // Getters y Setters
    
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        this.progreso = calculateProgress(); // Recalcular al cambiar total
    }

    public Long getPendientes() {
        return pendientes;
    }

    public void setPendientes(Long pendientes) {
        this.pendientes = pendientes;
    }

    public Long getEnviados() {
        return enviados;
    }

    public void setEnviados(Long enviados) {
        this.enviados = enviados;
        this.progreso = calculateProgress(); // Recalcular al cambiar enviados
    }

    public Long getErrores() {
        return errores;
    }

    public void setErrores(Long errores) {
        this.errores = errores;
    }

    public Double getProgreso() {
        return progreso;
    }

    public void setProgreso(Double progreso) {
        this.progreso = progreso;
    }

    // Métodos helper
    
    /**
     * Calcula el porcentaje de progreso basado en emails enviados vs total
     * @return Porcentaje de progreso (0-100)
     */
    private Double calculateProgress() {
        if (total == null || total == 0) {
            return 0.0;
        }
        if (enviados == null) {
            return 0.0;
        }
        return Math.round((enviados * 100.0 / total) * 100.0) / 100.0; // 2 decimales
    }

    /**
     * Verifica si la campaña está completamente procesada
     * @return true si todos los emails fueron procesados (enviados o con error)
     */
    public boolean isCompleted() {
        return pendientes != null && pendientes == 0;
    }

    /**
     * Verifica si hay emails con errores
     * @return true si al menos un email tuvo error
     */
    public boolean hasErrors() {
        return errores != null && errores > 0;
    }

    /**
     * Calcula la tasa de éxito excluyendo pendientes
     * @return Porcentaje de éxito sobre emails procesados (0-100)
     */
    public Double getSuccessRate() {
        long procesados = (enviados != null ? enviados : 0) + (errores != null ? errores : 0);
        if (procesados == 0) {
            return 0.0;
        }
        return Math.round(((enviados != null ? enviados : 0) * 100.0 / procesados) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "CampaignStatusDTO{" +
                "campaignId=" + campaignId +
                ", campaignName='" + campaignName + '\'' +
                ", estado='" + estado + '\'' +
                ", total=" + total +
                ", pendientes=" + pendientes +
                ", enviados=" + enviados +
                ", errores=" + errores +
                ", progreso=" + progreso + "%" +
                ", completed=" + isCompleted() +
                '}';
    }
}
