package com.comunicamosmas.api.web.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.IEmailCampaignReportService;
import com.comunicamosmas.api.service.IEmailCampaignService;
import com.comunicamosmas.api.service.dto.CampaignStatusDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignProcessResultDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignReportDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

/**
 * Controlador REST para integración con Apache Airflow.
 * Proporciona endpoints específicos para procesamiento batch de campañas de email.
 * 
 * Endpoints disponibles:
 * - GET  /api/airflow/health                         - Health check del servicio
 * - GET  /api/airflow/campaigns/pending              - Obtener campañas pendientes
 * - POST /api/airflow/campaigns/{id}/process         - Procesar una campaña específica
 * - GET  /api/airflow/campaigns/{id}/status          - Obtener estado de una campaña
 * - GET  /api/airflow/campaigns/{id}/reports         - Obtener reportes de una campaña
 * 
 * Este controlador maneja:
 * - Procesamiento batch de emails
 * - Generación de reportes de ejecución
 * - Monitoreo de progreso de campañas
 * - Integración con sistema de tareas automáticas (Airflow)
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@RestController
@RequestMapping("/api/airflow")
public class EmailCampaignAirflowController {

    private static final Logger log = LoggerFactory.getLogger(EmailCampaignAirflowController.class);

    private final IEmailCampaignDetalleService emailCampaignDetalleService;
    private final IEmailCampaignService emailCampaignService;
    private final IEmailCampaignReportService emailCampaignReportService;

    /**
     * Constructor con inyección de dependencias
     */
    public EmailCampaignAirflowController(
        IEmailCampaignDetalleService emailCampaignDetalleService,
        IEmailCampaignService emailCampaignService,
        IEmailCampaignReportService emailCampaignReportService
    ) {
        this.emailCampaignDetalleService = emailCampaignDetalleService;
        this.emailCampaignService = emailCampaignService;
        this.emailCampaignReportService = emailCampaignReportService;
    }

    /**
     * GET /api/airflow/health
     * 
     * Health check para verificar disponibilidad del servicio.
     * Utilizado por Airflow para validar conectividad antes de ejecutar tareas.
     * 
     * @return ResponseEntity con estado del servicio
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("status", "UP");
            response.put("service", "Email Campaign Batch Processor");
            response.put("timestamp", LocalDateTime.now());
            response.put("version", "1.0");
            
            log.debug("Health check solicitado - Servicio UP");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error en health check", e);
            response.put("status", "DOWN");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    /**
     * GET /api/airflow/campaigns/pending
     * 
     * Obtiene lista de campañas activas pendientes de procesamiento.
     * Excluye campañas con estado 'PortalWeb', 'Finalizado' e 'Inactivo'.
     * 
     * Response:
     * {
     *   "success": true,
     *   "campaigns": [
     *     {
     *       "id": 1,
     *       "nombre": "Facturación Enero 2024",
     *       "estado": "Activo",
     *       "totalPendientes": 150
     *     }
     *   ],
     *   "total": 1,
     *   "timestamp": "2024-10-20T10:30:00"
     * }
     * 
     * @return ResponseEntity con lista de campañas pendientes
     */
    @GetMapping("/campaigns/pending")
    public ResponseEntity<?> getPendingCampaigns() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("Airflow solicitando campañas pendientes");
            
            // Obtener campañas activas
            List<EmailCampaign> campaigns = emailCampaignService.findActiveCampaignsForBatch();
            
            List<Map<String, Object>> campaignList = new ArrayList<>();
            
            for (EmailCampaign campaign : campaigns) {
                Map<String, Object> campaignData = new HashMap<>();
                campaignData.put("id", campaign.getId());
                campaignData.put("nombre", campaign.getNombre());
                campaignData.put("estado", campaign.getEstado());
                campaignData.put("mes", campaign.getMes());
                campaignData.put("anno", campaign.getAnno());
                
                // Contar pendientes
                long totalPendientes = emailCampaignDetalleService.countPendingByCampaign(
                    Long.valueOf(campaign.getId())
                );
                campaignData.put("totalPendientes", totalPendientes);
                
                // Solo agregar si tiene pendientes
                if (totalPendientes > 0) {
                    campaignList.add(campaignData);
                }
            }
            
            response.put("success", true);
            response.put("campaigns", campaignList);
            response.put("total", campaignList.size());
            response.put("timestamp", LocalDateTime.now());
            
            log.info("Se encontraron {} campañas con emails pendientes", campaignList.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error obteniendo campañas pendientes", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * POST /api/airflow/campaigns/{campaignId}/process
     * 
     * Procesa una campaña específica en modo batch.
     * Envía todos los emails pendientes y genera reporte de ejecución.
     * 
     * Request Body (opcional):
     * {
     *   "batchSize": 100,        // Cantidad de emails por lote (default: 50)
     *   "delayMs": 100          // Delay entre envíos en ms (default: 100)
     * }
     * 
     * Response:
     * {
     *   "success": true,
     *   "campaignId": 1,
     *   "campaignName": "Facturación Enero 2024",
     *   "totalProcessed": 150,
     *   "successCount": 148,
     *   "failedCount": 2,
     *   "duration": 45.5,
     *   "timestamp": "2024-10-20T10:35:00"
     * }
     * 
     * @param campaignId ID de la campaña a procesar
     * @param params Parámetros opcionales de procesamiento
     * @return ResponseEntity con resultado del procesamiento
     */
    @PostMapping("/campaigns/{campaignId}/process")
    public ResponseEntity<?> processCampaign(
        @PathVariable Long campaignId,
        @RequestBody(required = false) Map<String, Object> params
    ) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("Airflow iniciando procesamiento de campaña ID: {}", campaignId);
            
            // Validar que la campaña existe
            EmailCampaign campaign = emailCampaignService.findById(campaignId);
            
            if (campaign == null) {
                response.put("success", false);
                response.put("error", "Campaña no encontrada");
                log.warn("Campaña {} no encontrada", campaignId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // Validar que NO es PortalWeb
            if ("PortalWeb".equalsIgnoreCase(campaign.getEstado())) {
                response.put("success", false);
                response.put("error", "Las campañas PortalWeb no se procesan por batch");
                log.warn("Intento de procesar campaña PortalWeb ID: {}", campaignId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Parámetros de procesamiento
            int batchSize = params != null && params.containsKey("batchSize") 
                ? ((Number) params.get("batchSize")).intValue()
                : 50;
            int delayMs = params != null && params.containsKey("delayMs")
                ? ((Number) params.get("delayMs")).intValue()
                : 100;
            
            log.info("Procesando campaña {} - BatchSize: {}, Delay: {}ms", 
                campaignId, batchSize, delayMs);
            
            // Procesar campaña
            EmailCampaignProcessResultDTO result = processCampaignBatch(
                campaign, 
                batchSize, 
                delayMs
            );
            
            // Guardar reporte
            EmailCampaignReportDTO reportDTO = new EmailCampaignReportDTO(result);
            reportDTO.setCampaignName(campaign.getNombre());
            emailCampaignReportService.saveReport(reportDTO);
            
            // Preparar respuesta
            response.put("success", true);
            response.put("campaignId", campaignId);
            response.put("campaignName", campaign.getNombre());
            response.put("totalProcessed", result.getTotalProcessed());
            response.put("successCount", result.getSuccessCount());
            response.put("failedCount", result.getFailedCount());
            response.put("duration", result.getDurationSeconds());
            response.put("successRate", String.format("%.2f%%", result.getSuccessRate()));
            response.put("timestamp", LocalDateTime.now());
            
            log.info("Campaña {} procesada - Total: {}, Éxitos: {}, Fallos: {}, Duración: {}s", 
                campaignId, 
                result.getTotalProcessed(),
                result.getSuccessCount(), 
                result.getFailedCount(),
                result.getDurationSeconds());
            
            return ResponseEntity.ok(response);
            
        } catch (ExceptionNullSql ex) {
            log.error("Error SQL procesando campaña {}: {}", campaignId, ex.getMessage(), ex);
            response.put("success", false);
            response.put("error", ex.getMessage());
            response.put("details", ex.getDetails());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (Exception e) {
            log.error("Error procesando campaña {}", campaignId, e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/airflow/campaigns/{campaignId}/status
     * 
     * Obtiene el estado actual de procesamiento de una campaña.
     * Incluye contadores de emails en diferentes estados y cálculo de progreso.
     * 
     * Response:
     * {
     *   "success": true,
     *   "campaignId": 1,
     *   "campaignName": "Facturación Enero 2024",
     *   "estado": "Activo",
     *   "total": 150,
     *   "pendientes": 2,
     *   "enviados": 148,
     *   "errores": 0,
     *   "progreso": 98.67
     * }
     * 
     * @param campaignId ID de la campaña
     * @return ResponseEntity con estado de la campaña
     */
    @GetMapping("/campaigns/{campaignId}/status")
    public ResponseEntity<?> getCampaignStatus(@PathVariable Long campaignId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.debug("Consultando estado de campaña ID: {}", campaignId);
            
            EmailCampaign campaign = emailCampaignService.findById(campaignId);
            
            if (campaign == null) {
                response.put("success", false);
                response.put("error", "Campaña no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // Obtener contadores
            long total = emailCampaignDetalleService.countByCampaign(campaignId);
            long pendientes = emailCampaignDetalleService.countPendingByCampaign(campaignId);
            long enviados = emailCampaignDetalleService.countSentByCampaign(campaignId);
            long errores = emailCampaignDetalleService.countErrorByCampaign(campaignId);
            
            // Calcular progreso
            double progreso = total > 0 ? (enviados * 100.0 / total) : 0.0;
            
            // Crear DTO de status
            CampaignStatusDTO statusDTO = new CampaignStatusDTO(
                campaignId,
                campaign.getNombre(),
                campaign.getEstado(),
                total,
                pendientes,
                enviados,
                errores
            );
            
            response.put("success", true);
            response.put("campaignId", campaignId);
            response.put("campaignName", campaign.getNombre());
            response.put("estado", campaign.getEstado());
            response.put("total", total);
            response.put("pendientes", pendientes);
            response.put("enviados", enviados);
            response.put("errores", errores);
            response.put("progreso", Math.round(progreso * 100.0) / 100.0);
            response.put("completed", statusDTO.isCompleted());
            response.put("successRate", statusDTO.getSuccessRate());
            
            log.debug("Estado campaña {}: {}/{} enviados ({}%)", 
                campaignId, enviados, total, String.format("%.2f", progreso));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error obteniendo estado de campaña {}", campaignId, e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/airflow/campaigns/{campaignId}/reports
     * 
     * Obtiene historial de reportes de una campaña.
     * Opcionalmente filtra por días recientes.
     * 
     * Query params:
     * - days: Cantidad de días hacia atrás (opcional, default: todos)
     * 
     * @param campaignId ID de la campaña
     * @param days Días a consultar (opcional)
     * @return ResponseEntity con lista de reportes
     */
    @GetMapping("/campaigns/{campaignId}/reports")
    public ResponseEntity<?> getCampaignReports(
        @PathVariable Long campaignId,
        @RequestParam(required = false) Integer days
    ) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.debug("Consultando reportes de campaña ID: {}, días: {}", campaignId, days);
            
            List<EmailCampaignReportDTO> reports;
            
            if (days != null && days > 0) {
                reports = emailCampaignReportService.findReportsByCampaignAndDays(campaignId, days);
            } else {
                reports = emailCampaignReportService.findReportsByCampaign(campaignId);
            }
            
            response.put("success", true);
            response.put("campaignId", campaignId);
            response.put("reports", reports);
            response.put("total", reports.size());
            
            log.debug("Se encontraron {} reportes para campaña {}", reports.size(), campaignId);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error obteniendo reportes de campaña {}", campaignId, e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Método privado para procesar una campaña en batch.
     * Itera sobre todos los detalles pendientes y los procesa.
     * 
     * @param campaign Campaña a procesar
     * @param batchSize Tamaño del lote
     * @param delayMs Delay entre envíos en milisegundos
     * @return Resultado del procesamiento con estadísticas
     */
    private EmailCampaignProcessResultDTO processCampaignBatch(
        EmailCampaign campaign,
        int batchSize,
        int delayMs
    ) {
        long startTime = System.currentTimeMillis();
        
        EmailCampaignProcessResultDTO result = new EmailCampaignProcessResultDTO(
            Long.valueOf(campaign.getId())
        );
        
        try {
            // Obtener detalles pendientes
            List<EmailCampaignDetalleDTO> pendingDetails = 
                emailCampaignDetalleService.findEmailCampaignDetalleSinProcesar(
                    Long.valueOf(campaign.getId())
                );
            
            log.info("Procesando {} emails pendientes para campaña {}", 
                pendingDetails.size(), campaign.getId());
            
            int processed = 0;
            
            for (EmailCampaignDetalleDTO detalle : pendingDetails) {
                try {
                    // Enviar email unitario
                    String sendResult = emailCampaignDetalleService.sendMailUnitario(detalle);
                    
                    // Evaluar resultado
                    if (sendResult != null && 
                        (sendResult.toLowerCase().contains("enviado") || 
                         sendResult.toLowerCase().contains("exitoso") ||
                         sendResult.toLowerCase().contains("success"))) {
                        result.incrementSuccess();
                    } else {
                        result.incrementFailed();
                        result.addError(detalle.getEmail(), sendResult != null ? sendResult : "Sin respuesta");
                        log.warn("Envío fallido a {}: {}", detalle.getEmail(), sendResult);
                    }
                    
                    result.incrementProcessed();
                    processed++;
                    
                    // Pausa entre envíos cada lote
                    if (processed % batchSize == 0) {
                        Thread.sleep(delayMs);
                        log.info("Procesados {} de {} emails ({}%)", 
                            processed, 
                            pendingDetails.size(),
                            (processed * 100 / pendingDetails.size()));
                    }
                    
                } catch (ExceptionNullSql ex) {
                    log.error("Error SQL enviando a {}: {}", detalle.getEmail(), ex.getMessage());
                    result.incrementFailed();
                    result.addError(detalle.getEmail(), ex.getMessage() + ": " + ex.getDetails());
                    
                } catch (InterruptedException ex) {
                    log.warn("Interrupción en delay de envío");
                    Thread.currentThread().interrupt();
                    
                } catch (Exception ex) {
                    log.error("Error enviando a {}: {}", detalle.getEmail(), ex.getMessage());
                    result.incrementFailed();
                    result.addError(detalle.getEmail(), ex.getMessage());
                }
            }
            
            log.info("Procesamiento completado: {} exitosos, {} fallidos", 
                result.getSuccessCount(), result.getFailedCount());
            
        } catch (Exception e) {
            log.error("Error general procesando campaña {}", campaign.getId(), e);
            result.setGeneralError(e.getMessage());
        }
        
        long endTime = System.currentTimeMillis();
        result.setEndTime(LocalDateTime.now());
        result.setDurationSeconds((endTime - startTime) / 1000.0);
        
        return result;
    }
}
