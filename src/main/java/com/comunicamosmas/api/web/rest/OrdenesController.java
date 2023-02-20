package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO;  
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class OrdenesController {

    @Autowired
    IOrdenService ordenService;

    @GetMapping("/ordenes/instalacion")
    public ResponseEntity<?> listInstacion() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OrdenInstalacionDTO> ordenInstalacion = ordenService.ordenInstalacion();

            response.put("response", ordenInstalacion);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ordenes/FindByIdOrdeForInstalacion/{id}")
    public ResponseEntity<?> findByIdOrdeForInstalacion(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            OrdenForInstalacionFindByIdOrdenDTO orden = ordenService.ordenForInstalacionFindByIdOrden(id);

            response.put("response", orden);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT*/
    @PostMapping("/ordenes/instalacion/betwenn")
    public ResponseEntity<?> instalacionBetween(@RequestParam String valor1, @RequestParam String valor2) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OrdenInstalacionDTO> result = ordenService.getListFindBetwee(valor1, valor2);
            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
