package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.MailRelaySendMail;

public interface IMailRelayService {
    
    public String mailRelaySend(MailRelaySendMail.Send mailRelaySendMail , String token , String url);
}
