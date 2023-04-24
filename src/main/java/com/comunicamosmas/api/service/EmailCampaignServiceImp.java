package com.comunicamosmas.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.repository.IEmailCampanignDao;
import com.comunicamosmas.api.service.dto.EmailCampanignDTO;

@Service
public class EmailCampaignServiceImp implements IEmailCampaignService{

	@Autowired
	IEmailCampanignDao emailCampaignDao;
	
	@Override
	public void save(EmailCampaign emailCampaign) {
		// TODO Auto-generated method stub
		emailCampaignDao.save(emailCampaign);
	}

	@Override
	public EmailCampanignDTO findByIdDTO(Integer id) {
		// TODO Auto-generated method stub
		List<Object[]> result = emailCampaignDao.findByIdDTO(id);
		
		//EmailCampanignDTO email = new EmailCampanignDTO();
		EmailCampanignDTO obj = new EmailCampanignDTO();
		
		for(Object[] rs: result)
		{
			
			
			obj.setId((Integer) rs[0]);
			obj.setAnno((Integer) rs[1]);
			obj.setFecha((Date) rs[2]);
			obj.setFechaLimitePago((String) rs[3]);
			obj.setMes((Integer) rs[4]);
			obj.setNombre((String) rs[5]);
			obj.setNombreComercial((String) rs[6]);
			
			 
		}
		
		return obj;
	}

	@Override
	public List<EmailCampaign> findAll() {
		// TODO Auto-generated method stub
		return (List<EmailCampaign>)emailCampaignDao.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		emailCampaignDao.deleteById(id);
	}

	@Override
	public List<EmailCampanignDTO> findAllEmailCampaign() {
		// TODO Auto-generated method stub
		List<Object[]> result = emailCampaignDao.findAllEmailCampaign();
		List<EmailCampanignDTO> email = new ArrayList<EmailCampanignDTO>();
		for(Object[] rs: result)
		{
			EmailCampanignDTO obj = new EmailCampanignDTO();
			obj.setId((Integer) rs[0]);
			obj.setAnno((Integer) rs[1]);
			obj.setFecha((Date) rs[2]);
			obj.setFechaLimitePago((String) rs[3]);
			obj.setMes((Integer) rs[4]);
			obj.setNombre((String) rs[5]);
			obj.setNombreComercial((String) rs[6]);
			
			email.add(obj);
		}
		
		return email;
	}

	@Override
	public EmailCampaign findById(Integer id) {
		// TODO Auto-generated method stub
		return emailCampaignDao.findById(id).orElse(null);
	}

}
