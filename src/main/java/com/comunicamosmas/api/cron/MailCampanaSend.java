package com.comunicamosmas.api.cron;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 

import com.comunicamosmas.api.domain.EmailCampaign; 
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.IEmailCampaignService;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;

 
public class MailCampanaSend {
    
@Autowired
IEmailCampaignService  emailCampaignService;

@Autowired
IEmailCampaignDetalleService emailDetalleService;



    public void sendCampaign()
    {
        //consultamos estado de campana con un solo item
        EmailCampaign campaign = emailCampaignService.findEmailCampaignLimitOne();

        if(campaign != null)
        {
            //recorrer detalle campaign buscar por id estado diferente de 1
            List<EmailCampaignDetalleDTO> detalle = emailDetalleService.findEmailCampaignDetalleSinProcesar(campaign.getId());
            
            if(detalle != null && !detalle.isEmpty())
            {
                for(EmailCampaignDetalleDTO rs: detalle)
                {
                    emailDetalleService.sendMailUnitario(rs);
                }
            }
        }
    }
}
