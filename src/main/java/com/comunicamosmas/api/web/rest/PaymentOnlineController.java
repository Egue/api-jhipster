package com.comunicamosmas.api.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.security.AuthoritiesConstants;
import com.comunicamosmas.api.service.IPaymentOnlineService;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO;

@RestController
@RequestMapping("/api/controlmas")
public class PaymentOnlineController {

    private final IPaymentOnlineService paymentOnlineService;

    public PaymentOnlineController(IPaymentOnlineService paymentOnlineService)
    {
        this.paymentOnlineService = paymentOnlineService;
    }

    @GetMapping("/paymentOnline/list")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> findList(@Valid @RequestParam("first") String dateUno , @RequestParam("final") String dateTwo)
    {
        try {
            List<PaymentOnlineDTO.ListPagos> pagos = paymentOnlineService.findListPaymentOnlyBetwenn(dateUno, dateTwo);

            return ResponseEntity.status(HttpStatus.OK).body(pagos);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
