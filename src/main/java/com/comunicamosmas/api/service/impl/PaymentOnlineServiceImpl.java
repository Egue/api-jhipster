package com.comunicamosmas.api.service.impl;

import java.net.URI;
import java.util.List;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.service.IPagoService;
import com.comunicamosmas.api.service.IPaymentOnlineService;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO; 
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO.Auth;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO.ListPagos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentOnlineServiceImpl implements IPaymentOnlineService{

    private final ISystemConfigService systemConfigService;

    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    private final IPagoService pagoService;

    private Auth auth;

    public PaymentOnlineServiceImpl(ISystemConfigService  systemConfigService , IPagoService pagoService){
        this.systemConfigService = systemConfigService;
        this.pagoService = pagoService;
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        
    }

    @Override
    public void downloadPaymentOnline() {
        // TODO Auto-generated method stub
        SystemConfig systemConfig = systemConfigService.findByOrigen("payment_online");
        
        this.auth = convertAuth(systemConfig.getComando());

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
            //System.out.println(response.getStatusCode());
            if(response.getStatusCode().equals(HttpStatus.OK))
            {
                
                if(response.getBody() != "null")
                {
                    ObjectMapper objectMapper = new ObjectMapper();

                    List<PaymentOnlineDTO.PagosOnline> pagos = objectMapper.readValue(response.getBody(), new TypeReference<List<PaymentOnlineDTO.PagosOnline>>(){});

                    iterarPSE(pagos);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }
        


    }

    private void iterarPSE(List<PaymentOnlineDTO.PagosOnline> pagos)
    {
         pagos.stream().forEach(s -> {
            
            s.getFacturas().stream().forEach(f -> {
                this.pagoService.registerPagosOnline(s.getReference(),   f);
                this.udpatePagoByReference(s.getReference());
            });

         });
    }

    private void udpatePagoByReference(String reference)
    {
        URI uri = UriComponentsBuilder.fromHttpUrl(auth.getUrl_download())
                .queryParam("token", auth.getToken())
                .queryParam("reference", reference)
                .build()
                .toUri();
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        try {

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT,entity,String.class);
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
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

    @Override
    public List<ListPagos> findListPaymentOnlyBetwenn(String dateOne, String dateTwo) {
        // TODO Auto-generated method stub
        SystemConfig systemConfig = systemConfigService.findByOrigen("payment_online");
        
        this.auth = convertAuth(systemConfig.getComando());

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-login", auth.getLogin());

        URI uri = UriComponentsBuilder.fromHttpUrl(auth.getUrl_list())
                .queryParam("firts", dateOne)
                .queryParam("final", dateTwo)
                .build()
                .toUri();
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class);

            if(response.getStatusCode().equals(HttpStatus.OK))
            {
                ObjectMapper objectMapper = new ObjectMapper();

                 List<PaymentOnlineDTO.ListPagos> pagos = objectMapper.readValue(response.getBody(), new TypeReference<List<PaymentOnlineDTO.ListPagos>>(){});

                 return pagos;
             }

             return null;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            return null;

        }

    }
    
}
