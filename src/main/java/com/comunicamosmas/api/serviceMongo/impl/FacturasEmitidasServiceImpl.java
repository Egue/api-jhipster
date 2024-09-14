package com.comunicamosmas.api.serviceMongo.impl;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.domainMongo.FacturasEmitidas;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.repositoryMongo.FacturasEmitidasRepository;
import com.comunicamosmas.api.service.IDeudaService;
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.IGeneratePDFService;
import com.comunicamosmas.api.service.IZipFileCreatorService;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;
import com.comunicamosmas.api.serviceMongo.IFacturasEmitidasService;
import com.fasterxml.jackson.databind.ObjectMapper;
 

@Service
public class FacturasEmitidasServiceImpl implements IFacturasEmitidasService{
    private final FacturasEmitidasRepository facturasEmitidasRepository;

    private final IDeudaService deudaService;

    private final IGeneratePDFService generatePDFService;

    private final IZipFileCreatorService zipFileCreatorService;

    private final ISystemConfigDao systemConfigDao;

    private final IEmailCampaignDetalleService emailCampaignDetalleService;

    public FacturasEmitidasServiceImpl(FacturasEmitidasRepository facturasEmitidasRepository, IDeudaService deudaService , 
    IGeneratePDFService generatePDFService , IZipFileCreatorService zipFileCreatorService , ISystemConfigDao systemConfigDao , 
    IEmailCampaignDetalleService emailCampaignDetalleService)
    {
        this.facturasEmitidasRepository = facturasEmitidasRepository;

        this.deudaService = deudaService;

        this.generatePDFService = generatePDFService;

        this.zipFileCreatorService = zipFileCreatorService;

        this.systemConfigDao = systemConfigDao;

        this.emailCampaignDetalleService = emailCampaignDetalleService;
    }
    @Override
    public void save(FacturasEmitidas emitidas) {
        // TODO Auto-generated method stub
        facturasEmitidasRepository.save(emitidas);
    }

    @Override
    public List<FacturasEmitidas> findByidCampaign(Integer idCampaign) {
        // TODO Auto-generated method stub
        return facturasEmitidasRepository.findByIdCampaign(idCampaign).get();
    }
    @Override
    public void saveAll(List<FacturasEmitidas> emitidas) {
        // TODO Auto-generated method stub
        facturasEmitidasRepository.saveAll(emitidas);
    }
    @Override
    public List<FacturasEmitidas> findByIdCliente(Integer idCliente, Pageable page) {
        // TODO Auto-generated method stub
       return facturasEmitidasRepository.findByIdCliente(idCliente , page).getContent();
    }

    @Override
    public void sendFactura(EmailCampaignDetalleDTO detalle , EmailCampaign campaign) {
    
			String fecha = campaign.getAnno() + campaign.getMes();

			String primeroscaracteres = fecha.substring(0, 6);

			Long mesServicio = Long.parseLong(primeroscaracteres);
			/*
			 * consultamos las deudas de acuerdo a la factura
			 * @param factura
			 * @Param messervicio esta va a concatenar facturado_mes
			 * @param idCliente
			 * @Param Origen A o B
			 */
			List<DeudasForFacturaDTO> deudas = deudaService.findDeudaByFacturaAndMesServiceAndIdEmpresa(Long.parseLong(detalle.getFactura()), mesServicio, campaign.getIdEmpresa(), detalle.getIdCliente() , detalle.getOrigen());

            RespuestaGeneracionPDFFactura generacionPDF = generatePDFService.generateFacturaPDF(detalle, deudas, campaign);


            if(detalle.getOrigen().equals("A"))
			{
				String pathZip = zipFileCreatorService.zipFileFactura(generacionPDF , detalle);

				generacionPDF.setPathZIP(pathZip);

				//eliminar pdf y zip
				File pdf = new File(generacionPDF.getPathPDF());
				File xml = new File(generacionPDF.getPathXML());
				if(pdf.exists()){pdf.delete();}
				if(xml.exists()){xml.delete();}
			} 

           this.sendMail(detalle , generacionPDF);
            
    }

    private String sendMail(EmailCampaignDetalleDTO detalle, RespuestaGeneracionPDFFactura responsePDF) {
 

		try {

            SystemConfig config = systemConfigDao.findByOrigen("mail_send");

            ObjectMapper mapper = new ObjectMapper();

            EmailCampaignApi datos = mapper.readValue(config.getComando() ,  EmailCampaignApi.class);

            String html = systemConfigDao.findByOrigen(datos.getHtml_part()).getComando();

            datos.setHtml_part(html);

            String fondo = emailCampaignDetalleService.fondoMail(responsePDF, datos);

            EmailCampaignDetalle unitario = new EmailCampaignDetalle();

            unitario.setEmail(detalle.getEmail());
            unitario.setFactura(detalle.getFactura());
            unitario.setIdCliente(detalle.getIdCliente());
            unitario.setIdEmailCampaign(detalle.getIdCampaign());
            unitario.setOrigen(detalle.getOrigen());

            String send =  emailCampaignDetalleService.mailRelaySendMail(datos, unitario, fondo, responsePDF);

            FacturasEmitidas facturas = facturasEmitidasRepository.findByIdClienteAndFacturaAndIdCampaign(detalle.getIdCliente(), detalle.getFactura(), detalle.getIdCampaign());
            facturas.setEmail(detalle.getEmail());
            this.save(facturas);
            
            return send;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            return null;
        }
        
		
		// System.out.print(fondo);
		

	} 
   
    
}
