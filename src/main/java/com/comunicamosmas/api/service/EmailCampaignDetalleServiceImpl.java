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
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
		try {
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

			// To
			MailRelaySendMail.To to = mailRelay.new To();
			to.setEmail(destino.getEmail());
			to.setName(responseGeneracionFactura.getDestinatario());
			List<MailRelaySendMail.To> listTo = new ArrayList<MailRelaySendMail.To>();
			listTo.add(to);

			send.setTo(listTo);
			// subject
			String subject = responseGeneracionFactura.getNit() + ";" + responseGeneracionFactura.getRazon_social()
					+ ";" + responseGeneracionFactura.getPrefijo() + responseGeneracionFactura.getFactura() + ";"
					+ responseGeneracionFactura.getCodigoDocumento() + ";"
					+ responseGeneracionFactura.getNameComercial();
			send.setSubject(subject);
			send.setHtml_part(fondo);
			send.setText_part("Factura");
			send.setTxt_part_auto(false);
			// Attachment
			MailRelaySendMail.Attachment attachment = mailRelay.new Attachment();
			List<MailRelaySendMail.Attachment> listAttachments = new ArrayList<MailRelaySendMail.Attachment>();
			// codificar en base 64
			String pathConverter = destino.getOrigen().equals("A") ? responseGeneracionFactura.getPathZIP() : responseGeneracionFactura.getPathPDF();
			String contentCodificadoBase64 = zipCreatorService.codificarBase64(pathConverter);

			attachment.setContent(contentCodificadoBase64);
			// scar nombre del archivo
			Path filePath = destino.getOrigen().equals("A") ? Paths.get(responseGeneracionFactura.getPathZIP()) : Paths.get(responseGeneracionFactura.getPathPDF()) ;

			attachment.setFile_name(filePath.getFileName().toString());
			String typeFile = destino.getOrigen().equals("A") ? "application/zip" : "application/pdf";
			attachment.setContent_type(typeFile);
			attachment.setContent_id("");
			listAttachments.add(attachment);

			send.setAttachments(listAttachments);
			// headers
			MailRelaySendMail.Headers header = mailRelay.new Headers();
			header.setCustomHeader("Header value");

			send.setHeaders(header);

			//
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			// application/json
			headers.setContentType(MediaType.APPLICATION_JSON);
			// url
			String url = datos.getUrl() + "send_emails";
			// se envia xtoken con codigo
			headers.set("X-AUTH-TOKEN", datos.getToken());
			// se instancia clase objectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			// la clase mailRelaysendmail se pasa a json
			String json = objectMapper.writeValueAsString(send);
			// System.out.print(json);
			// System.out.print(send.toString());
			// se agrega todo en la httpEntity con json y cabeceras
			HttpEntity<String> sendMail = new HttpEntity<>(json, headers);
			// System.out.println(sendMail);
			// se envia la solicitud
			ResponseEntity<String> response = restTemplate.postForEntity(url, sendMail, String.class);

			// System.out.print(response.getBody());
			String respuestaFinal = response.getBody();

			destino.setRespuestaMailRelay(respuestaFinal);
			destino.setProcesado(1);
			String urlDocument = destino.getOrigen().equals("A") ? responseGeneracionFactura.getPathZIP() : responseGeneracionFactura.getPathPDF();
			File file = new File(urlDocument);
			destino.setNameDocument(file.getName());
			this.save(destino);

			return respuestaFinal;
		} catch (HttpMessageConversionException j) {
			// System.out.print(j.getMessage());
			destino.setRespuestaMailRelay(j.getMessage());
			this.save(destino);
			throw new ExceptionNullSql(new Date(), "Error enviando a MailRelay", j.getMessage());

		} catch (Exception e) {

			// System.out.print("servidor error" + e.getMessage());
			destino.setRespuestaMailRelay(e.getMessage());
			this.save(destino);

			throw new ExceptionNullSql(new Date(), "Error Inesperado con MailRelay", e.getMessage());
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
		int month = fecha.getMonthValue();
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
		// TODO Auto-generated method stub
		//System.out.println(detalle.getEmail());
		//update(detalle);
		// System.out.print(detalle.getEmail());
		// generar factura
		// buscar facturas y enviar a crear pdf
		try {
			EmailCampaign campaña = emailCampaignService.findById(detalle.getIdCampaign());
			 
			// realizamos la busqueda de la factura y sus componentes
			String fecha = campaña.getAnno() + campaña.getMes();

			String primeroscaracteres = fecha.substring(0, 6);

			Long mesServicio = Long.parseLong(primeroscaracteres);
			/*
			 * consultamos las deudas de acuerdo a la factura
			 * @param factura
			 * @Param messervicio esta va a concatenar facturado_mes
			 * @param idCliente
			 * @Param Origen A o B
			 */
			List<DeudasForFacturaDTO> deudas = deudaService.findDeudaByFacturaAndMesServiceAndIdEmpresa(
					Long.parseLong(detalle.getFactura()), mesServicio, campaña.getIdEmpresa(), detalle.getIdCliente() , detalle.getOrigen());
			/*
			 * Generar el PDF de la factura con las deudas consultadas
			 */
			RespuestaGeneracionPDFFactura generacionPDF = generatePDFService.generateFacturaPDF(detalle, deudas,
					campaña);

			if(detalle.getOrigen().equals("A"))
			{
				String pathZip = zipCreatorService.zipFileFactura(generacionPDF , detalle);

				generacionPDF.setPathZIP(pathZip);

				//eliminar pdf y zip
				File pdf = new File(generacionPDF.getPathPDF());
				File xml = new File(generacionPDF.getPathXML());
				if(pdf.exists()){pdf.delete();}
				if(xml.exists()){xml.delete();}
			} 

			String send = this.sendMail(detalle.getId(), generacionPDF);

			return send;

		} catch (Exception e) {
			throw new ExceptionNullSql(new Date(), "Error generando el envio", e.getMessage());

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

	 

}
