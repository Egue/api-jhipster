package com.comunicamosmas.api.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.IApiRestService;
import com.comunicamosmas.api.service.IClienteService;
import com.comunicamosmas.api.service.IPagoLineaVersionDosService;
import com.comunicamosmas.api.service.IPaymentOnlineService;

@RestController
@RequestMapping("/api/controlmas")
public class ScheduledController {

    private final IApiRestService apiRestService;

    private final IPaymentOnlineService paymentOnlineService;

    private final ISystemConfigDao systemConfigDao;

    private final IPagoLineaVersionDosService pagoLineaVersionDosService;

    private final IClienteService clienteService;

    public ScheduledController(IApiRestService apiRestService , IPaymentOnlineService paymentOnlineService , ISystemConfigDao systemConfigDao, IPagoLineaVersionDosService pagoLineaVersionDosService,
    IClienteService clienteService){

        this.apiRestService = apiRestService;

        this.paymentOnlineService = paymentOnlineService;

        this.systemConfigDao = systemConfigDao;

        this.pagoLineaVersionDosService = pagoLineaVersionDosService;

        this.clienteService = clienteService;

    }

    @RequestMapping("/scheduled/supergiros")
    public ResponseEntity<?> supergiros(@RequestParam("token")String token)
    {
        try {

            if(valitateToken(token)){
                
                apiRestService.pagosSupergiros();
                
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorization");
            }

            
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping("/scheduled/pse")
    public ResponseEntity<?> downloadPaymentOnline(@RequestParam("token") String token)
    {
        try {

            if(valitateToken(token)){
                
                paymentOnlineService.downloadPaymentOnline();
                
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorization");
            }
   
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping("/scheduled/pseordenes")
    public ResponseEntity<?>reconexionAndAnulationCortePse(@RequestParam("token") String token)
    {
        try {

            if(valitateToken(token)){
                
                pagoLineaVersionDosService.iterarReconexionesAndCorte();
                
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorization");
            }
   
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/scheduled/reporte-clausuras")
    public ResponseEntity<?> reporteClausuras(@RequestParam("token") String token)
    {
        try {
            if(valitateToken(token)){
                
                //send reporte

                clienteService.clientesDeclineClausura();
                
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorization");
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    private boolean valitateToken(String token)
    {
        SystemConfig system = systemConfigDao.findByOrigen("token_scheduled");
        if(system.getComando().equals(token))
        {
            return true;
        }else{

            return false;
        }
    }
    
}
