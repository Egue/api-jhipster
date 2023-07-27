package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.service.IMailRelayService;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MailRelayServiceImpl implements IMailRelayService{

    @Override
    public String mailRelaySend( MailRelaySendMail.Send mailRelaySendMail , String token , String url) {
        try {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // application/json
			headers.setContentType(MediaType.APPLICATION_JSON);
			// url
			//String url = datos.getUrl()+"send_emails";
			// se envia xtoken con codigo
			headers.set("X-AUTH-TOKEN", token);
            // se instancia clase objectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			// la clase mailRelaysendmail se pasa a json
			String json = objectMapper.writeValueAsString(mailRelaySendMail);
            // se agrega todo en la httpEntity con json y cabeceras
			HttpEntity<String> sendMail = new HttpEntity<>(json, headers);
            //se envia la solicitud
		    
            ResponseEntity<String> response =  restTemplate.postForEntity(url, sendMail, String.class);

            String respuestaFinal = response.getBody();
           
            return respuestaFinal;

        }catch(HttpMessageConversionException e){

            throw new ExceptionNullSql(new Date(), "Error enviando a MailRelay", e.getMessage());

        } catch (Exception e) {

            throw new ExceptionNullSql(new Date(), "Error Inesperado con MailRelay", e.getMessage() );
        }
    }
    
}
