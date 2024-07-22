package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.service.dto.PaymentOnlineDTO;

public interface IPaymentOnlineService {
    
    public void downloadPaymentOnline();

    public List<PaymentOnlineDTO.ListPagos> findListPaymentOnlyBetwenn(String dateOne , String dateTwo);
}
