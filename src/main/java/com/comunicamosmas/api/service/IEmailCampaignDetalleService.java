package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;

public interface IEmailCampaignDetalleService {
	
	public void save(EmailCampaignDetalle emailCampaignDetalle);
	
	public void findEmailBySend(Integer idEmailCampaign);

	//public void invoiceByPortalWeb(Integer idEmailCampaign);
	
	public List<EmailCampaignDetalleDTO> findByIdEmailCampaing(Long id);
	
	public EmailCampaignDetalle findById(Integer id);
	
	//public void sendMail(Integer id);
	
	public String sendMailUnitario(EmailCampaignDetalleDTO detalle);

	//buscar emaildetalle procesado 0
	public List<EmailCampaignDetalleDTO> findEmailCampaignDetalleSinProcesar(Integer idEmailCampaign);

	public void addFactura(EmailCampaignDetalleDTO detalle);

	public void update(EmailCampaignDetalleDTO detalle);

	public String fondoMail(RespuestaGeneracionPDFFactura response, EmailCampaignApi api);

	public String mailRelaySendMail(EmailCampaignApi datos, EmailCampaignDetalle destino, String fondo,	RespuestaGeneracionPDFFactura responseGeneracionFactura);
	
	/**
	 * Cuenta el total de detalles de una campaña
	 * 
	 * @param campaignId ID de la campaña
	 * @return Cantidad total de detalles
	 */
	public long countByCampaign(Long campaignId);

	/**
	 * Cuenta detalles pendientes de envío (estado = 0 o NULL)
	 * 
	 * @param campaignId ID de la campaña
	 * @return Cantidad de detalles pendientes
	 */
	public long countPendingByCampaign(Long campaignId);

	/**
	 * Cuenta detalles enviados exitosamente (estado = 1)
	 * 
	 * @param campaignId ID de la campaña
	 * @return Cantidad de detalles enviados
	 */
	public long countSentByCampaign(Long campaignId);

	/**
	 * Cuenta detalles con error (estado = 2)
	 * 
	 * @param campaignId ID de la campaña
	 * @return Cantidad de detalles con error
	 */
	public long countErrorByCampaign(Long campaignId);

	/**
	 * Busca detalles sin procesar por ID de campaña (sobrecarga para Long)
	 * 
	 * @param campaignId ID de la campaña (Long)
	 * @return Lista de detalles sin procesar
	 */
	public List<EmailCampaignDetalleDTO> findEmailCampaignDetalleSinProcesar(Long campaignId);

}
