package com.comunicamosmas.api.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;
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

	/**Enviando mail a al cliente por mailrelay api
	 * @param datos del api de mailrelay
	 * @param destino detalle de la campaña 1 a 1 
	 * @param fondo
	 * @param responseGeneracionFactura informacion de la generacion de factura
	 */
	private String mailRelaySendMail(EmailCampaignApi datos , EmailCampaignDetalle destino , String fondo , RespuestaGeneracionPDFFactura responseGeneracionFactura ) {
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
			String subject = responseGeneracionFactura.getNit()+";"+responseGeneracionFactura.getRazon_social()+";"+responseGeneracionFactura.getPrefijo()+responseGeneracionFactura.getFactura()+";"+responseGeneracionFactura.getCodigoDocumento()+";"+responseGeneracionFactura.getNameComercial();
			send.setSubject(subject);
			send.setHtml_part(fondo);
			send.setText_part("Factura");
			send.setTxt_part_auto(false);
			//Attachment
			MailRelaySendMail.Attachment attachment = mailRelay.new Attachment();
			List<MailRelaySendMail.Attachment> listAttachments = new ArrayList<MailRelaySendMail.Attachment>();
			//codificar en base 64
			String contentCodificadoBase64 = zipCreatorService.codificarBase64(responseGeneracionFactura.getPathZIP());

			attachment.setContent(contentCodificadoBase64);
			//scar nombre del archivo
			Path filePath = Paths.get(responseGeneracionFactura.getPathZIP());
			
			attachment.setFile_name(filePath.getFileName().toString());
			attachment.setContent_type("application/zip");
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
			String url = datos.getUrl()+"send_emails";
			// se envia xtoken con codigo
			headers.set("X-AUTH-TOKEN", datos.getToken());
			// se instancia clase objectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			// la clase mailRelaysendmail se pasa a json
			String json = objectMapper.writeValueAsString(send);
			//System.out.print(json);
			//System.out.print(send.toString());
			// se agrega todo en la httpEntity con json y cabeceras
			HttpEntity<String> sendMail = new HttpEntity<>(json, headers);
			//System.out.println(sendMail);
			//se envia la solicitud
			ResponseEntity<String> response = restTemplate.postForEntity(url, sendMail, String.class);

			//System.out.print(response.getBody());
			String respuestaFinal = response.getBody();

			destino.setRespuestaMailRelay(respuestaFinal);
			destino.setProcesado(1);
			this.save(destino);

			return respuestaFinal;
		} catch (HttpMessageConversionException j) {
			//System.out.print(j.getMessage());
			destino.setRespuestaMailRelay(j.getMessage());
			this.save(destino);
			throw new ExceptionNullSql(new Date(), "Error enviando a MailRelay", j.getMessage());

		} catch (Exception e) {

			//System.out.print("servidor error" + e.getMessage());
			destino.setRespuestaMailRelay(e.getMessage());
			this.save(destino);

			throw new ExceptionNullSql(new Date(), "Error Inesperado con MailRelay", e.getMessage() );
		}
	}

	/**guarda el detalle de la campaña */
	@Override
	public void save(EmailCampaignDetalle emailCampaignDetalle) {
		// TODO Auto-generated method stub
		emailCampaignDetalleDao.save(emailCampaignDetalle);
	}

	/**
	 * buscar los contratos 
	 * @param idEmailCampaign 
	 * id de la campaña para guardar en el detalle
	 * @param fecha
	 * carga la fecha de las facturas generadas a los contratos
	 * hace un recorrido de los contratos con preferencia_factura = 2
	 */
	@Override
	public void findEmailBySend(Integer idEmailCampaign) {
		EmailCampaign campaign = emailCampaignService.findById(idEmailCampaign);
		/*LocalDate fechaActual = LocalDate.now();
		int anio = fechaActual.getYear();
		Month mes = fechaActual.getMonth();
		String fecha = String.valueOf(anio) + String.valueOf(mes);*/
		
		String fechaFactura = campaign.getAnno()+campaign.getMes();
		List<Object[]> result = emailCampaignDetalleDao.findEmailBySend(campaign.getIdEmpresa(), fechaFactura);

		if(result != null)
		{
			for (Object[] rs : result) {
				EmailCampaignDetalle obj = new EmailCampaignDetalle();
				 
				obj.setFactura((String) rs[0].toString());
				obj.setIdCliente((Integer) rs[1]);
				obj.setEmail((String) rs[2]);
				obj.setIdServicio((Integer) rs[3]);
				obj.setProcesado(0);
				obj.setIdEmailCampaign(idEmailCampaign);
				this.save(obj);
			}
		}

	}
	/**
	 * @param Long id
	 * id de la campaña principal
	 * Busca el detalle de los contratos para enviar en la campaña
	 */

	@Override
	public List<EmailCampaignDetalleDTO> findByIdEmailCampaing(Long id) {
		List<Object[]> result = emailCampaignDetalleDao.findByIdEmailCampaign(id);
		List<EmailCampaignDetalleDTO> email = new ArrayList<EmailCampaignDetalleDTO>();

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
		return email;
	}


	/**
	 * @param Integer id
	 * Id del detalle de la campaña detalle, para realizar el recorrido e iniciar a enviar
	 */
	 
	private String sendMail(Integer id , RespuestaGeneracionPDFFactura responsePDF) {

		//consultamos el id de emailcampaigndetalle
		EmailCampaignDetalle unitario = this.findById(id);
		 
		String unitarioIdService = Integer.toString(unitario.getIdServicio());
		//buscar los datos de sesión en emailCampaignApi
		EmailCampaignApi datos = new EmailCampaignApi();
		
		List<EmailCampaignApiDTO> api = emailCampaignApiService.findAll();
		
		outerloop:
			for(EmailCampaignApiDTO rs : api)
			{
				String[] idServicio = rs.getServicio().split(",");
				
				for(String service : idServicio)
				{
					//System.out.print("unitarios: "+unitario.getIdServicio()+ "service : " +  service);
						 
					if(unitarioIdService.equals(service))
					{
						datos.setToken(rs.getToken());
						datos.setUrl(rs.getUrl());
						datos.setMail_envio(rs.getMail_envio());
						datos.setNombre_envio(rs.getNombre_envio());
						datos.setHtml_part(rs.getHtml_part());
						break outerloop;
					}
				}
			}
		String fondo = this.fondoMail(responsePDF , datos);
		//System.out.print(fondo);
		return this.mailRelaySendMail(datos , unitario , fondo , responsePDF);

	}

	/**
	 * @param Integer id
	 * buscar detalla de la campaña 1 registro buscar por idcamañadetalle
	 */
	@Override
	public EmailCampaignDetalle findById(Integer id) {
		// TODO Auto-generated method stub
		return emailCampaignDetalleDao.findById(id).orElse(null);
	}
	
	/**@param idContrato el contrato del la factura
	 * @param api el objecto de la informacion del html_part
	 * */	
	 private String fondoMail( RespuestaGeneracionPDFFactura response,     EmailCampaignApi api)
	{
		 
		if(api.getHtml_part().isEmpty())
		{
			  throw new ExceptionNullSql(new Date() , "Campo nullo" , "no se encontro información para fondo");
		}
		
		String[] html_part = api.getHtml_part().split("@");
		 //System.out.print(html_part[0]);
		 /*Long id = Integer.toUnsignedLong(idContrato);
		  
		 Empresa empresa = empresaService.findByIdContrato(id);
		 //buscar nombre del pariente 
		 /*Cliente cliente = clienteService.getClientByIdContrato(id);
		 String nombreCliente = "";
		 if(cliente.getTipoCliente().equals("J"))
		 {
			 nombreCliente = cliente.getRazonSocial();
			 
		 }else {
			 
			 nombreCliente = cliente.getNombrePrimer()+" "+cliente.getApellidoPaterno()+' '+cliente.getApellidoMaterno();
		 }*/
		 //ultimo dia del mes
		 LocalDate fecha = LocalDate.now();
		 int year = fecha.getYear();
		 int month = fecha.getMonthValue();
		 LocalDate ultimoDiaDelMes = fecha.withDayOfMonth(fecha.lengthOfMonth());
		 String fechaConcatenada = ultimoDiaDelMes.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
		 //valor a cancelar
		// String deuda = deudaService.findDeudaByIdContrato(id);
		 
		 String message = html_part[0]+response.getLogoPublico()+html_part[1]+response.getDestinatario()+html_part[2]+fechaConcatenada+html_part[3]+response.getValorPagar()+html_part[4]+year+
				 html_part[5]+"Version: 2.0"+html_part[6];
		 //System.out.print(message);
		 return message;
		//return null;
	}

	 /**
	  * @param EmailCampaignDetalle.get id*/
	@Override
	public String sendMailUnitario(EmailCampaignDetalleDTO detalle) {
		// TODO Auto-generated method stub
		
		//System.out.print(detalle.getEmail());
		//generar factura
		//buscar facturas y enviar a crear pdf
		try{
		EmailCampaign campaña = emailCampaignService.findById(detalle.getIdCampaign());
		//realizamos la busqueda de la factura y sus componentes
		String fecha = campaña.getAnno()+campaña.getMes();
		
		String primeroscaracteres = fecha.substring(0 , 6);
		
		Long mesServicio =  Long.parseLong(primeroscaracteres);
		
		
		List<DeudasForFacturaDTO> deudas = deudaService.findDeudaByFacturaAndMesServiceAndIdEmpresa(Long.parseLong(detalle.getFactura()), mesServicio, campaña.getIdEmpresa());
		
		 
		RespuestaGeneracionPDFFactura generacionPDF =  generatePDFService.generateFacturaPDF(detalle , deudas , campaña);

		String pathZip = zipCreatorService.zipFileFactura(generacionPDF);
			
		generacionPDF.setPathZIP(pathZip);
			
		String send = this.sendMail(detalle.getId() , generacionPDF);

		return send;
		 

		}catch(Exception e)
		{
			throw new ExceptionNullSql(new Date() , "Error generando el envio" , e.getMessage());

		}
		
	}

	@Override
	public List<EmailCampaignDetalleDTO> findEmailCampaignDetalleSinProcesar(Integer idEmailCampaign) {
		
		List<Object[]> result = emailCampaignDetalleDao.findEmailCampaignDetalleSinProcesar(idEmailCampaign);

		List<EmailCampaignDetalleDTO> email = new ArrayList<EmailCampaignDetalleDTO>();

		if(result != null && !result.isEmpty())
		{
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

	 
	
 
	 
}
