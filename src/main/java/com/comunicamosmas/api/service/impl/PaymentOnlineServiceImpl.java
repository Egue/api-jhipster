package com.comunicamosmas.api.service.impl;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.service.IPaymentOnlineService;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentOnlineServiceImpl implements IPaymentOnlineService{

    private final ISystemConfigService systemConfigService;

    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    public PaymentOnlineServiceImpl(ISystemConfigService  systemConfigService){
        this.systemConfigService = systemConfigService;
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
    }

    @Override
    public void downloadPaymentOnline() {
        // TODO Auto-generated method stub
        SystemConfig dataOnline = systemConfigService.findByOrigen("payment_online");
        
        Auth auth = convertAuth(dataOnline.getComando());

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-login", auth.getLogin());

        URI uri = UriComponentsBuilder.fromHttpUrl(auth.getUrl())
                .queryParam("token", auth.getToken())
                .build()
                .toUri();
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class);
            if(response.getStatusCode().equals(200))
            {
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        


    }

    private PaymentOnlineDTO.Auth convertAuth(String dataOnline)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            PaymentOnlineDTO.Auth auth = objectMapper.readValue(dataOnline, Auth.class);

            return auth;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            return null;
        }

    }
    
}
