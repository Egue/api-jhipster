package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.service.IEstacionService;
import com.comunicamosmas.api.service.dto.EstacionDTO;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/api/controlmas")
public class EstacionController {

    @Autowired
    private IEstacionService estacionService;

    /*
     * ENDPOINT save**/
    @PostMapping("/estaciones/save")
    public ResponseEntity<?> save(@RequestBody Estacion estacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            estacionService.save(estacion);

            response.put("response", "creado");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT all por estado activos 1 inactivo 0**/
    @GetMapping("/estaciones/all/{id}")
    public ResponseEntity<?> list(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<EstacionDTO> estaciones = estacionService.findAllDTO(id);

            response.put("response", estaciones);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**ENDPOINT post nombre estacion and idServicio*/
    @PostMapping("/estaciones/findByNombreAndIdServicio")
    public ResponseEntity<?> findByNombreAndIdServicio(@RequestBody Estacion estacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Estacion> result = estacionService.findByNombreAndIdServicio(estacion.getNombre(), estacion.getIdServicio());

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //estaciones por servicio
    @GetMapping("/estaciones/findByServicio/{idServicio}")
    public ResponseEntity<?> findByIdServicio(@PathVariable long idServicio){
         Map<String, Object> response = new HashMap<>();

        try {
             List<EstacionDTO> estaciones = estacionService.findByIdServicio(idServicio);

            response.put("response", estaciones);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
