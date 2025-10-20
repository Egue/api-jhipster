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

	/**
	 * Busca campañas activas disponibles para procesamiento batch.
	 * Excluye campañas con estado 'PortalWeb', 'Finalizado' o 'Inactivo'.
	 * 
	 * Este método es utilizado por:
	 * - EmailCampaignAirflowController para obtener campañas pendientes
	 * - Procesos batch automáticos
	 * 
	 * @return Lista de campañas activas listas para procesar
	 */
	public List<EmailCampaign> findActiveCampaignsForBatch();

	/**
	 * Busca una campaña por ID (sobrecarga para Long)
	 * 
	 * @param id ID de la campaña (Long)
	 * @return EmailCampaign o null si no existe
	 */
	public EmailCampaign findById(Long id);
	 
}
