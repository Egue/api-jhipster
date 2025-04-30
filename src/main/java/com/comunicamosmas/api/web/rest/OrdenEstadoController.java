package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.OrdenEstado;
import com.comunicamosmas.api.service.IOrdenEstadoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class OrdenEstadoController {
	
	
	@Autowired
	IOrdenEstadoService ordenEstadoService;
	
	/**
	 * list*/
	@GetMapping("/ordenestado/all/{rol}")
	public ResponseEntity<?> all(@PathVariable String rol)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			List<OrdenEstado> result = ordenEstadoService.findAllByEstadoAndCliente(rol);
			
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
			
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
		}
	}

	/*@GetMapping("/ordenestado/all")
	public ResponseEntity<?> findAll()
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			List<OrdenEstado>  result = ordenEstadoService.findAll();
			
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
			
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
		}
	}*/

	@GetMapping("/ordenestado")
	public ResponseEntity<?> orders(@RequestParam(defaultValue = "1") Long client , @RequestParam(defaultValue = "0") Long estacion , @RequestParam(defaultValue = "0") Long reservada)
	{

		try {
			Optional<List<OrdenEstado>> list = ordenEstadoService.find(client , estacion , reservada);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
