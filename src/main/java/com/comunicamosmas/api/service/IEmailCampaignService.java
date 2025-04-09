package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.service.dto.EmailCampanignDTO;

public interface IEmailCampaignService {

	public void save(EmailCampaign emailCampaign);
	
	public EmailCampaign findById(Integer id);
	
	public EmailCampanignDTO findByIdDTO(Integer id);
	
	public List<EmailCampaign> findAll();
	
	public void deleteById(Integer id);
	
	public List<EmailCampanignDTO> findAllEmailCampaign();

	public EmailCampaign findEmailCampaignLimitOne();

	public List<EmailCampanignDTO> filterEmailCampaign(Long idEmpresa , String fecha);

	public List<EmailCampaignDetalle> findByMesAndAnio(int mes, int anio);
	 
}
