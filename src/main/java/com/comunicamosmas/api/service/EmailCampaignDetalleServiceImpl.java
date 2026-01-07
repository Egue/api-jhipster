package com.comunicamosmas.api.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.domainMongo.FacturasEmitidas;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;
import com.comunicamosmas.api.service.mapper.EmailCampaingDetalleMapper;
import com.comunicamosmas.api.serviceMongo.IFacturasEmitidasService;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class EmailCampaignDetalleServiceImpl implements IEmailCampaignDetalleService {

	private static final Logger log = LoggerFactory.getLogger(EmailCampaignDetalleServiceImpl.class);

	@Autowired
	IEmailCampaignDetalleDao emailCampaignDetalleDao;

	@Autowired
	IEmailCampaignService emailCampaignService;

	@Autowired
	EmailCampaignApiService emailCampaignApiService;

	@Autowired
	IEmpresaService empresaService;

	@Autowired
	IClienteService clienteService;

	@Autowired
	IDeudaService deudaService;

	@Autowired
	IGeneratePDFService generatePDFService;

	@Autowired
	IZipFileCreatorService zipCreatorService;

	@Autowired
	EmailCampaingDetalleMapper emailCampaingDetalleMapper;

	@Autowired
	IFacturasEmitidasService facturasEmitidasService;

	/**
	 * Enviando mail a al cliente por mailrelay api
	 *
	 * @param datos                     del api de mailrelay
	 * @param destino                   detalle de la campaña 1 a 1
	 * @param fondo
	 * @param responseGeneracionFactura informacion de la generacion de factura
	 */
	@Override
	public String mailRelaySendMail(EmailCampaignApi datos, EmailCampaignDetalle destino, String fondo,
			RespuestaGeneracionPDFFactura responseGeneracionFactura) {

		log.debug("═══════════════════════════════════════════════════════════");
		log.debug("📤 MAILRELAY API - Enviando email");
		log.debug("═══════════════════════════════════════════════════════════");
		log.debug("📧 Destinatario: {}", destino.getEmail());
		log.debug("🧾 Factura: {}", destino.getFactura());
		log.debug("🔤 Origen: {}", destino.getOrigen());
		log.debug("🌐 URL API: {}", datos.getUrl());

		try {
			// ===== Construcción del mensaje =====
			log.debug("🔨 Construyendo estructura del mensaje...");

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MailRelaySendMail mailRelay = new MailRelaySendMail();

			// Mail json
			MailRelaySendMail.Send send = mailRelay.new Send();

			// From
			MailRelaySendMail.From from = mailRelay.new From();
			from.setEmail(datos.getMail_envio());
			from.setName(datos.getNombre_envio());
			send.setFrom(from);
			log.debug("   ✅ Remitente: {} <{}>", datos.getNombre_envio(), datos.getMail_envio());

			// To
			MailRelaySendMail.To to = mailRelay.new To();
			to.setEmail(destino.getEmail());
			to.setName(responseGeneracionFactura.getDestinatario());
			List<MailRelaySendMail.To> listTo = new ArrayList<MailRelaySendMail.To>();
			listTo.add(to);
			send.setTo(listTo);
			log.debug("   ✅ Destinatario: {} <{}>",
					 responseGeneracionFactura.getDestinatario(),
					 destino.getEmail());

			// Subject
			String subject = responseGeneracionFactura.getNit() + ";" +
							 responseGeneracionFactura.getRazon_social() + ";" +
							 responseGeneracionFactura.getPrefijo() +
							 responseGeneracionFactura.getFactura() + ";" +
							 responseGeneracionFactura.getCodigoDocumento() + ";" +
							 responseGeneracionFactura.getNameComercial();
			send.setSubject(subject);
			log.debug("   ✅ Asunto: {}", subject);

			// Body
			send.setHtml_part(fondo);
			send.setText_part("Factura");
			send.setTxt_part_auto(false);
			log.debug("   ✅ Cuerpo HTML configurado");

			// Attachment
			log.debug("📎 Preparando archivo adjunto...");
			MailRelaySendMail.Attachment attachment = mailRelay.new Attachment();
			List<MailRelaySendMail.Attachment> listAttachments = new ArrayList<MailRelaySendMail.Attachment>();

			// Determinar ruta del archivo según origen
			String pathConverter = destino.getOrigen().equals("A") ?
								   responseGeneracionFactura.getPathZIP() :
								   responseGeneracionFactura.getPathPDF();

			if (pathConverter == null || pathConverter.trim().isEmpty()) {
				throw new ExceptionNullSql(
					new Date(),
					"Ruta de archivo vacía",
					String.format("No se encontró ruta de archivo %s para factura %s",
						destino.getOrigen().equals("A") ? "ZIP" : "PDF",
						destino.getFactura())
				);
			}

			log.debug("   📁 Ruta archivo: {}", pathConverter);

			// Verificar que el archivo existe
			File archivoAdjunto = new File(pathConverter);
			if (!archivoAdjunto.exists()) {
				throw new ExceptionNullSql(
					new Date(),
					"Archivo no encontrado",
					"El archivo no existe en ruta: " + pathConverter
				);
			}

			log.debug("   ✅ Archivo existe: {} ({} bytes)",
					 archivoAdjunto.getName(),
					 archivoAdjunto.length());

			// Codificar en base64
			log.debug("   🔐 Codificando archivo en Base64...");
			String contentCodificadoBase64 = zipCreatorService.codificarBase64(pathConverter);

			if (contentCodificadoBase64 == null || contentCodificadoBase64.trim().isEmpty()) {
				throw new ExceptionNullSql(
					new Date(),
					"Error en codificación Base64",
					"La codificación Base64 del archivo retornó vacío"
				);
			}

			log.debug("   ✅ Base64 generado: {} caracteres", contentCodificadoBase64.length());

			attachment.setContent(contentCodificadoBase64);

			// Nombre del archivo
			Path filePath = destino.getOrigen().equals("A") ?
							Paths.get(responseGeneracionFactura.getPathZIP()) :
							Paths.get(responseGeneracionFactura.getPathPDF());

			attachment.setFile_name(filePath.getFileName().toString());
			String typeFile = destino.getOrigen().equals("A") ? "application/zip" : "application/pdf";
			attachment.setContent_type(typeFile);
			attachment.setContent_id("");
			listAttachments.add(attachment);
			send.setAttachments(listAttachments);

			log.debug("   ✅ Adjunto: {} ({})", filePath.getFileName().toString(), typeFile);

			// Headers
			MailRelaySendMail.Headers header = mailRelay.new Headers();
			header.setCustomHeader("Header value");
			send.setHeaders(header);

			// ===== Envío HTTP =====
			log.debug("🌐 Enviando petición HTTP a MailRelay...");

			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			headers.setContentType(MediaType.APPLICATION_JSON);

			String url = datos.getUrl() + "send_emails";
			headers.set("X-AUTH-TOKEN", datos.getToken());
			log.debug("   🔑 Token configurado: {}***", datos.getToken().substring(0, Math.min(10, datos.getToken().length())));

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(send);
			log.debug("   📦 Payload JSON: {} caracteres", json.length());

			HttpEntity<String> sendMail = new HttpEntity<>(json, headers);

			log.debug("   📡 POST → {}", url);
			ResponseEntity<String> response = restTemplate.postForEntity(url, sendMail, String.class);

			log.debug("   ✅ Respuesta HTTP: {} {}",
					 response.getStatusCode().value(),
					 response.getStatusCode().getReasonPhrase());

			String respuestaFinal = response.getBody();
			log.debug("   📝 Body respuesta: {}",
					 respuestaFinal != null && respuestaFinal.length() > 200 ?
					 respuestaFinal.substring(0, 200) + "..." : respuestaFinal);

			// ===== Guardar en BD =====
			log.debug("💾 Actualizando detalle en base de datos...");
			destino.setRespuestaMailRelay(respuestaFinal);
			destino.setProcesado(1);

			String urlDocument = destino.getOrigen().equals("A") ?
								 responseGeneracionFactura.getPathZIP() :
								 responseGeneracionFactura.getPathPDF();
			File file = new File(urlDocument);
			destino.setNameDocument(file.getName());

			this.save(destino);
			log.debug("   ✅ Detalle guardado con procesado=1");

			log.debug("╔═══════════════════════════════════════════════════════════╗");
			log.debug("║  ✅ EMAIL ENVIADO VÍA MAILRELAY                          ║");
			log.debug("╚═══════════════════════════════════════════════════════════╝");

			return respuestaFinal;

		} catch (org.springframework.web.client.HttpClientErrorException e) {
			log.error("╔═══════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR HTTP 4xx - PROBLEMA DEL CLIENTE                ║");
			log.error("╚═══════════════════════════════════════════════════════════╝");
			log.error("📧 Email: {}", destino.getEmail());
			log.error("🧾 Factura: {}", destino.getFactura());
			log.error("🔴 Código HTTP: {}", e.getStatusCode().value());
			log.error("📝 Respuesta: {}", e.getResponseBodyAsString());
			log.error("💡 Posibles causas:");
			log.error("   400 Bad Request - JSON malformado o datos inválidos");
			log.error("   401 Unauthorized - Token inválido o expirado");
			log.error("   403 Forbidden - Sin permisos para enviar emails");
			log.error("   404 Not Found - Endpoint incorrecto");
			log.error("   429 Too Many Requests - Límite de envíos excedido");
			log.error("🔍 Stack trace:", e);

			String errorMsg = String.format("Error HTTP %d: %s",
				e.getStatusCode().value(),
				e.getResponseBodyAsString());

			destino.setRespuestaMailRelay(errorMsg);
			destino.setProcesado(2); // Marcar como error
			this.save(destino);

			throw new ExceptionNullSql(
				new Date(),
				"Error HTTP al enviar a MailRelay",
				errorMsg
			);

		} catch (org.springframework.web.client.HttpServerErrorException e) {
			log.error("╔═══════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR HTTP 5xx - PROBLEMA DEL SERVIDOR               ║");
			log.error("╚═══════════════════════════════════════════════════════════╝");
			log.error("📧 Email: {}", destino.getEmail());
			log.error("🧾 Factura: {}", destino.getFactura());
			log.error("🔴 Código HTTP: {}", e.getStatusCode().value());
			log.error("📝 Respuesta: {}", e.getResponseBodyAsString());
			log.error("💡 Posibles causas:");
			log.error("   500 Internal Server Error - Error en servidor MailRelay");
			log.error("   502 Bad Gateway - Proxy/Gateway falló");
			log.error("   503 Service Unavailable - Servicio temporalmente no disponible");
			log.error("   504 Gateway Timeout - Timeout en servidor");
			log.error("🔍 Stack trace:", e);

			String errorMsg = String.format("Error servidor MailRelay %d: %s",
				e.getStatusCode().value(),
				e.getResponseBodyAsString());

			destino.setRespuestaMailRelay(errorMsg);
			destino.setProcesado(2);
			this.save(destino);

			throw new ExceptionNullSql(
				new Date(),
				"Error del servidor MailRelay",
				errorMsg
			);

		} catch (org.springframework.web.client.ResourceAccessException e) {
			log.error("╔═══════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR DE CONEXIÓN / TIMEOUT                          ║");
			log.error("╚═══════════════════════════════════════════════════════════╝");
			log.error("📧 Email: {}", destino.getEmail());
			log.error("🧾 Factura: {}", destino.getFactura());
			log.error("🌐 URL: {}", datos.getUrl());
			log.error("💥 Error: {}", e.getMessage());
			log.error("💡 Posibles causas:");
			log.error("   - Servidor MailRelay no responde");
			log.error("   - Timeout de conexión");
			log.error("   - Firewall bloqueando la conexión");
			log.error("   - URL incorrecta");
			log.error("   - Sin conexión a internet");
			log.error("🔍 Stack trace:", e);

			destino.setRespuestaMailRelay("Error de conexión: " + e.getMessage());
			destino.setProcesado(2);
			this.save(destino);

			throw new ExceptionNullSql(
				new Date(),
				"No se pudo conectar con MailRelay",
				e.getMessage()
			);

		} catch (HttpMessageConversionException e) {
			log.error("╔═══════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR DE CONVERSIÓN JSON                             ║");
			log.error("╚═══════════════════════════════════════════════════════════╝");
			log.error("📧 Email: {}", destino.getEmail());
			log.error("🧾 Factura: {}", destino.getFactura());
			log.error("💥 Error: {}", e.getMessage());
			log.error("💡 Posibles causas:");
			log.error("   - Objeto Send con datos inválidos");
			log.error("   - Campos null donde no deberían estar");
			log.error("   - Encoding incorrecto en HTML");
			log.error("   - Base64 malformado");
			log.error("🔍 Stack trace:", e);

			destino.setRespuestaMailRelay("Error JSON: " + e.getMessage());
			destino.setProcesado(2);
			this.save(destino);

			throw new ExceptionNullSql(
				new Date(),
				"Error al convertir datos a JSON",
				e.getMessage()
			);

		} catch (Exception e) {
			log.error("╔═══════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR INESPERADO EN MAILRELAY                        ║");
			log.error("╚═══════════════════════════════════════════════════════════╝");
			log.error("📧 Email: {}", destino.getEmail());
			log.error("🧾 Factura: {}", destino.getFactura());
			log.error("🔴 Tipo error: {}", e.getClass().getName());
			log.error("💥 Mensaje: {}", e.getMessage());
			log.error("🔍 Stack trace completo:", e);

			destino.setRespuestaMailRelay("Error inesperado: " + e.getMessage());
			destino.setProcesado(2);
			this.save(destino);

			throw new ExceptionNullSql(
				new Date(),
				"Error inesperado con MailRelay",
				e.getMessage()
			);
		}
	}

	/** guarda el detalle de la campaña */
	@Override
	public void save(EmailCampaignDetalle emailCampaignDetalle) {
		// TODO Auto-generated method stub
		emailCampaignDetalleDao.save(emailCampaignDetalle);
	}

	/**
	 * buscar los contratos
	 *
	 * @param idEmailCampaign
	 *                        id de la campaña para guardar en el detalle
	 * @param fecha
	 *                        carga la fecha de las facturas generadas a los
	 *                        contratos
	 *                        hace un recorrido de los contratos con
	 *                        preferencia_factura = 2
	 */
	@Override
	public void findEmailBySend(Integer idEmailCampaign) {
		EmailCampaign campaign = emailCampaignService.findById(idEmailCampaign);
		/*
		 * LocalDate fechaActual = LocalDate.now();
		 * int anio = fechaActual.getYear();
		 * Month mes = fechaActual.getMonth();
		 * String fecha = String.valueOf(anio) + String.valueOf(mes);
		 */
		if(campaign.getEstado().equals("PortalWeb"))
		{
			invoiceByPortalWeb(idEmailCampaign , campaign);
		}else{


			String fechaFactura = campaign.getAnno() + campaign.getMes();

			List<Object[]> result = emailCampaignDetalleDao.findEmailBySend(campaign.getIdEmpresa(), fechaFactura);

			if (result != null) {
				for (Object[] rs : result) {
					EmailCampaignDetalle obj = new EmailCampaignDetalle();

					obj.setFactura((String) rs[0].toString());
					obj.setIdCliente((Integer) rs[1]);
					obj.setEmail((String) rs[2]);
					obj.setIdServicio((Integer) rs[3]);
					obj.setProcesado(0);
					obj.setIdEmailCampaign(idEmailCampaign);
					obj.setOrigen((String) rs[4].toString());
					this.save(obj);
				}
			}
		}


	}

	/**
	 * @param Long id
	 *             id de la campaña principal
	 *             Busca el detalle de los contratos para enviar en la campaña
	 */

	@Override
	public List<EmailCampaignDetalleDTO> findByIdEmailCampaing(Long id) {
		EmailCampaign campaign = emailCampaignService.findById(id.intValue());

		if(campaign.getEstado().equals("PortalWeb"))
		{
			List<FacturasEmitidas> emitidas = facturasEmitidasService.findByidCampaign(campaign.getId());

			return emitidas.stream().map(this::converterFacturasToDetalleDTO).collect(Collectors.toList());

		}else{
			Optional<List<Object[]>> result = emailCampaignDetalleDao.findByIdEmailCampaign(id);

		List<EmailCampaignDetalleDTO> email = result.map(resp ->
			resp.stream().map(rs ->{

			EmailCampaignDetalleDTO obj = new EmailCampaignDetalleDTO();

			obj.setFactura((String) rs[0]);
			obj.setIdCliente((Integer) rs[1]);
			obj.setEmail((String) rs[2]);
			obj.setResponse((String) rs[3]);
			obj.setProcesado((String) rs[4]);
			obj.setNombreServicio((String) rs[5]);
			obj.setTipoCliente((String) rs[6]);
			obj.setNombreCliente((String) rs[7]);
			obj.setId((Integer) rs[8]);
			obj.setIdCampaign((Integer) rs[9]);
			obj.setOrigen((String) rs[10]);
			obj.setNameDocument((String) rs[11]);

			return obj;

			}).collect(Collectors.toList())).orElse(new ArrayList<>());

		return email;
		}


	}

	/**
	 * @param Integer id
	 *                Id del detalle de la campaña detalle, para realizar el
	 *                recorrido e iniciar a enviar
	 */

	private String sendMail(Integer id, RespuestaGeneracionPDFFactura responsePDF) {

		// consultamos el id de emailcampaigndetalle
		EmailCampaignDetalle unitario = this.findById(id);

		String unitarioIdService = Integer.toString(unitario.getIdServicio());
		// buscar los datos de sesión en emailCampaignApi
		EmailCampaignApi datos = new EmailCampaignApi();

		List<EmailCampaignApiDTO> api = emailCampaignApiService.findAll();

		outerloop: for (EmailCampaignApiDTO rs : api) {
			String[] idServicio = rs.getServicio().split(",");

			for (String service : idServicio) {
				// System.out.print("unitarios: "+unitario.getIdServicio()+ "service : " +
				// service);

				if (unitarioIdService.equals(service)) {
					datos.setToken(rs.getToken());
					datos.setUrl(rs.getUrl());
					datos.setMail_envio(rs.getMail_envio());
					datos.setNombre_envio(rs.getNombre_envio());
					datos.setHtml_part(rs.getHtml_part());
					break outerloop;
				}
			}
		}
		String fondo = this.fondoMail(responsePDF, datos);
		// System.out.print(fondo);
		return this.mailRelaySendMail(datos, unitario, fondo, responsePDF);

	}

	/**
	 * @param Integer id
	 *                buscar detalla de la campaña 1 registro buscar por
	 *                idcamañadetalle
	 */
	@Override
	public EmailCampaignDetalle findById(Integer id) {
		// TODO Auto-generated method stub
		return emailCampaignDetalleDao.findById(id).orElse(null);
	}

	/**
	 * @param idContrato el contrato del la factura
	 * @param api        el objecto de la informacion del html_part
	 */
	@Override
	public String fondoMail(RespuestaGeneracionPDFFactura response, EmailCampaignApi api) {

		if (api.getHtml_part().isEmpty()) {
			throw new ExceptionNullSql(new Date(), "Campo nullo", "no se encontro información para fondo");
		}

		String[] html_part = api.getHtml_part().split("@");

		// ultimo dia del mes
		LocalDate fecha = LocalDate.now();
		int year = fecha.getYear();
		LocalDate ultimoDiaDelMes = fecha.withDayOfMonth(fecha.lengthOfMonth());
		String fechaConcatenada = ultimoDiaDelMes.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		// valor a cancelar
		// String deuda = deudaService.findDeudaByIdContrato(id);

		String message = html_part[0] + response.getLogoPublico() + html_part[1] + response.getDestinatario()
				+ html_part[2] + fechaConcatenada + html_part[3] + response.getValorPagar() + html_part[4] + year +
				html_part[5] + "Version: 2.0" + html_part[6];
		// System.out.print(message);
		return message;
		// return null;
	}

	/**
	 * @param EmailCampaignDetalle.get id
	 */
	@Override
	public String sendMailUnitario(EmailCampaignDetalleDTO detalle) {
		log.info("╔════════════════════════════════════════════════════════════╗");
		log.info("║  📧 ENVÍO INDIVIDUAL - sendMailUnitario()                 ║");
		log.info("╚════════════════════════════════════════════════════════════╝");
		log.info("🧾 Factura: {}", detalle.getFactura());
		log.info("👤 Cliente ID: {}", detalle.getIdCliente());
		log.info("📧 Email destino: {}", detalle.getEmail());
		log.info("🆔 ID Detalle: {}", detalle.getId());
		log.info("🆔 ID Campaña: {}", detalle.getIdCampaign());
		log.info("🔤 Origen: {}", detalle.getOrigen());

		try {
			// ===== PASO 1: Obtener campaña =====
			log.debug("🔍 PASO 1/6: Obteniendo datos de campaña...");
			EmailCampaign campaña = emailCampaignService.findById(detalle.getIdCampaign());

			if (campaña == null) {
				throw new ExceptionNullSql(
					new Date(),
					"Campaña no encontrada",
					"No existe campaña con ID: " + detalle.getIdCampaign()
				);
			}

			log.debug("✅ Campaña obtenida: {} (ID: {})", campaña.getNombre(), campaña.getId());
			log.debug("   📅 Período: {}/{}", campaña.getMes(), campaña.getAnno());
			log.debug("   🏢 Empresa: {}", campaña.getIdEmpresa());

			// ===== PASO 2: Calcular período de facturación =====
			log.debug("🔍 PASO 2/6: Calculando período de facturación...");
			String fecha = campaña.getAnno() + campaña.getMes();
			String primeroscaracteres = fecha.substring(0, 6);
			Long mesServicio = Long.parseLong(primeroscaracteres);
			log.debug("✅ Mes servicio calculado: {}", mesServicio);

			// ===== PASO 3: Consultar deudas =====
			log.debug("� PASO 3/6: Consultando deudas asociadas a la factura...");
			log.debug("   🧾 Factura: {}", detalle.getFactura());
			log.debug("   📅 Mes: {}", mesServicio);
			log.debug("   🏢 Empresa: {}", campaña.getIdEmpresa());
			log.debug("   👤 Cliente: {}", detalle.getIdCliente());
			log.debug("   � Origen: {}", detalle.getOrigen());

			List<DeudasForFacturaDTO> deudas = deudaService.findDeudaByFacturaAndMesServiceAndIdEmpresa(
					Long.parseLong(detalle.getFactura()),
					mesServicio,
					campaña.getIdEmpresa(),
					detalle.getIdCliente(),
					detalle.getOrigen()
			);

			if (deudas == null || deudas.isEmpty()) {
				throw new ExceptionNullSql(
					new Date(),
					"Sin deudas asociadas",
					String.format("No se encontraron deudas para factura %s, cliente %d",
						detalle.getFactura(), detalle.getIdCliente())
				);
			}

			log.debug("✅ Deudas encontradas: {} registros", deudas.size());

			// ===== PASO 4: Generar PDF de factura =====
			log.debug("🔍 PASO 4/6: Generando PDF de factura...");
			RespuestaGeneracionPDFFactura generacionPDF = generatePDFService.generateFacturaPDF(
				detalle,
				deudas,
				campaña
			);

			if (generacionPDF == null) {
				throw new ExceptionNullSql(
					new Date(),
					"Error en generación PDF",
					"El servicio de generación PDF retornó null"
				);
			}

			log.debug("✅ PDF generado exitosamente");
			log.debug("   📄 Ruta PDF: {}", generacionPDF.getPathPDF());
			log.debug("   👤 Destinatario: {}", generacionPDF.getDestinatario());
			log.debug("   💰 Valor: {}", generacionPDF.getValorPagar());

			// ===== PASO 5: Generar ZIP si origen es 'A' =====
			if(detalle.getOrigen().equals("A")) {
				log.debug("🔍 PASO 5/6: Origen 'A' detectado - Generando ZIP...");

				try {
					String pathZip = zipCreatorService.zipFileFactura(generacionPDF, detalle);

					if (pathZip == null || pathZip.trim().isEmpty()) {
						throw new ExceptionNullSql(
							new Date(),
							"Error generando ZIP",
							"El servicio de ZIP retornó ruta vacía"
						);
					}

					generacionPDF.setPathZIP(pathZip);
					log.debug("✅ ZIP generado: {}", pathZip);

					// Eliminar archivos temporales
					log.debug("🗑️ Eliminando archivos temporales (PDF y XML)...");
					File pdf = new File(generacionPDF.getPathPDF());
					File xml = new File(generacionPDF.getPathXML());

					boolean pdfDeleted = false;
					boolean xmlDeleted = false;

					if (pdf.exists()) {
						pdfDeleted = pdf.delete();
						log.debug("   📄 PDF temporal: {}", pdfDeleted ? "✅ Eliminado" : "❌ No eliminado");
					}

					if (xml.exists()) {
						xmlDeleted = xml.delete();
						log.debug("   📄 XML temporal: {}", xmlDeleted ? "✅ Eliminado" : "❌ No eliminado");
					}

				} catch (Exception zipEx) {
					log.error("❌ Error al crear ZIP:");
					log.error("   💥 Mensaje: {}", zipEx.getMessage());
					throw new ExceptionNullSql(
						new Date(),
						"Error al crear archivo ZIP",
						zipEx.getMessage()
					);
				}
			} else {
				log.debug("ℹ️ PASO 5/6: Origen '{}' - No requiere ZIP", detalle.getOrigen());
			}

			// ===== PASO 6: Enviar email =====
			log.info("🔍 PASO 6/6: Enviando email...");
			log.info("   📧 Destinatario: {}", detalle.getEmail());
			log.info("   📎 Adjunto: {}", detalle.getOrigen().equals("A") ? "ZIP" : "PDF");
			log.info("   🧾 Factura: {}", detalle.getFactura());

			String send = this.sendMail(detalle.getId(), generacionPDF);

			if (send == null) {
				throw new ExceptionNullSql(
					new Date(),
					"Error en envío de email",
					"El servicio de email retornó respuesta nula"
				);
			}

			log.info("╔════════════════════════════════════════════════════════════╗");
			log.info("║  ✅ EMAIL ENVIADO EXITOSAMENTE                            ║");
			log.info("╚════════════════════════════════════════════════════════════╝");
			log.info("📧 Email: {}", detalle.getEmail());
			log.info("🧾 Factura: {}", detalle.getFactura());
			log.info("📝 Respuesta API: {}", send.length() > 100 ? send.substring(0, 100) + "..." : send);

			return send;

		} catch (ExceptionNullSql ex) {
			log.error("╔════════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR SQL / DATOS FALTANTES                           ║");
			log.error("╚════════════════════════════════════════════════════════════╝");
			log.error("📧 Email destino: {}", detalle.getEmail());
			log.error("🧾 Factura: {}", detalle.getFactura());
			log.error("👤 Cliente ID: {}", detalle.getIdCliente());
			log.error("🔴 Mensaje: {}", ex.getMessage());
			log.error("� Detalles: {}", ex.getDetails());
			log.error("💡 Posibles causas:");
			log.error("   - Campaña no existe");
			log.error("   - Cliente sin email configurado");
			log.error("   - Deudas no encontradas para la factura");
			log.error("   - Datos incompletos en base de datos");
			log.error("🔍 Stack trace:", ex);
			throw ex;

		} catch (NumberFormatException ex) {
			log.error("╔════════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR DE FORMATO NUMÉRICO                             ║");
			log.error("╚════════════════════════════════════════════════════════════╝");
			log.error("📧 Email destino: {}", detalle.getEmail());
			log.error("🧾 Factura: {}", detalle.getFactura());
			log.error("💥 Error: {}", ex.getMessage());
			log.error("💡 Posibles causas:");
			log.error("   - Número de factura inválido");
			log.error("   - Formato de fecha incorrecto en campaña");
			log.error("   - ID de cliente no numérico");
			log.error("🔍 Stack trace:", ex);
			throw new ExceptionNullSql(
				new Date(),
				"Error de formato numérico",
				"Número de factura o fecha con formato inválido: " + ex.getMessage()
			);

		} catch (Exception e) {
			log.error("╔════════════════════════════════════════════════════════════╗");
			log.error("║  ❌ ERROR INESPERADO                                      ║");
			log.error("╚════════════════════════════════════════════════════════════╝");
			log.error("📧 Email destino: {}", detalle.getEmail());
			log.error("🧾 Factura: {}", detalle.getFactura());
			log.error("👤 Cliente: {}", detalle.getNombreCliente());
			log.error("🆔 ID Detalle: {}", detalle.getId());
			log.error("🔴 Tipo error: {}", e.getClass().getName());
			log.error("💥 Mensaje: {}", e.getMessage());
			log.error("🔍 Stack trace completo:", e);
			throw new ExceptionNullSql(
				new Date(),
				"Error inesperado al generar envío",
				e.getMessage()
			);
		}
	}

	@Override
	public List<EmailCampaignDetalleDTO> findEmailCampaignDetalleSinProcesar(Integer idEmailCampaign) {

		List<Object[]> result = emailCampaignDetalleDao.findEmailCampaignDetalleSinProcesar(idEmailCampaign);

		List<EmailCampaignDetalleDTO> email = new ArrayList<EmailCampaignDetalleDTO>();

		if (result != null && !result.isEmpty()) {
			for (Object[] rs : result) {
				EmailCampaignDetalleDTO obj = new EmailCampaignDetalleDTO();

				obj.setFactura((String) rs[0]);
				obj.setIdCliente((Integer) rs[1]);
				obj.setEmail((String) rs[2]);
				obj.setResponse((String) rs[3]);
				obj.setProcesado((String) rs[4]);
				obj.setNombreServicio((String) rs[5]);
				obj.setTipoCliente((String) rs[6]);
				obj.setNombreCliente((String) rs[7]);
				obj.setId((Integer) rs[8]);
				obj.setIdCampaign((Integer) rs[9]);
				obj.setOrigen((String) rs[10]); // ⭐ AGREGADO: Campo origen
				email.add(obj);
			}
		}
		return email;
	}

	@Override
	public void addFactura(EmailCampaignDetalleDTO detalle) {
		// validamos si existe la factura ya creada
		Optional<List<Object[]>> result = emailCampaignDetalleDao.validExiste(detalle.getFactura(),
				detalle.getIdCampaign());
		EmailCampaignDetalle insert = new EmailCampaignDetalle();
		insert.setEmail(detalle.getEmail());
		insert.setFactura(detalle.getFactura());
		insert.setIdCliente(detalle.getIdCliente());
		insert.setIdEmailCampaign(detalle.getIdCampaign());
		insert.setIdServicio(Integer.parseInt(detalle.getNombreServicio()));
		insert.setOrigen(detalle.getOrigen());
		insert.setProcesado(0);
		insert.setRespuestaMailRelay(null);
		if (result.isPresent()) {
			List<Object[]> resultList = result.get();
			if (!resultList.isEmpty()) {
				throw new ExceptionNullSql(new Date(), "Factura existente", detalle.getFactura() + " ya existe");
			} else {
				emailCampaignDetalleDao.save(insert);
			}
		} else {
			emailCampaignDetalleDao.save(insert);
		}
	}

	@Override
	public void update(EmailCampaignDetalleDTO detalle) {
		// TODO Auto-generated method stub
		this.emailCampaignDetalleDao.findById(detalle.getId())
				.map(exist-> {
					emailCampaingDetalleMapper.partialUpdate(exist, detalle);

					return exist;
				})
				.map(emailCampaignDetalleDao::save)
				.map(emailCampaingDetalleMapper::toDto);
	}


	private void invoiceByPortalWeb(Integer idEmailCampaign , EmailCampaign campaign) {
		// TODO Auto-generated method stub
		//EmailCampaign campaign = emailCampaignService.findById(idEmailCampaign);

		String fechaFactura = campaign.getAnno() + campaign.getMes();

		Optional<List<Object[]>> list = emailCampaignDetalleDao.findFacturaByPortalweb(campaign.getIdEmpresa(), fechaFactura);
		List<FacturasEmitidas> emitidas = list
						.map(l -> l.stream()
						.map(data->converterFacturasEmitidas(data, campaign))
						.collect(Collectors.toList()))
						.orElseGet(Collections::emptyList);

		List<FacturasEmitidas> group = emitidas.stream().collect(Collectors.collectingAndThen(
			Collectors.toMap(
				FacturasEmitidas::getFactura,
				factura -> factura,
				(facturaExistent  ,factura) -> facturaExistent
			), map -> new ArrayList<>(map.values())));

		//consultar por el idCampaing si ya existe una lista cargada
		List<FacturasEmitidas> searchMongo = facturasEmitidasService.findByidCampaign(idEmailCampaign);
		if(searchMongo.isEmpty() || searchMongo == null)
		{
			facturasEmitidasService.saveAll(group);

		}else{

			List<FacturasEmitidas> notCoincide = group.stream().filter(data -> searchMongo.stream()
							.noneMatch(mongo -> data.getFactura().equals(mongo.getFactura())
							&& data.getIdCliente().equals(mongo.getIdCliente()))
							).collect(Collectors.toList());

			facturasEmitidasService.saveAll(notCoincide);
		}



	}

	private FacturasEmitidas converterFacturasEmitidas(Object[] object , EmailCampaign campaign)
	{
		FacturasEmitidas emitidas = new FacturasEmitidas();
		emitidas.setFactura((String) object[0].toString());
		emitidas.setIdCliente((Integer) object[1]);
		emitidas.setEmail((String) object[2]);
		emitidas.setNombreServicio((String) object[3]);
		emitidas.setTipoCliente((String) object[4]);
		emitidas.setOrigen((String) object[5]);
		emitidas.setFecha_corte(campaign.getFechaCorte());
		emitidas.setFecha_limite(campaign.getFechaLimitePago());
		emitidas.setIdCampaign(campaign.getId());
		return emitidas;

	}

	private EmailCampaignDetalleDTO converterFacturasToDetalleDTO(FacturasEmitidas facturasEmitidas)
	{
		EmailCampaignDetalleDTO detalle = new EmailCampaignDetalleDTO();
		detalle.setEmail(facturasEmitidas.getEmail());
		detalle.setFactura(facturasEmitidas.getFactura());
		detalle.setTipoCliente(facturasEmitidas.getTipoCliente());
		detalle.setNombreCliente(facturasEmitidas.getNombreCliente());
		detalle.setResponse(facturasEmitidas.getResponse());
		detalle.setIdCliente(facturasEmitidas.getIdCliente());
		detalle.setOrigen(facturasEmitidas.getOrigen());

		return detalle;
	}

	/**
	 * Cuenta el total de detalles de una campaña.
	 * Útil para estadísticas y cálculo de progreso.
	 *
	 * @param campaignId ID de la campaña
	 * @return Cantidad total de detalles
	 */
	@Override
	public long countByCampaign(Long campaignId) {
		if (campaignId == null) {
			return 0;
		}
		return emailCampaignDetalleDao.countByCampaignId(campaignId);
	}

	/**
	 * Cuenta detalles pendientes de envío (estado = 0 o NULL).
	 * Los detalles pendientes son aquellos que aún no han sido procesados.
	 *
	 * @param campaignId ID de la campaña
	 * @return Cantidad de detalles pendientes
	 */
	@Override
	public long countPendingByCampaign(Long campaignId) {
		if (campaignId == null) {
			return 0;
		}
		return emailCampaignDetalleDao.countPendingByCampaignId(campaignId);
	}

	/**
	 * Cuenta detalles enviados exitosamente (estado = 1).
	 *
	 * @param campaignId ID de la campaña
	 * @return Cantidad de detalles enviados
	 */
	@Override
	public long countSentByCampaign(Long campaignId) {
		if (campaignId == null) {
			return 0;
		}
		return emailCampaignDetalleDao.countSentByCampaignId(campaignId);
	}

	/**
	 * Cuenta detalles con error (estado = 2).
	 * Emails que fallaron durante el proceso de envío.
	 *
	 * @param campaignId ID de la campaña
	 * @return Cantidad de detalles con error
	 */
	@Override
	public long countErrorByCampaign(Long campaignId) {
		if (campaignId == null) {
			return 0;
		}
		return emailCampaignDetalleDao.countErrorByCampaignId(campaignId);
	}

	/**
	 * Sobrecarga del método findEmailCampaignDetalleSinProcesar para aceptar Long.
	 * Busca detalles pendientes de procesamiento (estado != 1).
	 *
	 * @param campaignId ID de la campaña (Long)
	 * @return Lista de detalles sin procesar
	 */
	@Override
	public List<EmailCampaignDetalleDTO> findEmailCampaignDetalleSinProcesar(Long campaignId) {
		if (campaignId == null) {
			return Collections.emptyList();
		}
		return findEmailCampaignDetalleSinProcesar(campaignId.intValue());
	}



}
