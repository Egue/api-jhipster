package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/controlmas")
public class MikrotikController {

    @GetMapping("/mikrotik/test/{id}")
    public ResponseEntity<?> test(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            //consultamos la estacion para realizar el test

            response.put("response", "conexión válidada");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
