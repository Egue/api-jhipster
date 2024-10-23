package com.comunicamosmas.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.dto.FacturaElectronicaResponseDTO;
import com.comunicamosmas.api.service.dto.ResponseSlimLoginDTO;
import com.comunicamosmas.api.service.dto.SupergirosPagosDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiRestServiceImpl implements IApiRestService {

	@Autowired
	IContratoService contratoService;

	@Autowired
	IOrdenService ordenService;

	@Autowired
	IPagoService pagoService;

	RestTemplate restTemplate = new RestTemplate();

	HttpHeaders headers = new HttpHeaders();

	/*
	 * REALIZA CONSULTA A INTERNETINALAMBRICO.COM.CO PARA CONSULTAR PAGOS SUPERGIROS
	 **/
	@Override
	public void pagosSupergiros() {

		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		headers.setContentType(MediaType.APPLICATION_JSON);

		String token = this.getAuthToken();

		if (token == null) {
			token = this.getAuthToken();
		}

		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Void> authenticatedRequest = new HttpEntity<>(headers);

		String url = "https://apps.internetinalambrico.com.co/repositories/backend_jwt_3_slim/public/pagos/estado/cargue";

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, authenticatedRequest,
					String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			ResponseRecaudo supergiros = objectMapper.readValue(response.getBody(), ResponseRecaudo.class);

			this.iterarSupergiros(supergiros);
			// SupergirosPagosDTO supergiros = response.getBody();

		} catch (HttpClientErrorException.Unauthorized ex) {
			token = this.getAuthToken();

			headers.set("Authorization", "Bearer " + token);

			authenticatedRequest = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, authenticatedRequest,
					String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			try {

				ResponseRecaudo supergiros = objectMapper.readValue(response.getBody(), ResponseRecaudo.class);

				this.iterarSupergiros(supergiros);

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}

	}

	/** ACTUALIZAR DESCARGUE */
	private void updatedEstadoCargue(Integer id) {

		headers.setContentType(MediaType.APPLICATION_JSON);

		String token = this.getAuthToken();

		if (token == null) {
			token = this.getAuthToken();
		}

		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Void> authenticatedRequest = new HttpEntity<>(headers);

		String url = "https://apps.internetinalambrico.com.co/repositories/backend_jwt_3_slim/public/pagos/actualizarcargue/"
				+ id;

		try {

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, authenticatedRequest,
					String.class);

		} catch (HttpClientErrorException.Unauthorized ex) {
			token = this.getAuthToken();

			headers.set("Authorization", "Bearer " + token);

			authenticatedRequest = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, authenticatedRequest,
					String.class);

		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}

	}

	/**
	 * cache @author Edwin egue
	 */
	//@Cacheable("authTokenCache")
	public String getAuthToken() {
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		ResponseSlimLoginDTO response = new ResponseSlimLoginDTO();
		try {
			HttpHeaders header = new HttpHeaders();

			header.setContentType(MediaType.APPLICATION_JSON);

			String jsonBody = "{\"email\":\"web@internetinalambrico.com.co\",\"password\":\"E$LaClav3\"}";

			HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, header);

			String url = "https://apps.internetinalambrico.com.co/repositories/backend_jwt_3_slim/public/auth/login";

			ResponseEntity<ResponseSlimLoginDTO> responseEntity = restTemplate.postForEntity(url, requestEntity,
					ResponseSlimLoginDTO.class);

			response = responseEntity.getBody();

			System.out.print("genera token nuevamente");

		} catch (Exception e) {
			System.out.print(e.getMessage());

		}

		return response.getResponse();
	}

	/**
	 * RECORRER LISTA DE PAGOS UNO A UNO
	 */

	private void iterarSupergiros(ResponseRecaudo supergiros) {
		if (supergiros.isSuccess() && supergiros.getResponse().size() > 0) {
			List<SupergirosPagosDTO> list = supergiros.getResponse();

			for (SupergirosPagosDTO rs : list) {
				String[] partes = rs.getNumero_referencia().split("A");
				// [0] contrato;
				// [1] cedula;
				Contrato contrato = contratoService.findById((Long) Long.parseLong(partes[0]));

				if (contrato.getEstado() == 2) {
					Orden reconexionActiva = ordenService.findOrdenActivaByTipo(3L, contrato.getId());

					if (reconexionActiva == null) {
						// validar si ya existe una orden de reconexion activa
						String comentario = "Pago por supergiros se crea automaticamente valor pagado: $"
								+ rs.getValor_total() + " / id_transaccion:" + rs.getId_transaccion();

						ordenService.reconexion(contrato, comentario);

					}

				} else if (contrato.getEstado() == 1) {
					// buscar si tiene orden de cortes activas
					Orden corteActiva = ordenService.findOrdenActivaByTipo(2L, contrato.getId());
					if (corteActiva != null) {
						// anular orde
						String comment = "Anulada por pago supergiros antes de ser asignada";

						ordenService.anularOrden(corteActiva, comment);

					}
				}
				//registrar el pago
				this.pagoService.registerPagoSupergiros(contrato, rs.getValor_total() , rs.getId_transaccion());
				// actualizar estado_cargue intinalambrico

				this.updatedEstadoCargue(rs.getId());

				// fin de ciclo
			}
		}
	}

	private String formatearFecha(String formato) {
		LocalDate fechaActual = LocalDate.now();

		// Formatear la fecha en el formato deseado (yyyyMMdd)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String fechaFormateada = fechaActual.format(formatter);

		return fechaFormateada;
	}

	/*
	 * private void login()
	 * {
	 * restTemplate.getMessageConverters().add(new
	 * MappingJackson2HttpMessageConverter());
	 * try {
	 * HttpHeaders header = new HttpHeaders();
	 * 
	 * header.setContentType(MediaType.APPLICATION_JSON);
	 * 
	 * String jsonBody =
	 * "{\"email\":\"web@internetinalambrico.com.co\",\"password\":\"E$LaClav3\"}";
	 * 
	 * HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, header);
	 * 
	 * String url =
	 * "https://apps.internetinalambrico.com.co/repositories/backend_jwt_3_slim/public/auth/login";
	 * 
	 * ResponseEntity<ResponseSlimLoginDTO> responseEntity =
	 * restTemplate.postForEntity(url, requestEntity, ResponseSlimLoginDTO.class);
	 * 
	 * ResponseSlimLoginDTO response = responseEntity.getBody();
	 * 
	 * jwt = response.getResponse();
	 * 
	 * System.out.print(response.getResponse());
	 * 
	 * 
	 * }catch(Exception e)
	 * {
	 * System.out.print(e.getMessage());
	 * 
	 * 
	 * }
	 * }
	 */

	/**
	 * CLASE PRIVATE
	 */

	static class ResponseRecaudo {
		private boolean success;

		private List<SupergirosPagosDTO> response;

		ResponseRecaudo() {

		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public List<SupergirosPagosDTO> getResponse() {
			return response;
		}

		public void setResponse(List<SupergirosPagosDTO> response) {
			this.response = response;
		}

	}

	@Override
	public FacturaElectronicaResponseDTO unSerializablePHP(String factura , Integer mesServicio , Long idEmpresa) {
		 
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		FacturaElectronicaResponseDTO facturaElectronica = new FacturaElectronicaResponseDTO();

		//String url = "http://10.111.39.2/controlmas/scriptPhp/index.php";
		//String url = "http://10.112.109.2/api/scriptPhp/index.php";
		String url = "http://gestion.cableytv.com:8050/api/scriptPhp/index.php";

		 
		JsonString jsonString = new JsonString(factura, mesServicio , idEmpresa);

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<JsonString> requEntity = new HttpEntity<>(jsonString, headers);

		try{
			ResponseEntity<FacturaElectronicaResponseDTO> responseEntity = restTemplate.postForEntity(url, requEntity, FacturaElectronicaResponseDTO.class);

		if(responseEntity.getStatusCode().is2xxSuccessful())
		{
			facturaElectronica = responseEntity.getBody();
			
			return facturaElectronica;
		}else{
			System.out.print("Error al enviar info");
		}
		}catch (HttpClientErrorException ex) {
			// Manejar una respuesta de error 4xx
			 
			throw new ExceptionNullSql(new Date(), "Error 4xx" , ex.getResponseBodyAsString());
		} catch (HttpServerErrorException ex) {
			// Manejar una respuesta de error 5xx
			throw new ExceptionNullSql(new Date(), "Error 5xx" , ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			// Manejar errores de conexión o URL incorrecta
			throw new ExceptionNullSql(new Date(), "Error url" , ex.getMessage());
		} catch (RestClientException ex) {
			// Manejar otras excepciones de RestTemplate
			throw new ExceptionNullSql(new Date(), "Error Inesperado" , ex.getMessage());
		}

		return null;
	}

	static class JsonString {
		private String factura;
		private Integer facturado_fecha;
		private Long idEmpresa;

		public JsonString(String factura , Integer facturado_fecha, Long idEmpresa)
		{ 
			this.factura = factura;
			this.facturado_fecha = facturado_fecha;
			this.idEmpresa = idEmpresa;
		}

		public String getFactura()
		{
			return this.factura;
		}

		public Integer getFacturado_fecha()
		{
			return this.facturado_fecha;
		}

		public Long getIdEmpresa()
		{
			return this.idEmpresa;
		}
	}

}
