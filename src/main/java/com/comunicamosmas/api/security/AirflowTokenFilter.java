package com.comunicamosmas.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Filtro de seguridad para autenticar peticiones de Apache Airflow.
 *
 * Propósito:
 * - Validar token estático en header "tokenAir" para endpoints de Airflow
 * - Proporcionar autenticación adicional específica para tareas automáticas
 * - Separar la autenticación de Airflow de la autenticación JWT de usuarios
 *
 * Funcionamiento:
 * 1. Intercepta peticiones a /api/airflow/**
 * 2. Verifica header "tokenAir" con token configurado
 * 3. Si el token es válido, autentica la petición
 * 4. Si no es válido, rechaza con 401 Unauthorized
 *
 * Configuración:
 * - Token se configura en application-dev.yml: airflow.security.tokenAir
 * - Header esperado: tokenAir
 *
 * @author Sistema de Email Campaigns
 * @version 1.0
 */
@Component
public class AirflowTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AirflowTokenFilter.class);

    /**
     * Token estático configurado para Airflow
     * Se inyecta desde application-dev.yml
     */
    @Value("${airflow.security.tokenAir:}")
    private String airflowToken;

    /**
     * Nombre del header donde se espera el token
     */
    private static final String TOKEN_HEADER = "tokenAir";

    /**
     * Prefijo de rutas protegidas por este filtro
     */
    private static final String AIRFLOW_API_PREFIX = "/api/airflow/";

    /**
     * Filtra cada petición HTTP para validar token de Airflow
     *
     * @param request Petición HTTP
     * @param response Respuesta HTTP
     * @param filterChain Cadena de filtros
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // Solo procesar peticiones a endpoints de Airflow
        if (requestUri.startsWith(AIRFLOW_API_PREFIX)) {

            // Obtener token del header
            String tokenFromRequest = request.getHeader(TOKEN_HEADER);

            log.debug("Validando token de Airflow para URI: {}", requestUri);

            // Validar que el token esté presente y sea correcto
            if (tokenFromRequest == null || tokenFromRequest.trim().isEmpty()) {
                log.warn("Token de Airflow no proporcionado para URI: {}", requestUri);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token de Airflow requerido en header 'tokenAir'\"}");
                return;
            }

            if (!isValidToken(tokenFromRequest)) {
                log.warn("Token de Airflow inválido para URI: {}", requestUri);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token de Airflow inválido\"}");
                return;
            }

            // Token válido - establecer autenticación en contexto de seguridad
            log.debug("Token de Airflow válido para URI: {}", requestUri);
            authenticateAirflowRequest();
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Valida si el token proporcionado coincide con el configurado
     *
     * @param token Token a validar
     * @return true si es válido, false en caso contrario
     */
    private boolean isValidToken(String token) {
        if (airflowToken == null || airflowToken.trim().isEmpty()) {
            log.error("Token de Airflow no configurado en application-dev.yml");
            return false;
        }
        return airflowToken.equals(token.trim());
    }

    /**
     * Establece la autenticación en el contexto de seguridad
     * Otorga rol ROLE_AIRFLOW para permitir acceso a endpoints específicos
     */
    private void authenticateAirflowRequest() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            "airflow-service",
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_AIRFLOW"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Autenticación de Airflow establecida con rol ROLE_AIRFLOW");
    }
}
