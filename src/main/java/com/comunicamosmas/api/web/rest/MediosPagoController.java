package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.MedioPago;
import com.comunicamosmas.api.service.IMedioPagoService;

@RestController
@RequestMapping("/api/controlmas")
public class MediosPagoController {
    

    @Autowired
    IMedioPagoService medioPagoService;

    @GetMapping("/mediopago")
    public ResponseEntity<?> getAll()
    {
        Map<String,Object> response = new HashMap<>();
        try {
            
            List<MedioPago> result = medioPagoService.findAll();

            response.put("medios", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
