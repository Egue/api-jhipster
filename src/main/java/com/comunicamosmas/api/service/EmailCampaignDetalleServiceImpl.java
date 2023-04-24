package com.comunicamosmas.api.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailCampaignDetalleServiceImpl implements IEmailCampaignDetalleService {

	@Autowired
	IEmailCampaignDetalleDao emailCampaignDetalleDao;

	@Autowired
	IEmailCampaignService emailCampaignService;

	private void mailRelaySendMail() {
		try {
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();

			MailRelaySendMail mailRelay = new MailRelaySendMail();
			// Mail json
			MailRelaySendMail.Send send = mailRelay.new Send();
			// From
			MailRelaySendMail.From from = mailRelay.new From();
			from.setEmail("no-reply@comunicamosmas.com");
			from.setName("Comunicamosmas telecomunicaciones");

			send.setFrom(from);

			// To
			MailRelaySendMail.To to = mailRelay.new To();
			to.setEmail("web@internetinalambrico.com.co");
			to.setName("Edwin Egue");
			List<MailRelaySendMail.To> listTo = new ArrayList<MailRelaySendMail.To>();
			listTo.add(to);

			send.setTo(listTo);
			// subject
			send.setSubject("FACTURA ELECTRÓNICA DE VENTA");
			send.setHtml_part(
					"<html>\n<head></head>\n<body><p>Hola Ing buenos días, enviando desde mailrealy jejeej.</p></body>\n</html>\n");
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
			String url = "https://comunicamosmas.ipzmarketing.com/api/v1/send_emails";
			// se envia xtoken con codigo
			headers.set("X-AUTH-TOKEN", "8o57qifCbU9bskncG1FFbaPexUaEb_cyR2PWaSBv");
			// se instancia clase objectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			// la clase mailRelaysendmail se pasa a json
			String json = objectMapper.writeValueAsString(send);
			
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

		this.mailRelaySendMail();

	}

}
