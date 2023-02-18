package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.InventarioArticulo;
import com.comunicamosmas.api.service.IInventarioArticuloService;
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
public class InventarioArticuloController {

    @Autowired
    private IInventarioArticuloService inventarioArticuloService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/inventarioArticulo/list")
    public ResponseEntity<?> list() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<InventarioArticulo> listInventarioArticulos = inventarioArticuloService.findAll(null);

            response.put("response", listInventarioArticulos);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET findArticuloByCategoria*/
    @GetMapping("/inventarioArticulo/findArticuloByCategoria/{id}")
    public ResponseEntity<?> findArticuloByCategoria(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<InventarioArticulo> listInventarioArticulos = inventarioArticuloService.findInventarioArticuloByIdCategoria(id);

            response.put("response", listInventarioArticulos);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/inventarioArticulo/save")
    public ResponseEntity<?> save(@RequestBody InventarioArticulo inventarioArticulo) {
        Map<String, Object> response = new HashMap<>();

        try {
            inventarioArticuloService.save(inventarioArticulo);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT findArticuloById*/
    @GetMapping("/inventarioArticulo/{id}")
    public ResponseEntity<?> findArticuloById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            InventarioArticulo articulo = inventarioArticuloService.findById(id);
            response.put("response", articulo);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT updated*/
    @PutMapping("/inventarioArticulo/{id}")
    public ResponseEntity<?> update(@RequestBody InventarioArticulo articulo, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            //coonsultar por id_articulo
            InventarioArticulo inventarioArticulo = inventarioArticuloService.findById(id);

            inventarioArticulo.setNombre(articulo.getNombre());
            inventarioArticulo.setTipo(articulo.getTipo());
            inventarioArticulo.setIdCategoria(articulo.getIdCategoria());
            inventarioArticulo.setIdMarca(articulo.getIdMarca());
            inventarioArticulo.setModelo(articulo.getModelo());
            inventarioArticulo.setPrecio(articulo.getPrecio());
            inventarioArticulo.setPrecioVenta(articulo.getPrecioVenta());
            inventarioArticulo.setUnitario(articulo.getUnitario());
            inventarioArticulo.setNota(articulo.getNota());
            inventarioArticulo.setCodigo(articulo.getCodigo());

            inventarioArticuloService.save(inventarioArticulo);
            response.put("response", "Actualizado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
