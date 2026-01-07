package com.comunicamosmas.api.service.dto;

import java.time.LocalTime;

/**
 * DTO para información de la ventana de ejecución del proceso automático.
 * 
 * Contiene información sobre:
 * - Hora actual del sistema
 * - Límites de la ventana de ejecución (01:00 - 05:00)
 * - Estado actual (dentro/fuera de ventana)
 * - Tiempo restante hasta el fin de la ventana
 * 
 * Utilizado por InvoiceSchedulerService para gestionar
 * la ventana de tiempo del proceso automático.
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public class ExecutionWindowInfo {
    
    /** Hora actual del sistema */
    private LocalTime currentTime;
    
    /** Hora de inicio de la ventana (01:00 AM) */
    private LocalTime windowStart;
    
    /** Hora de fin de la ventana (05:00 AM) */
    private LocalTime windowEnd;
    
    /** Indica si estamos dentro de la ventana de ejecución */
    private boolean withinWindow;
    
    /** Minutos restantes hasta el fin de la ventana */
    private long remainingMinutes;

    // Constructores
    
    /**
     * Constructor por defecto
     */
    public ExecutionWindowInfo() {
    }

    /**
     * Constructor con todos los campos
     */
    public ExecutionWindowInfo(LocalTime currentTime, LocalTime windowStart, 
                               LocalTime windowEnd, boolean withinWindow, 
                               long remainingMinutes) {
        this.currentTime = currentTime;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.withinWindow = withinWindow;
        this.remainingMinutes = remainingMinutes;
    }

    // Getters y Setters

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public LocalTime getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(LocalTime windowStart) {
        this.windowStart = windowStart;
    }

    public LocalTime getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(LocalTime windowEnd) {
        this.windowEnd = windowEnd;
    }

    public boolean isWithinWindow() {
        return withinWindow;
    }

    public void setWithinWindow(boolean withinWindow) {
        this.withinWindow = withinWindow;
    }

    public long getRemainingMinutes() {
        return remainingMinutes;
    }

    public void setRemainingMinutes(long remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }

    @Override
    public String toString() {
        return "ExecutionWindowInfo{" +
                "currentTime=" + currentTime +
                ", windowStart=" + windowStart +
                ", windowEnd=" + windowEnd +
                ", withinWindow=" + withinWindow +
                ", remainingMinutes=" + remainingMinutes +
                '}';
    }
}
