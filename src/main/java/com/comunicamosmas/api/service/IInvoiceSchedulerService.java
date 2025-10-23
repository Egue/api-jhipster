package com.comunicamosmas.api.service;

import com.comunicamosmas.api.service.dto.ExecutionWindowInfo;

/**
 * Servicio para gestionar la ventana de tiempo de ejecución del proceso automático.
 * 
 * Responsabilidades:
 * - Verificar si estamos dentro de la ventana de ejecución (01:00 - 05:00)
 * - Calcular tiempo restante hasta el fin de la ventana
 * - Proporcionar información completa del estado de la ventana
 * 
 * Este servicio NO contiene lógica de negocio de procesamiento,
 * solo gestiona el aspecto temporal de la ejecución.
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public interface IInvoiceSchedulerService {

    /**
     * Verifica si la hora actual está dentro de la ventana de ejecución.
     * 
     * Ventana de ejecución: 01:00 AM - 05:00 AM
     * 
     * @return true si estamos dentro de la ventana, false en caso contrario
     */
    boolean isWithinExecutionWindow();

    /**
     * Calcula los minutos restantes hasta el fin de la ventana de ejecución.
     * 
     * Si estamos fuera de la ventana, retorna 0.
     * Si estamos dentro, retorna los minutos hasta las 05:00 AM.
     * 
     * @return Minutos restantes hasta el fin de la ventana
     */
    long getRemainingMinutes();

    /**
     * Obtiene información completa sobre el estado de la ventana de ejecución.
     * 
     * Incluye:
     * - Hora actual
     * - Límites de la ventana
     * - Si estamos dentro o fuera
     * - Minutos restantes
     * 
     * @return Objeto con toda la información de la ventana
     */
    ExecutionWindowInfo getWindowInfo();
}
