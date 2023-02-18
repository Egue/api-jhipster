package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.InventarioMarca;
import com.comunicamosmas.api.service.IInventarioMarcaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class InventarioMarcaController {

    @Autowired
    private IInventarioMarcaService inventarioMarcaService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/inventarioMarca/list")
    public ResponseEntity<?> list() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<InventarioMarca> listInventarioMarca = inventarioMarcaService.findAll();

            response.put("response", listInventarioMarca);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET findById*/
    @GetMapping("/inventarioMarca/{id}")
    public ResponseEntity<?> findMarcaById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            InventarioMarca marca = inventarioMarcaService.findById(id);
            response.put("response", marca);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/inventarioMarca/save")
    public ResponseEntity<?> save(@RequestBody InventarioMarca inventarioMarca) {
        Map<String, Object> response = new HashMap<>();

        try {
            inventarioMarcaService.save(inventarioMarca);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT updated*/
    @PutMapping("/inventarioMarca/{id}")
    public ResponseEntity<?> update(@RequestBody InventarioMarca marca, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            //consultar por id_marca
            InventarioMarca inventarioMarca = inventarioMarcaService.findById(id);

            inventarioMarca.setNombre(marca.getNombre());
            inventarioMarca.setEstado(marca.getEstado());

            inventarioMarcaService.save(inventarioMarca);
            response.put("response", "Actualizado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
