package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.security.AuthoritiesConstants;
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
	
	@GetMapping("/deudas/findbyidcontrato")
	public ResponseEntity<?> findByIdContrato(@RequestParam("contrato") Long contrato , 
	@RequestParam("init") int page , @RequestParam("size") int size)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			PageRequest pageable = PageRequest.of(page, size);

			Page<EstadoCuentaDeudasDTO> result = deudaService.findByIdContrato(contrato , pageable);
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

	@DeleteMapping("/deudas/deletebyid/{iddeuda}")
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<?> deletebyid(@Valid @PathVariable Long iddeuda)
	{
		Map<String, Object> response = new HashMap<>();

		try {
			deudaService.deleteById(iddeuda);

			response.put("response", "Eliminado");

			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.put("error", e.getMessage());

			return ResponseEntity.badRequest().body(response);
		}
	}
}
