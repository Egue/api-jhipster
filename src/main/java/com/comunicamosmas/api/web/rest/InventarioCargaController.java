package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.InventarioCarga;
import com.comunicamosmas.api.service.IInventarioCargaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/controlmas")
public class InventarioCargaController {

    @Autowired
    private IInventarioCargaService inventarioCargaService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/inventarioCarga/list")
    public ResponseEntity<?> list() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<InventarioCarga> listInventarioCarga = inventarioCargaService.findAll();

            response.put("response", listInventarioCarga);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/inventarioCarga/save")
    public ResponseEntity<?> save(@RequestBody InventarioCarga inventarioCarga) {
        Map<String, Object> response = new HashMap<>();

        try {
            inventarioCargaService.save(inventarioCarga);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
