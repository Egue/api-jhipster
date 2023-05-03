package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;

public interface IEmailCampaignDetalleService {
	
	public void save(EmailCampaignDetalle emailCampaignDetalle);
	
	public void findEmailBySend(Integer idEmailCampaign);
	
	public List<EmailCampaignDetalleDTO> findByIdEmailCampaing(Long id);
	
	public EmailCampaignDetalle findById(Integer id);
	
	public void sendMail(Integer id);
	
	

}
