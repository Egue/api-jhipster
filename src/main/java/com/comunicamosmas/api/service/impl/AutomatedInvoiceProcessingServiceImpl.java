package com.comunicamosmas.api.service.impl;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.*;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignProcessResultDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio de procesamiento automático de facturas.
 * 
 * Este servicio es el núcleo del sistema automatizado de envío de facturas.
 * Orquesta todos los componentes necesarios para procesar facturas
 * de manera autónoma dentro de una ventana de tiempo.
 * 
 * RESPONSABILIDADES:
 * - Gestionar el ciclo completo de procesamiento
 * - Coordinar servicios de campañas, emails, scheduler y reportes
 * - Aplicar delays obligatorios de 10 segundos
 * - Respetar ventana de tiempo (01:00 - 05:00)
 * - Logging detallado de todo el proceso
 * - Persistencia continua en base de datos
 * 
 * PATRÓN MVC:
 * - Service Layer (Lógica de negocio)
 * - NO accede directamente a DAOs
 * - Usa otros servicios para operaciones específicas
 * - NO contiene código HTTP (eso es del Controller)
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Service
public class AutomatedInvoiceProcessingServiceImpl implements IAutomatedInvoiceProcessingService {

    private static final Logger log = LoggerFactory.getLogger(AutomatedInvoiceProcessingServiceImpl.class);

    /**
     * Delay OBLIGATORIO entre cada intento de envío de factura.
     * 10 segundos = 10,000 milisegundos
     * CONSTANTE - NO MODIFICAR
     */
    private static final long MANDATORY_DELAY_MS = 10_000L;

    /**
     * Límite de tiempo de ejecución: 4 horas en milisegundos
     * Si Airflow llama a las 01:00, terminará a las 05:00
     * CONSTANTE - NO MODIFICAR
     */
    private static final long MAX_EXECUTION_TIME_MS = 4L * 60L * 60L * 1000L; // 4 horas

    private final ICampaignStateService campaignStateService;
    private final IEmailCampaignDetalleService emailDetalleService;
    private final IEmailCampaignReportService reportService;

    /**
     * Constructor con inyección de dependencias
     */
    public AutomatedInvoiceProcessingServiceImpl(
            ICampaignStateService campaignStateService,
            IEmailCampaignDetalleService emailDetalleService,
            IEmailCampaignReportService reportService) {
        this.campaignStateService = campaignStateService;
        this.emailDetalleService = emailDetalleService;
        this.reportService = reportService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailCampaignProcessResultDTO processInvoicesAutomatically() {
        
        logStartBanner();

        LocalDateTime startTime = LocalDateTime.now();
        EmailCampaignProcessResultDTO result = new EmailCampaignProcessResultDTO();
        result.setExecutionWindowStart(startTime);
        result.setCampaignIds(new ArrayList<>());

        log.info("⏰ Inicio de procesamiento: {}", startTime);
        log.info("📌 Airflow invocó el endpoint - procesamiento iniciado");

        try {
            // Buscar campañas abiertas
            log.info("🔍 Buscando campañas con estado 'Abierto'...");
            List<EmailCampaign> openCampaigns = campaignStateService.findOpenCampaigns();

            if (openCampaigns.isEmpty()) {
                log.warn("╔═══════════════════════════════════════════════════════════╗");
                log.warn("║  ⚠️ NO HAY CAMPAÑAS DISPONIBLES                          ║");
                log.warn("╚═══════════════════════════════════════════════════════════╝");
                log.warn("📋 No se encontraron campañas en estado 'Abierto'");
                log.warn("💡 Posibles causas:");
                log.warn("   - Todas las campañas están en estado 'Finalizado'");
                log.warn("   - No hay campañas creadas para este período");
                log.warn("   - Campañas en estado 'PortalWeb' (se procesan por otro medio)");
                log.warn("⏹️ Finalizando sin procesar ninguna factura");
                
                finalizarProcesamiento(result, startTime, false);
                return result;
            }

            log.info("✅ Campañas encontradas: {}", openCampaigns.size());
            openCampaigns.forEach(c -> log.info("   → Campaña #{}: {}", c.getId(), c.getNombre()));

            // Procesar cada campaña
            int campaignsProcessed = 0;
            for (EmailCampaign campaign : openCampaigns) {
                
                // Verificar límite de tiempo ANTES de cada campaña (4 horas)
                long tiempoTranscurrido = System.currentTimeMillis() - 
                    startTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
                
                if (tiempoTranscurrido >= MAX_EXECUTION_TIME_MS) {
                    log.warn("╔═══════════════════════════════════════════════════════════╗");
                    log.warn("║  ⏰ LÍMITE DE TIEMPO ALCANZADO (4 HORAS)                 ║");
                    log.warn("╚═══════════════════════════════════════════════════════════╝");
                    log.warn("⏱️ Tiempo transcurrido: {} horas", tiempoTranscurrido / (1000 * 60 * 60));
                    log.warn("📊 Campañas procesadas: {}/{}", campaignsProcessed, openCampaigns.size());
                    log.warn("⏹️ Deteniendo procesamiento automáticamente");
                    result.setStoppedByScheduler(true);
                    break;
                }

                log.info("╔════════════════════════════════════════════════════════════╗");
                log.info("║  📋 CAMPAÑA #{} - {}                              ", 
                         campaign.getId(), 
                         String.format("%-30s", truncateString(campaign.getNombre(), 30)));
                log.info("╚════════════════════════════════════════════════════════════╝");

                processCampaign(campaign, result, startTime);
                
                campaignsProcessed++;
                result.getCampaignIds().add(campaign.getId().longValue());
            }

            result.setCampaignsProcessed(campaignsProcessed);
            finalizarProcesamiento(result, startTime, result.getStoppedByScheduler() != null && result.getStoppedByScheduler());

        } catch (org.springframework.dao.DataAccessException e) {
            log.error("╔═══════════════════════════════════════════════════════════╗");
            log.error("║  💥 ERROR DE BASE DE DATOS                               ║");
            log.error("╚═══════════════════════════════════════════════════════════╝");
            log.error("🗃️ Problema al acceder a la base de datos");
            log.error("💡 Posibles causas:");
            log.error("   - Conexión a BD perdida");
            log.error("   - Timeout de consulta");
            log.error("   - Tablas no existen o están corruptas");
            log.error("   - Permisos insuficientes");
            log.error("💥 Error técnico: {}", e.getMessage());
            log.error("🔍 Stack trace:", e);
            
            result.addError("ERROR DE BASE DE DATOS: " + e.getMessage());
            finalizarProcesamiento(result, startTime, false);
            
        } catch (Exception e) {
            log.error("╔═══════════════════════════════════════════════════════════╗");
            log.error("║  💥 ERROR CRÍTICO EN PROCESAMIENTO AUTOMÁTICO            ║");
            log.error("╚═══════════════════════════════════════════════════════════╝");
            log.error("🔴 Tipo error: {}", e.getClass().getName());
            log.error("💥 Mensaje: {}", e.getMessage());
            log.error("📊 Estado actual:");
            log.error("   - Campañas procesadas: {}", result.getCampaignsProcessed());
            log.error("   - Facturas procesadas: {}", result.getTotalProcessed());
            log.error("   - Exitosas: {}", result.getSuccessCount());
            log.error("   - Fallidas: {}", result.getFailedCount());
            log.error("🔍 Stack trace completo:", e);
            
            result.addError("ERROR CRÍTICO: " + e.getMessage());
            finalizarProcesamiento(result, startTime, false);
        }

        return result;
    }

    /**
     * Trunca un string a la longitud especificada.
     */
    private String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * Procesa una campaña completa: obtiene facturas pendientes y las envía una por una.
     * 
     * @param campaign Campaña a procesar
     * @param result Resultado acumulado del procesamiento
     * @param startTime Hora de inicio del procesamiento general
     */
    private void processCampaign(EmailCampaign campaign, EmailCampaignProcessResultDTO result, LocalDateTime startTime) {
        try {
            log.info("   ┌─────────────────────────────────────────────────────────");
            log.info("   │ 📋 PROCESANDO CAMPAÑA: {}", campaign.getNombre());
            log.info("   │ 🆔 ID: {}", campaign.getId());
            log.info("   │ 🏢 Empresa: {}", campaign.getIdEmpresa());
            log.info("   │ 📅 Período: {}/{}", campaign.getMes(), campaign.getAnno());
            log.info("   └─────────────────────────────────────────────────────────");

            // Obtener facturas pendientes de la campaña
            List<EmailCampaignDetalleDTO> facturasPendientes = 
                emailDetalleService.findEmailCampaignDetalleSinProcesar(campaign.getId());

            if (facturasPendientes == null || facturasPendientes.isEmpty()) {
                log.info("   ℹ️ No hay facturas pendientes en esta campaña");
                log.info("   ✅ Campaña #{} completada (sin facturas pendientes)", campaign.getId());
                return;
            }

            log.info("   📊 FACTURAS PENDIENTES: {}", facturasPendientes.size());
            log.info("   ⏱️ Tiempo estimado: ~{} minutos ({} seg/factura)", 
                     (facturasPendientes.size() * MANDATORY_DELAY_MS) / 60000,
                     MANDATORY_DELAY_MS / 1000);

            // Procesar cada factura
            int procesadasEnCampana = 0;
            int exitosasEnCampana = 0;
            int fallidasEnCampana = 0;

            for (EmailCampaignDetalleDTO factura : facturasPendientes) {
                
                // Verificar límite de tiempo ANTES de cada factura (4 horas)
                long tiempoTranscurrido = System.currentTimeMillis() - 
                    startTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
                
                if (tiempoTranscurrido >= MAX_EXECUTION_TIME_MS) {
                    log.warn("   ╔═══════════════════════════════════════════════════════════╗");
                    log.warn("   ║  ⏰ LÍMITE DE 4 HORAS ALCANZADO                          ║");
                    log.warn("   ╚═══════════════════════════════════════════════════════════╝");
                    log.warn("   📊 Progreso en campaña #{}: {}/{} facturas procesadas", 
                             campaign.getId(), procesadasEnCampana, facturasPendientes.size());
                    log.warn("   ⏹️ Deteniendo procesamiento de campaña actual");
                    result.setStoppedByScheduler(true);
                    break;
                }

                // Enviar factura con delay obligatorio
                boolean exitoso = sendInvoiceWithDelay(factura, procesadasEnCampana + 1, facturasPendientes.size());

                // Actualizar resultado
                result.incrementTotalProcessed();
                if (exitoso) {
                    result.incrementSuccess();
                    exitosasEnCampana++;
                } else {
                    result.incrementFailed();
                    fallidasEnCampana++;
                    result.addError(String.format("Campaña %d - Factura %s - Cliente %s", 
                                                 campaign.getId(), factura.getFactura(), factura.getNombreCliente()));
                }

                procesadasEnCampana++;
            }

            log.info("   ╔═══════════════════════════════════════════════════════════╗");
            log.info("   ║  📊 RESUMEN CAMPAÑA #{}                                  ", campaign.getId());
            log.info("   ╠═══════════════════════════════════════════════════════════╣");
            log.info("   ║  📋 Nombre: {}", String.format("%-40s", campaign.getNombre()));
            log.info("   ║  📊 Total procesadas: {}/{}", procesadasEnCampana, facturasPendientes.size());
            log.info("   ║  ✅ Exitosas: {}", exitosasEnCampana);
            log.info("   ║  ❌ Fallidas: {}", fallidasEnCampana);
            log.info("   ║  📈 Tasa éxito: {}%", 
                     procesadasEnCampana > 0 ? String.format("%.2f", (exitosasEnCampana * 100.0) / procesadasEnCampana) : "0.00");
            log.info("   ╚═══════════════════════════════════════════════════════════╝");

        } catch (Exception e) {
            log.error("   ╔═══════════════════════════════════════════════════════════╗");
            log.error("   ║  💥 ERROR CRÍTICO EN CAMPAÑA                             ║");
            log.error("   ╚═══════════════════════════════════════════════════════════╝");
            log.error("   🆔 Campaña ID: {}", campaign.getId());
            log.error("   📋 Nombre: {}", campaign.getNombre());
            log.error("   🔴 Tipo error: {}", e.getClass().getName());
            log.error("   💥 Mensaje: {}", e.getMessage());
            log.error("   🔍 Stack trace:", e);
            
            result.addError(String.format("ERROR CRÍTICO en campaña %d (%s): %s", 
                                         campaign.getId(), campaign.getNombre(), e.getMessage()));
        }
    }

    /**
     * Envía una factura individual aplicando el delay OBLIGATORIO de 10 segundos.
     * 
     * @param factura Factura a enviar
     * @param numero Número de factura actual
     * @param total Total de facturas
     * @return true si se envió exitosamente, false en caso contrario
     */
    private boolean sendInvoiceWithDelay(EmailCampaignDetalleDTO factura, int numero, int total) {
        try {
            log.info("   ┌─────────────────────────────────────────────────────────");
            log.info("   │ 📧 Factura {}/{}: {}", numero, total, factura.getFactura());
            log.info("   │ 👤 Cliente: {} ({})", factura.getNombreCliente(), factura.getEmail());
            log.info("   │ 📨 Email destino: {}", factura.getEmail());
            log.info("   │ 🆔 ID Detalle: {}", factura.getId());
            log.info("   │ ⏳ Iniciando envío...");

            // Enviar email (delega al servicio especializado)
            String response = emailDetalleService.sendMailUnitario(factura);

            // Validación robusta de la respuesta
            boolean exitoso = isSuccessfulResponse(response);

            if (exitoso) {
                log.info("   │ ✅ ENVÍO EXITOSO");
                log.info("   │ 📝 Respuesta API: {}", truncateResponse(response, 100));
            } else {
                log.error("   │ ❌ FALLO EN ENVÍO");
                log.error("   │ 📝 Respuesta API: {}", response);
                log.error("   │ 📧 Email afectado: {}", factura.getEmail());
                log.error("   │ 🧾 Factura afectada: {}", factura.getFactura());
            }

            log.info("   │ ⏱️ Aplicando delay obligatorio de {} segundos...", MANDATORY_DELAY_MS / 1000);
            log.info("   └─────────────────────────────────────────────────────────");

            // DELAY OBLIGATORIO - SIEMPRE se aplica independientemente del resultado
            Thread.sleep(MANDATORY_DELAY_MS);

            return exitoso;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("   ╔═══════════════════════════════════════════════════════════╗");
            log.error("   ║  ⚠️ INTERRUPCIÓN DE THREAD                               ║");
            log.error("   ╚═══════════════════════════════════════════════════════════╝");
            log.error("   📧 Email: {}", factura.getEmail());
            log.error("   🧾 Factura: {}", factura.getFactura());
            log.error("   💥 Motivo: El thread fue interrumpido durante el delay");
            log.error("   🔍 Stack trace:", e);
            return false;
            
        } catch (org.springframework.web.client.RestClientException e) {
            log.error("   ╔═══════════════════════════════════════════════════════════╗");
            log.error("   ║  ⚠️ ERROR DE COMUNICACIÓN HTTP                           ║");
            log.error("   ╚═══════════════════════════════════════════════════════════╝");
            log.error("   📧 Email: {}", factura.getEmail());
            log.error("   🧾 Factura: {}", factura.getFactura());
            log.error("   🌐 Problema: No se pudo conectar con el servicio de email");
            log.error("   💡 Posibles causas:");
            log.error("      - API de MailRelay no responde");
            log.error("      - Token de autenticación inválido");
            log.error("      - Timeout de conexión");
            log.error("      - Red no disponible");
            log.error("   💥 Error técnico: {}", e.getMessage());
            log.error("   🔍 Stack trace:", e);
            
            aplicarDelayDespuesDeError();
            return false;
            
        } catch (com.comunicamosmas.api.web.rest.errors.ExceptionNullSql e) {
            log.error("   ╔═══════════════════════════════════════════════════════════╗");
            log.error("   ║  ⚠️ ERROR EN BASE DE DATOS / DATOS FALTANTES            ║");
            log.error("   ╚═══════════════════════════════════════════════════════════╝");
            log.error("   📧 Email: {}", factura.getEmail());
            log.error("   🧾 Factura: {}", factura.getFactura());
            log.error("   🗃️ Mensaje: {}", e.getMessage());
            log.error("   📋 Detalles: {}", e.getDetails());
            log.error("   💡 Posibles causas:");
            log.error("      - Datos de factura incompletos en BD");
            log.error("      - Cliente sin email configurado");
            log.error("      - Deudas no encontradas para la factura");
            log.error("      - Campaña sin configuración de API");
            log.error("   🔍 Stack trace:", e);
            
            aplicarDelayDespuesDeError();
            return false;
            
        } catch (Exception e) {
            log.error("   ╔═══════════════════════════════════════════════════════════╗");
            log.error("   ║  ⚠️ ERROR INESPERADO                                     ║");
            log.error("   ╚═══════════════════════════════════════════════════════════╝");
            log.error("   📧 Email: {}", factura.getEmail());
            log.error("   🧾 Factura: {}", factura.getFactura());
            log.error("   👤 Cliente: {}", factura.getNombreCliente());
            log.error("   🆔 ID Detalle: {}", factura.getId());
            log.error("   🔴 Tipo de error: {}", e.getClass().getName());
            log.error("   💥 Mensaje: {}", e.getMessage());
            log.error("   💡 Acción recomendada: Revisar logs completos y notificar a soporte técnico");
            log.error("   🔍 Stack trace completo:", e);
            
            aplicarDelayDespuesDeError();
            return false;
        }
    }

    /**
     * Valida si la respuesta del servicio de email fue exitosa.
     * 
     * @param response Respuesta del servicio
     * @return true si fue exitoso, false en caso contrario
     */
    private boolean isSuccessfulResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            log.warn("      ⚠️ Respuesta nula o vacía del servicio de email");
            return false;
        }

        // Lista de indicadores de error
        String responseLower = response.toLowerCase();
        String[] errorIndicators = {
            "error", "fail", "failed", "exception", 
            "invalid", "unauthorized", "forbidden",
            "not found", "timeout", "rejected"
        };

        for (String indicator : errorIndicators) {
            if (responseLower.contains(indicator)) {
                return false;
            }
        }

        // Validación adicional para respuestas JSON exitosas
        if (response.contains("\"status\":\"ok\"") || 
            response.contains("\"status\":\"success\"") ||
            response.contains("\"success\":true")) {
            return true;
        }

        // Si no contiene errores explícitos, considerar exitoso
        return true;
    }

    /**
     * Trunca una respuesta larga para logging.
     * 
     * @param response Respuesta completa
     * @param maxLength Longitud máxima
     * @return Respuesta truncada
     */
    private String truncateResponse(String response, int maxLength) {
        if (response == null) return "null";
        if (response.length() <= maxLength) return response;
        return response.substring(0, maxLength) + "... (truncado)";
    }

    /**
     * Aplica el delay obligatorio incluso después de un error.
     */
    private void aplicarDelayDespuesDeError() {
        try {
            log.info("   ⏱️ Aplicando delay obligatorio después de error ({} segundos)...", 
                     MANDATORY_DELAY_MS / 1000);
            Thread.sleep(MANDATORY_DELAY_MS);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            log.error("   ⚠️ Delay post-error también fue interrumpido");
        }
    }

    /**
     * Finaliza el procesamiento guardando el reporte en base de datos.
     * 
     * @param result Resultado del procesamiento
     * @param startTime Hora de inicio
     * @param stoppedByScheduler Si fue detenido por el scheduler
     */
    private void finalizarProcesamiento(EmailCampaignProcessResultDTO result, 
                                       LocalDateTime startTime, 
                                       boolean stoppedByScheduler) {
        
        LocalDateTime endTime = LocalDateTime.now();
        result.setExecutionWindowEnd(endTime);
        result.setStoppedByScheduler(stoppedByScheduler);

        // Calcular duración
        long durationSeconds = java.time.Duration.between(startTime, endTime).getSeconds();
        result.setDurationSeconds((double) durationSeconds);

        log.info("════════════════════════════════════════════════════════════");
        log.info("📊 RESUMEN FINAL DE PROCESAMIENTO");
        log.info("════════════════════════════════════════════════════════════");
        log.info("⏰ Inicio:              {}", startTime);
        log.info("⏰ Fin:                 {}", endTime);
        log.info("⏱️ Duración:            {} segundos ({} minutos)", durationSeconds, durationSeconds / 60);
        log.info("📋 Campañas procesadas: {}", result.getCampaignsProcessed() != null ? result.getCampaignsProcessed() : 0);
        log.info("📊 Total procesado:     {}", result.getTotalProcessed());
        log.info("✅ Exitosos:            {}", result.getSuccessCount());
        log.info("❌ Fallidos:            {}", result.getFailedCount());
        log.info("⏰ Detenido a las 05:00: {}", stoppedByScheduler ? "SÍ" : "NO");
        log.info("════════════════════════════════════════════════════════════");

        // Guardar reporte en base de datos
        guardarReporte(result, startTime, endTime, stoppedByScheduler);
    }

    /**
     * Guarda el reporte final en la base de datos.
     * 
     * @param result Resultado del procesamiento
     * @param startTime Hora de inicio
     * @param endTime Hora de fin
     * @param stoppedByScheduler Si fue detenido por scheduler
     */
    private void guardarReporte(EmailCampaignProcessResultDTO result, 
                               LocalDateTime startTime, 
                               LocalDateTime endTime,
                               boolean stoppedByScheduler) {
        try {
            log.info("💾 Guardando reporte en base de datos...");

            EmailCampaignReportDTO reportDTO = new EmailCampaignReportDTO();
            
            // Datos básicos
            if (result.getCampaignIds() != null && !result.getCampaignIds().isEmpty()) {
                reportDTO.setCampaignId(result.getCampaignIds().get(0)); // Primera campaña procesada
            }
            
            reportDTO.setStartTime(startTime);
            reportDTO.setEndTime(endTime);
            reportDTO.setTotalProcessed(result.getTotalProcessed());
            reportDTO.setSuccessCount(result.getSuccessCount());
            reportDTO.setFailedCount(result.getFailedCount());
            reportDTO.setDurationSeconds(result.getDurationSeconds());
            
            // Detalles de errores
            if (result.getErrorsList() != null && !result.getErrorsList().isEmpty()) {
                reportDTO.setErrorDetails(String.join("\n", result.getErrorsList()));
            }

            // Guardar
            reportService.saveReport(reportDTO);
            
            log.info("💾 ✅ Reporte guardado exitosamente");

        } catch (Exception e) {
            log.error("💾 💥 Error guardando reporte en BD", e);
        }
    }

    /**
     * Log del banner de inicio del procesamiento
     */
    private void logStartBanner() {
        log.info("════════════════════════════════════════════════════════════");
        log.info("🚀 PROCESAMIENTO AUTOMÁTICO DE FACTURAS");
        log.info("════════════════════════════════════════════════════════════");
        log.info("📅 Fecha: {}", LocalDateTime.now().toLocalDate());
        log.info("⏰ Hora inicio: {}", LocalDateTime.now().toLocalTime());
        log.info("⏱️ Límite de tiempo: 4 horas");
        log.info("🕐 Delay obligatorio: {} segundos por factura", MANDATORY_DELAY_MS / 1000);
        log.info("📋 Campañas a procesar: Solo estado 'Abierto'");
        log.info("════════════════════════════════════════════════════════════");
    }
}
