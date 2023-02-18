package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.InventarioCategoria;
import com.comunicamosmas.api.service.IInventarioCategoriaService;
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
public class InventarioCategoriaController {

    @Autowired
    private IInventarioCategoriaService inventarioCategoriaService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/inventarioCategoria/list")
    public ResponseEntity<?> list() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<InventarioCategoria> listInventarioCategoria = inventarioCategoriaService.findAll();

            response.put("response", listInventarioCategoria);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/inventarioCategoria/save")
    public ResponseEntity<?> save(@RequestBody InventarioCategoria inventarioCategoria) {
        Map<String, Object> response = new HashMap<>();

        try {
            inventarioCategoriaService.save(inventarioCategoria);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * ENDPOINT GET FindById
     **/
    @GetMapping("/inventarioCategoria/{id}")
    public ResponseEntity<?> findCategoriaById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            InventarioCategoria categoria = inventarioCategoriaService.findById(id);
            response.put("response", categoria);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT updated*/
    @PutMapping("/inventarioCategoria/{id}")
    public ResponseEntity<?> update(@RequestBody InventarioCategoria categoria, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            //consultar por id_categoria
            InventarioCategoria inventarioCategoria = inventarioCategoriaService.findById(id);

            inventarioCategoria.setNombre(categoria.getNombre());
            inventarioCategoria.setEstado(categoria.getEstado());

            inventarioCategoriaService.save(inventarioCategoria);
            response.put("response", "Actualizado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
