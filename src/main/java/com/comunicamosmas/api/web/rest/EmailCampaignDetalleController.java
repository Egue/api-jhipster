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
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class EmailCampaignDetalleController {

	@Autowired
	IEmailCampaignDetalleService emailCampaignDetalleService;

	@GetMapping("/emailCampaignDetalle/generate/{idEmailCampaign}")
	public ResponseEntity<?> generate(@PathVariable Integer idEmailCampaign) {
		Map<String, Object> response = new HashMap<>();

		try {
			emailCampaignDetalleService.findEmailBySend(idEmailCampaign);

			response.put("response", "Generado");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/** GET BUSCAR POR IDEMAILCAMPAIGN */
	@GetMapping("/emailCampaignDetalle/emailCampaign/{id}")
	public ResponseEntity<?> findByEmailCampaign(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {

			List<EmailCampaignDetalleDTO> result = emailCampaignDetalleService.findByIdEmailCampaing(id);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	

	/**
	 * GET
	 * 
	 * @param id informacion del contratounitario
	 */
	@PostMapping("/emailCampaingDetalle/sendMail")
	public ResponseEntity<?> sendMailUnitario(@RequestBody EmailCampaignDetalleDTO detalle) {
		Map<String, Object> response = new HashMap<>();

		try {
 
			String result = emailCampaignDetalleService.sendMailUnitario(detalle);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}catch(ExceptionNullSql ex){

				response.put("response", ex.getDetails());

				return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);

		}catch (Exception e) {
			
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
