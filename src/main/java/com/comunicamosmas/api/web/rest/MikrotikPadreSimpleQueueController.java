package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.service.IMikrotikPadreSimpleQueueService;
import com.comunicamosmas.api.service.dto.SimpleQueueFindReusoDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class MikrotikPadreSimpleQueueController {
	
	@Autowired
	IMikrotikPadreSimpleQueueService mikrotikPadreService;
	
	@GetMapping("/mikrotikpadresimplequeue/findinfobycontrato/{id}")
	public ResponseEntity<?> findInfoByContrato(@PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			SimpleQueueFindReusoDTO result = mikrotikPadreService.simpleQueueInfoComponent(id);
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}
	

}
