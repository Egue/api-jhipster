package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.IEmailCampaignService;
import com.comunicamosmas.api.service.IFacturasServices;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.serviceMongo.IFacturasEmitidasService;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
 
@RestController
@RequestMapping("/api/controlmas")
public class EmailCampaignDetalleController {

	private final IEmailCampaignDetalleService emailCampaignDetalleService;

	private final IFacturasServices facturasServices;

	private final IFacturasEmitidasService facturasEmitidasService;

	private final IEmailCampaignService emailCampaignService;

	public EmailCampaignDetalleController(IEmailCampaignDetalleService emailCampaignDetalleService , IFacturasServices facturasServices , 
	IEmailCampaignService emailCampaignService , IFacturasEmitidasService facturasEmitidasService)
	{
		this.emailCampaignDetalleService = emailCampaignDetalleService;

		this.facturasServices = facturasServices;

		this.emailCampaignService = emailCampaignService;

		this.facturasEmitidasService = facturasEmitidasService;
	}

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

			EmailCampaign campaign = emailCampaignService.findById(detalle.getIdCampaign());

			if(campaign.getEstado().equals("PortalWeb"))
			{
				facturasEmitidasService.sendFactura(detalle , campaign);
				
			}else{ 
				
				String result = emailCampaignDetalleService.sendMailUnitario(detalle);

				response.put("response", result);

			}
 
			

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}catch(ExceptionNullSql ex){

				response.put("response", ex.getMessage() +" : "+ ex.getDetails());

				return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);

		}catch (Exception e) {
			
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	//agregar una factura del mes
	@PostMapping("/emailCampaingDetalle/addFactura")
	public ResponseEntity<?> addFactrua(@RequestBody EmailCampaignDetalleDTO detalle) {
		Map<String, Object> response = new HashMap<>();

		try {
 
			 emailCampaignDetalleService.addFactura(detalle);

			response.put("response", "Factura agregada");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}catch(ExceptionNullSql ex){

				response.put("response", ex.getMessage() +" : "+ ex.getDetails());

				return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);

		}catch (Exception e) {
			
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/emailCampaingDetalle/findlistbyidcliente")
	public ResponseEntity<?> findListByIdCliente(@RequestParam("cliente") Long idCliente , @RequestParam("init") int page , @RequestParam("size") int size)
	{
		try {
			
			PageRequest pageable =  PageRequest.of(page, size);

			List<EmailCampaignDetalleDTO> list = facturasServices.findListFacturaByIdCliente(idCliente, pageable);


			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			// TODO: handle exception
			Map<String, Object> response = new HashMap<>();

			response.put("response", e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}




}
