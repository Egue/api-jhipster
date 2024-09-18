package com.comunicamosmas.api.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; 

import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.ISendWSService; 
import com.comunicamosmas.api.service.dto.SendWSDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@Service
public class SendWSServiceImpl implements ISendWSService{


    private final ISystemConfigDao systemConfigDao;

     

    public SendWSServiceImpl(ISystemConfigDao systemConfigDao  )
    {
        this.systemConfigDao = systemConfigDao;
         
    }

    @Override
    public void sendMsmPriority(String code, String phone) {
        // TODO Auto-generated method stub
        String comando = systemConfigDao
                            .findByOrigen("send-ws")
                            .getComando();
        if(!comando.isEmpty())
        {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                SendWSDTO.AccessKey accessKey = objectMapper.readValue(comando, new TypeReference<SendWSDTO.AccessKey>() {});
                SendWSDTO.MsmPriority meMsmPriority = new SendWSDTO.MsmPriority();

                meMsmPriority.setAccount(accessKey.getAccount());
                meMsmPriority.setApiKey(accessKey.getApiKey());
                meMsmPriority.setFlash("1");
                meMsmPriority.setSms("Código de confirmación INCONNECTION: " + code);
                meMsmPriority.setToNumber(phone);
                meMsmPriority.setToken(accessKey.getToken());

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders header = new HttpHeaders();

                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                header.setContentType(MediaType.APPLICATION_JSON);

                String json = objectMapper.writeValueAsString(meMsmPriority);

                HttpEntity<String> sendMsm = new HttpEntity<>(json , header);

                ResponseEntity<String> response = restTemplate.postForEntity(accessKey.getUrl(), sendMsm, String.class);

                if(response.getStatusCode() != HttpStatus.CREATED)
                {   
                    throw new IllegalArgumentException("Número de teléfono inválido");
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }
    
}
