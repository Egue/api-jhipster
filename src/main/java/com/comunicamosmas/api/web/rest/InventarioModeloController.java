package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.InventarioModelo;
import com.comunicamosmas.api.service.IInventarioModeloService;
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
public class InventarioModeloController {

    @Autowired
    private IInventarioModeloService inventarioModeloService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/inventarioModelo/list")
    public ResponseEntity<?> list() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<InventarioModelo> listInventarioModelo = inventarioModeloService.findAll();

            response.put("response", listInventarioModelo);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET findByModelo*/
    @GetMapping("/inventarioModelo/findByModelo/{id}")
    public ResponseEntity<?> findByModelo(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<InventarioModelo> listInventarioModel = inventarioModeloService.findByIdModelo(id);

            response.put("response", listInventarioModel);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //* ENDPOINT GET findById*/
    @GetMapping("/inventarioModelo/{id}")
    public ResponseEntity<?> findModeloById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            InventarioModelo modelo = inventarioModeloService.findById(id);
            response.put("response", modelo);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/inventarioModelo/save")
    public ResponseEntity<?> save(@RequestBody InventarioModelo inventarioModelo) {
        Map<String, Object> response = new HashMap<>();

        try {
            inventarioModeloService.save(inventarioModelo);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT updated*/
    @PutMapping("/inventarioModelo/{id}")
    public ResponseEntity<?> update(@RequestBody InventarioModelo modelo, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            //consultar por id_modelo
            InventarioModelo inventarioModelo = inventarioModeloService.findById(id);

            inventarioModelo.setNombre(modelo.getNombre());
            inventarioModelo.setEstado(modelo.getEstado());

            inventarioModeloService.save(inventarioModelo);
            response.put("response", "Actualizado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
