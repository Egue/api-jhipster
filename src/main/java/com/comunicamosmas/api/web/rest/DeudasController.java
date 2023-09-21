package com.comunicamosmas.api.web.rest;

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

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.service.IDeudaService;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class DeudasController {
	
	@Autowired
	IDeudaService deudaService;
	
	@GetMapping("/deudas/findbyidcontrato/{contrato}")
	public ResponseEntity<?> findByIdContrato(@PathVariable Long contrato)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<EstadoCuentaDeudasDTO> result = deudaService.findByIdContrato(contrato);
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
		}catch(Exception e)
		{
			response.put("Response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/deudas/findByFacturaEmailCampaign")
	public ResponseEntity<?> findByFacturaEmailCampaign(@RequestParam String factura  , Integer idCampaign)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<EmailCampaignDetalleDTO> result = deudaService.findDeudaByIdCampaingAndOrigen(factura, idCampaign);
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);

		}catch(ExceptionNullSql e){
			response.put("response", e.getMessage() + ": "+ e.getDetails());
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
		} catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
