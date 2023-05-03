package com.comunicamosmas.api.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
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

	private void mailRelaySendMail(EmailCampaignApi datos , EmailCampaignDetalle destino , String fondo) {
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
			to.setName("Cliente");
			List<MailRelaySendMail.To> listTo = new ArrayList<MailRelaySendMail.To>();
			listTo.add(to);

			send.setTo(listTo);
			// subject
			send.setSubject("FACTURA ELECTRÓNICA DE VENTA");
			send.setHtml_part(fondo);
			send.setText_part("Factura");
			send.setTxt_part_auto(false);

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
			System.out.print(json);
			//System.out.print(send.toString());
			// se agrega todo en la httpEntity con json y cabeceras
			HttpEntity<String> sendMail = new HttpEntity<>(json, headers);
 
			//se envia la solicitud
			ResponseEntity<String> response = restTemplate.postForEntity(url, sendMail, String.class);

			//System.out.print(response.getBody());

		} catch (HttpMessageConversionException j) {
			System.out.print(j.getMessage());

		} catch (Exception e) {

			System.out.print("servidor error" + e.getMessage());
		}
	}

	@Override
	public void save(EmailCampaignDetalle emailCampaignDetalle) {
		// TODO Auto-generated method stub
		emailCampaignDetalleDao.save(emailCampaignDetalle);
	}

	@Override
	public void findEmailBySend(Integer idEmailCampaign) {
		EmailCampaign campaign = emailCampaignService.findById(idEmailCampaign);
		LocalDate fechaActual = LocalDate.now();
		int anio = fechaActual.getYear();
		Month mes = fechaActual.getMonth();
		String fecha = String.valueOf(anio) + String.valueOf(mes);
		List<Object[]> result = emailCampaignDetalleDao.findEmailBySend(campaign.getIdEmpresa(), "202303");

		for (Object[] rs : result) {
			EmailCampaignDetalle obj = new EmailCampaignDetalle();

			obj.setIdContrato((Integer) rs[0]);
			obj.setIdCliente((Integer) rs[1]);
			obj.setEmail((String) rs[2]);
			obj.setIdServicio((Integer) rs[3]);
			obj.setProcesado(0);
			obj.setIdEmailCampaign(idEmailCampaign);
			this.save(obj);
		}

	}

	@Override
	public List<EmailCampaignDetalleDTO> findByIdEmailCampaing(Long id) {
		List<Object[]> result = emailCampaignDetalleDao.findByIdEmailCampaign(id);
		List<EmailCampaignDetalleDTO> email = new ArrayList<EmailCampaignDetalleDTO>();

		for (Object[] rs : result) {
			EmailCampaignDetalleDTO obj = new EmailCampaignDetalleDTO();

			obj.setIdContrato((Integer) rs[0]);
			obj.setIdCliente((Integer) rs[1]);
			obj.setEmail((String) rs[2]);
			obj.setResponse((String) rs[3]);
			obj.setProcesado((String) rs[4]);
			obj.setNombreServicio((String) rs[5]);
			obj.setTipoCliente((String) rs[6]);
			obj.setNombreCliente((String) rs[7]);
			obj.setId((Integer) rs[8]);
			email.add(obj);
		}
		return email;
	}

	@Override
	public void sendMail(Integer id) {

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
					System.out.print("unitarios: "+unitario.getIdServicio()+ "service : " +  service);
						
					
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
		String fondo = this.fondoMail(unitario.getIdContrato() , datos);
		//System.out.print(fondo);
		this.mailRelaySendMail(datos , unitario , fondo);

	}

	@Override
	public EmailCampaignDetalle findById(Integer id) {
		// TODO Auto-generated method stub
		return emailCampaignDetalleDao.findById(id).orElse(null);
	}
	
	/**@param idContrato el contrato del la factura
	 * @param api el objecto de la informacion del html_part
	 * */	
	 private String fondoMail(Integer idContrato ,  EmailCampaignApi api)
	{
		 String[] html_part = api.getHtml_part().split("@");
		 //System.out.print(html_part[0]);
		 Long id = new Long(idContrato);
		  
		 Empresa empresa = empresaService.findByIdContrato(id);
		 //buscar nombre del pariente 
		 Cliente cliente = clienteService.getClientByIdContrato(id);
		 String nombreCliente = "";
		 if(cliente.getTipoCliente().equals("J"))
		 {
			 nombreCliente = cliente.getRazonSocial();
			 
		 }else {
			 
			 nombreCliente = cliente.getNombrePrimer()+" "+cliente.getApellidoPaterno()+' '+cliente.getApellidoMaterno();
		 }
		 //ultimo dia del mes
		 LocalDate fecha = LocalDate.now();
		 int year = fecha.getYear();
		 int month = fecha.getMonthValue();
		 LocalDate ultimoDiaDelMes = fecha.withDayOfMonth(fecha.lengthOfMonth());
		 String fechaConcatenada = ultimoDiaDelMes.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
		 //valor a cancelar
		 String deuda = deudaService.findDeudaByIdContrato(id);
		 
		 String message = html_part[0]+empresa.getLogoPublico()+html_part[1]+nombreCliente+html_part[2]+fechaConcatenada+html_part[3]+deuda+html_part[4]+year+
				 html_part[5]+"Version: 2.0"+html_part[6];
		 //System.out.print(message);
		 return message;
		//return null;
	}

}
