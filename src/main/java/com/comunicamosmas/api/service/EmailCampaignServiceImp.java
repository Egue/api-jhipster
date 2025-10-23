package com.comunicamosmas.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.repository.IEmailCampanignDao;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.service.dto.EmailCampanignDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

@Service
public class EmailCampaignServiceImp implements IEmailCampaignService{

	@Autowired
	IEmailCampanignDao emailCampaignDao;

	@Autowired
	IEmailCampaignDetalleDao emailCampaignDetalleDao;
	
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
			obj.setAnno((String) rs[1]);
			obj.setFecha((Date) rs[2]);
			obj.setFechaLimitePago((String) rs[3]);
			obj.setMes((String) rs[4]);
			obj.setNombre((String) rs[5]);
			obj.setNombreComercial((String) rs[6]);
			obj.setFechaCorte((String)rs[7]);
			obj.setEstado((String) rs[8]);
			 
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
		try{
			for(Object[] rs: result)
		{
			EmailCampanignDTO obj = new EmailCampanignDTO();
			obj.setId((Integer) rs[0]);
			obj.setAnno((String) rs[1]);
			obj.setFecha((Date) rs[2]);
			obj.setFechaLimitePago((String) rs[3]);
			obj.setMes((String) rs[4]);
			obj.setNombre((String) rs[5]);
			obj.setNombreComercial((String) rs[6]);
			obj.setFechaCorte((String) rs[7]);
			obj.setEstado((String) rs[8]);
			email.add(obj);
		}
		}catch(Exception e)
		{
			throw new ExceptionNullSql(new Date(), "Email Campaign", "Sin resultados");
		}
		
		return email;
	}

	@Override
	public EmailCampaign findById(Integer id) {
		// TODO Auto-generated method stub
		return emailCampaignDao.findById(id).orElse(null);
	}

	@Override
	public EmailCampaign findEmailCampaignLimitOne() {
		
		return emailCampaignDao.findCampaignLimitOne();
	}

	@Override
	public List<EmailCampanignDTO> filterEmailCampaign(Long idEmpresa, String fecha) {
		// TODO Auto-generated method stub
		 List<Object[]> result = emailCampaignDao.filterEmailCampaign(idEmpresa , fecha);
		 List<EmailCampanignDTO> email = new ArrayList<EmailCampanignDTO>();
		 try{
			 for(Object[] rs: result)
		 {
			 EmailCampanignDTO obj = new EmailCampanignDTO();
			 obj.setId((Integer) rs[0]);
			 obj.setAnno((String) rs[1]);
			 obj.setFecha((Date) rs[2]);
			 obj.setFechaLimitePago((String) rs[3]);
			 obj.setMes((String) rs[4]);
			 obj.setNombre((String) rs[5]);
			 obj.setNombreComercial((String) rs[6]);
			 obj.setFechaCorte((String) rs[7]);
			 obj.setEstado((String) rs[8]);
			 email.add(obj);
		 }
		 }catch(Exception e)
		 {
			 throw new ExceptionNullSql(new Date(), "Email Campaign", "Sin resultados");
		 }
		 
		 return email;
	}

	
	@Override
	public List<EmailCampaignDetalle> findByMesAndAnio(int mes, int anio) {
	    // Obtener las campañas por mes y año
	    List<EmailCampaign> campaigns = emailCampaignDao.findByMesAndAnio(mes, anio);

	    // Lista para almacenar los detalles de las campañas
	    List<EmailCampaignDetalle> detalles = new ArrayList<>();

	    // Iterar sobre las campañas y obtener sus detalles
	    for (EmailCampaign campaign : campaigns) {
	        List<EmailCampaignDetalle> detallesCampaña = emailCampaignDetalleDao.findDetallesByIdCampaign(campaign.getId());
	        detalles.addAll(detallesCampaña);
	    }

	    return detalles;
	}

	/**
	 * Implementación de findActiveCampaignsForBatch
	 * Busca campañas activas excluyendo estados no procesables por batch.
	 * 
	 * Estados excluidos:
	 * - PortalWeb: Se procesan de forma diferente (MongoDB)
	 * - Finalizado: Ya no requieren procesamiento
	 * - Inactivo: Campañas deshabilitadas
	 * 
	 * @return Lista de campañas activas listas para procesamiento batch
	 */
	@Override
	public List<EmailCampaign> findActiveCampaignsForBatch() {
		// Lista de estados que NO deben procesarse en batch
		List<String> excludedStates = new ArrayList<>();
		excludedStates.add("PortalWeb");
		excludedStates.add("Finalizado");
		excludedStates.add("Inactivo");
		
		// Buscar campañas que NO estén en los estados excluidos
		return emailCampaignDao.findByEstadoNotIn(excludedStates);
	}

	/**
	 * Sobrecarga del método findById para aceptar Long
	 * Facilita la integración con otros componentes que usan Long como ID
	 * 
	 * @param id ID de la campaña (Long)
	 * @return EmailCampaign o null si no existe
	 */
	@Override
	public EmailCampaign findById(Long id) {
		if (id == null) {
			return null;
		}
		return emailCampaignDao.findById(id.intValue()).orElse(null);
	}

	/**
	 * Busca campañas por estado específico.
	 * Utilizado por el proceso automático para encontrar campañas "Abiertas".
	 * 
	 * @param estado Estado de la campaña (ej: "Abierto", "Finalizado")
	 * @return Lista de campañas con ese estado
	 */
	@Override
	public List<EmailCampaign> findByEstado(String estado) {
		if (estado == null || estado.trim().isEmpty()) {
			return new ArrayList<>();
		}
		return emailCampaignDao.findByEstado(estado);
	}

}

