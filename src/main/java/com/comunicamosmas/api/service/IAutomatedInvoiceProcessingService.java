package com.comunicamosmas.api.service;

import com.comunicamosmas.api.service.dto.EmailCampaignProcessResultDTO;

/**
 * Servicio principal para el procesamiento automático de facturas.
 * 
 * Responsabilidades:
 * - Orquestar el proceso completo de envío automático
 * - Descubrir campañas abiertas automáticamente
 * - Procesar facturas con delays obligatorios de 10 segundos
 * - Respetar ventana de tiempo (01:00 - 05:00)
 * - Guardar progreso y reportes en base de datos
 * 
 * Este es el servicio de más alto nivel que coordina
 * todos los demás servicios para completar el proceso automático.
 * 
 * IMPORTANTE:
 * - Delay de 10 segundos es OBLIGATORIO (no configurable)
 * - Proceso se detiene automáticamente a las 05:00 AM
 * - No requiere parámetros externos (completamente autónomo)
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
public interface IAutomatedInvoiceProcessingService {

    /**
     * Procesa facturas automáticamente dentro de la ventana de tiempo.
     * 
     * Este método:
     * 1. Verifica que estamos dentro de la ventana de ejecución
     * 2. Busca campañas en estado "Abierto"
     * 3. Por cada campaña, obtiene facturas pendientes
     * 4. Envía facturas una por una con delay de 10 segundos
     * 5. Guarda progreso en BD después de cada factura
     * 6. Se detiene automáticamente a las 05:00 AM
     * 7. Guarda reporte final en BD
     * 
     * Ventana de ejecución: 01:00 AM - 05:00 AM
     * Delay obligatorio: 10 segundos por factura
     * 
     * @return Resultado completo del procesamiento con estadísticas
     * @throws IllegalStateException Si se invoca fuera de la ventana de ejecución
     */
    EmailCampaignProcessResultDTO processInvoicesAutomatically();
}
