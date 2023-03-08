package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Servicio;
import com.comunicamosmas.api.service.IServicioService;
import com.comunicamosmas.api.service.ITarifaService;
import com.comunicamosmas.api.service.dto.DisctVelocidadDTO;
import com.comunicamosmas.api.service.dto.TarifasForCambioDTO;
import com.comunicamosmas.api.service.dto.valorDTO;

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
public class TarifasController {

    @Autowired
    ITarifaService tarifaService;

    @GetMapping("/tarifa/velocidadDisct/{id}/{tecnologia}")
    public ResponseEntity<?> velocidadDisct(@PathVariable Long id , @PathVariable Long tecnologia) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<DisctVelocidadDTO> result = tarifaService.findVelocidadDisctByIdServicio(id , tecnologia);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * ENDPOINT POST buscar tarifas por velocidad y idServicio y tipo de cliente*/
    @PostMapping("/tarifa/findtarifa")
    public ResponseEntity<?> findTarifa(@RequestParam Long velocidad, @RequestParam Long idServicio , 
    		@RequestParam Long tipoTarifa , @RequestParam Long valor , @RequestParam Long idTecnologia)
    {
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		
    		List<TarifasForCambioDTO> result = tarifaService.tarifasForCambio(idServicio, velocidad, tipoTarifa, idTecnologia, valor);
    		
    		response.put("response", result);
    		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    	}catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/tarifa/findtarifavalor")
    public ResponseEntity<?> findTarifaValor(@RequestParam Long velocidad, @RequestParam Long idServicio , @RequestParam Long tipoTarifa , @RequestParam Long idTecnologia)
    {
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		
    		List<valorDTO> result = tarifaService.findTarifaValor(idServicio, velocidad, tipoTarifa , idTecnologia);
    		
    		response.put("response", result);
    		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    	}catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
