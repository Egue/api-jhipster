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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.IEmailCampaignService;
import com.comunicamosmas.api.service.dto.EmailCampanignDTO;
 

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class EmailCampaignController {
	
	@Autowired
	IEmailCampaignService emailCampaignService;
	
	@PostMapping("/emailCampaign/save")
	public ResponseEntity<?> save(@RequestBody EmailCampaign emailCampaign)
	{
		Map<String, Object> response = new HashMap<>();
		
		try
		{
			emailCampaignService.save(emailCampaign);
			
			response.put("response", "creado");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**ENDPOINT ALL GET*/
	@GetMapping("/emailCampaign/all")
	public ResponseEntity<?> all()
	{
		Map<String, Object> response = new HashMap<>();
		
		try
		{
			 List<EmailCampanignDTO> result = emailCampaignService.findAllEmailCampaign();
			
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/emailCampaign/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			EmailCampanignDTO result = emailCampaignService.findByIdDTO(id);
			
			response.put("response", result);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
			
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/emailCampaign/search")
	public ResponseEntity<?> search(@RequestParam Long idEmpresa , @RequestParam String fecha)
	{
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			List<EmailCampanignDTO> result = emailCampaignService.filterEmailCampaign(idEmpresa, fecha);

			response.put("response", result);
			 
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
			
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
		}
	}

}
