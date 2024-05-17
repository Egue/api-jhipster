package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.ClausulaPermanencia;
import com.comunicamosmas.api.service.IClausulaPermanenciaService;

@RestController
@RequestMapping("/api/controlmas")
public class ClausurasController {
    
    private final IClausulaPermanenciaService clausulaPermanenciaService;

    public ClausurasController(IClausulaPermanenciaService clausulaPermanenciaService)
    {
        this.clausulaPermanenciaService = clausulaPermanenciaService;
    }

    @GetMapping("/clausuras")
    public ResponseEntity<?> find(@RequestParam("idContrato") Long idContrato)
    {
        try {
            ClausulaPermanencia clausulaPermanencia = clausulaPermanenciaService.findByIdContrato(idContrato);

            return ResponseEntity.status(HttpStatus.OK).body(clausulaPermanencia);
        } catch (Exception e) {
            // TODO: handle exception
            Map<String , Object> response = new HashMap<>();

            response.put("response", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
