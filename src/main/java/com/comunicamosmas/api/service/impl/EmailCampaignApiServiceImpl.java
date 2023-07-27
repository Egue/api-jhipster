package com.comunicamosmas.api.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.repository.EmailCampaignApiRepository;
import com.comunicamosmas.api.service.EmailCampaignApiService;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.service.mapper.EmailCampaignApiMapper;

/**
 * Service Implementation for managing {@link EmailCampaignApi}.
 */
@Service
@Transactional
public class EmailCampaignApiServiceImpl implements EmailCampaignApiService {

    private final Logger log = LoggerFactory.getLogger(EmailCampaignApiServiceImpl.class);

    private final EmailCampaignApiRepository emailCampaignApiRepository;

    private final EmailCampaignApiMapper emailCampaignApiMapper;

    public EmailCampaignApiServiceImpl(
        EmailCampaignApiRepository emailCampaignApiRepository,
        EmailCampaignApiMapper emailCampaignApiMapper
    ) {
        this.emailCampaignApiRepository = emailCampaignApiRepository;
        this.emailCampaignApiMapper = emailCampaignApiMapper;
    }

    @Override
    public EmailCampaignApiDTO save(EmailCampaignApiDTO emailCampaignApiDTO) {
        log.debug("Request to save EmailCampaignApi : {}", emailCampaignApiDTO);
        EmailCampaignApi emailCampaignApi = emailCampaignApiMapper.toEntity(emailCampaignApiDTO);
        emailCampaignApi = emailCampaignApiRepository.save(emailCampaignApi);
        return emailCampaignApiMapper.toDto(emailCampaignApi);
    }

    @Override
    public Optional<EmailCampaignApiDTO> partialUpdate(EmailCampaignApiDTO emailCampaignApiDTO) {
        log.debug("Request to partially update EmailCampaignApi : {}", emailCampaignApiDTO);

        return emailCampaignApiRepository
            .findById(emailCampaignApiDTO.getId())
            .map(existingEmailCampaignApi -> {
                emailCampaignApiMapper.partialUpdate(existingEmailCampaignApi, emailCampaignApiDTO);

                return existingEmailCampaignApi;
            })
            .map(emailCampaignApiRepository::save)
            .map(emailCampaignApiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailCampaignApiDTO> findAll() {
        log.debug("Request to get all EmailCampaignApis");
        return emailCampaignApiRepository
            .findAll()
            .stream()
            .map(emailCampaignApiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailCampaignApiDTO> findOne(Long id) {
        log.debug("Request to get EmailCampaignApi : {}", id);
        return emailCampaignApiRepository.findById(id).map(emailCampaignApiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailCampaignApi : {}", id);
        emailCampaignApiRepository.deleteById(id);
    }

    @Override
    public EmailCampaignApi findApiMailRelay(Integer idServicio) {
         
        List<EmailCampaignApiDTO> result =   this.findAll();

        EmailCampaignApi obj = new EmailCampaignApi();

        for(EmailCampaignApiDTO api:result)
        {
            String[] servicios = api.getServicio().split(",");
            if (Arrays.asList(servicios).contains(String.valueOf(idServicio)))
            {
                
                obj.setId(api.getId());
                obj.setMail_envio(api.getMail_envio());
                obj.setNombre_envio(api.getNombre_envio());
                obj.setToken(api.getToken());
                obj.setUrl(api.getUrl());
                break;
            }
        }
        //obj=this.findOneByServiceIdAndStatusActive(idServicio,true
        return obj;
    }
}
