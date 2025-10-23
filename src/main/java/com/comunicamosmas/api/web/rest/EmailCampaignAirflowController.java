package com.comunicamosmas.api.web.rest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.service.IAutomatedInvoiceProcessingService;
import com.comunicamosmas.api.service.dto.EmailCampaignProcessResultDTO;

@RestController
@RequestMapping("/api/airflow")
public class EmailCampaignAirflowController {

    private static final Logger log = LoggerFactory.getLogger(EmailCampaignAirflowController.class);

    private final IAutomatedInvoiceProcessingService automatedInvoiceProcessingService;

    public EmailCampaignAirflowController(
        IAutomatedInvoiceProcessingService automatedInvoiceProcessingService) {
        this.automatedInvoiceProcessingService = automatedInvoiceProcessingService;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("status", "UP");
            response.put("service", "Automated Invoice Processing Service");
            response.put("timestamp", LocalDateTime.now());
            response.put("version", "2.0-MVC");
            
            log.debug("Health check solicitado - Servicio UP");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error en health check", e);
            response.put("status", "DOWN");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    @PostMapping("/process-invoices")
    public ResponseEntity<EmailCampaignProcessResultDTO> processInvoicesAutomatically() {
        
        log.info("============================================================");
        log.info("ENDPOINT LLAMADO: POST /api/airflow/process-invoices");
        log.info("Timestamp: {}", LocalDateTime.now());
        log.info("============================================================");
        
        try {
            log.info("Delegando procesamiento al servicio automatico...");
            
            EmailCampaignProcessResultDTO resultado = 
                automatedInvoiceProcessingService.processInvoicesAutomatically();
            
            log.info("============================================================");
            log.info("PROCESAMIENTO COMPLETADO EXITOSAMENTE");
            log.info("============================================================");
            log.info("Resumen:");
            log.info("   Campanas procesadas: {}", resultado.getCampaignsProcessed());
            log.info("   Total emails: {}", resultado.getTotalProcessed());
            log.info("   Exitosos: {}", resultado.getSuccessCount());
            log.info("   Fallidos: {}", resultado.getFailedCount());
            log.info("   Duracion: {} segundos", String.format("%.2f", resultado.getDurationSeconds()));
            log.info("   Detenido por limite 4h: {}", resultado.getStoppedByScheduler());
            log.info("============================================================");
            
            return ResponseEntity.ok(resultado);
                
        } catch (Exception e) {
            log.error("============================================================");
            log.error("ERROR CRITICO en procesamiento automatico");
            log.error("Excepcion: {}", e.getMessage(), e);
            log.error("============================================================");
            
            EmailCampaignProcessResultDTO errorResult = createErrorResult(
                "Error critico: " + e.getMessage()
            );
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResult);
        }
    }

    private EmailCampaignProcessResultDTO createErrorResult(String errorMessage) {
        EmailCampaignProcessResultDTO result = new EmailCampaignProcessResultDTO();
        result.setExecutionWindowStart(LocalDateTime.now());
        result.setExecutionWindowEnd(LocalDateTime.now());
        result.setStoppedByScheduler(false);
        result.setCampaignsProcessed(0);
        result.addError(errorMessage);
        return result;
    }
}