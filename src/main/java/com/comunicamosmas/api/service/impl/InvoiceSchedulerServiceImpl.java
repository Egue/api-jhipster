package com.comunicamosmas.api.service.impl;

import com.comunicamosmas.api.service.IInvoiceSchedulerService;
import com.comunicamosmas.api.service.dto.ExecutionWindowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Implementación del servicio de gestión de ventana de tiempo.
 * 
 * Gestiona la ventana de ejecución automática de 01:00 AM a 05:00 AM.
 * 
 * Responsabilidades:
 * - Verificar si la hora actual está dentro de la ventana
 * - Calcular tiempo restante
 * - Proporcionar información detallada del estado temporal
 * 
 * IMPORTANTE:
 * - La ventana es FIJA: 01:00 - 05:00 (no configurable)
 * - El proceso DEBE detenerse a las 05:00 AM sin excepciones
 * 
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Service
public class InvoiceSchedulerServiceImpl implements IInvoiceSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceSchedulerServiceImpl.class);

    /**
     * Hora de inicio de la ventana de ejecución: 01:00 AM
     * CONSTANTE - NO MODIFICAR
     */
    private static final LocalTime WINDOW_START = LocalTime.of(1, 0);

    /**
     * Hora de fin de la ventana de ejecución: 05:00 AM
     * CONSTANTE - NO MODIFICAR
     */
    private static final LocalTime WINDOW_END = LocalTime.of(5, 0);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWithinExecutionWindow() {
        LocalTime now = LocalTime.now();
        boolean within = !now.isBefore(WINDOW_START) && now.isBefore(WINDOW_END);
        
        log.debug("⏰ Verificando ventana de ejecución: Hora actual={}, Dentro={}", now, within);
        
        return within;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getRemainingMinutes() {
        if (!isWithinExecutionWindow()) {
            log.debug("⏰ Fuera de la ventana de ejecución - Minutos restantes: 0");
            return 0;
        }

        LocalTime now = LocalTime.now();
        long minutes = ChronoUnit.MINUTES.between(now, WINDOW_END);
        
        log.debug("⏰ Minutos restantes hasta las 05:00 AM: {}", minutes);
        
        return minutes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionWindowInfo getWindowInfo() {
        LocalTime now = LocalTime.now();
        boolean within = isWithinExecutionWindow();
        long remaining = within ? getRemainingMinutes() : 0;

        ExecutionWindowInfo info = new ExecutionWindowInfo(
            now,
            WINDOW_START,
            WINDOW_END,
            within,
            remaining
        );

        log.debug("⏰ Información de ventana: {}", info);

        return info;
    }
}
