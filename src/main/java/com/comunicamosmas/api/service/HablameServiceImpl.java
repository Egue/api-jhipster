package com.comunicamosmas.api.service;

//import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.web.rest.vm.ClassHablame;
import com.comunicamosmas.api.web.rest.vm.ClassResponseHablame;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class HablameServiceImpl implements IHablameService {

    /*@Autowired
	RestTemplate restTemplate;*/

    @Override
    public ClassResponseHablame msmPriority(ClassHablame classHablame) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("account", "10012812");
        headers.add("apikey", "QuLTfSAmzLVsGpH8YMSnww0byK3e2b");
        headers.add("token", "174f24c10cf4900c0edfe98ac9b976e1");

        HttpEntity<ClassHablame> enviar = new HttpEntity<ClassHablame>(classHablame, headers);
        //ClassResponseHablame  result = restTemplate.exchange("https://api103.hablame.co/api/sms/v3/send/priority", HttpMethod.POST, enviar, ClassResponseHablame.class ).getBody();
        return null;
    }
}
