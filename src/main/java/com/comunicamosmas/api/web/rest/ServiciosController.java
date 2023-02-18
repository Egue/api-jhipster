package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Servicio;
import com.comunicamosmas.api.service.IServicioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class ServiciosController {

    @Autowired
    IServicioService servicioService;

    @GetMapping("/servicios/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Servicio> result = servicioService.findByName(name);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
