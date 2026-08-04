package com.comunicamosmas.api.service;

import com.comunicamosmas.api.config.HablameProperties;
import com.comunicamosmas.api.service.dto.ClassHablameDTO;
import com.comunicamosmas.api.service.dto.ClassResponseHablameDTO;

import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class HablameServiceImpl implements IHablameService {

    private final HablameProperties hablameProperties;

    public HablameServiceImpl(HablameProperties hablameProperties) {
        this.hablameProperties = hablameProperties;
    }

    @Override
    public ClassResponseHablameDTO msmPriority(ClassHablameDTO classHablame) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("account", hablameProperties.getAccount());
        headers.add("apikey", hablameProperties.getApikey());
        headers.add("token", hablameProperties.getToken());

        new HttpEntity<ClassHablameDTO>(classHablame, headers);
        return null;
    }
}
