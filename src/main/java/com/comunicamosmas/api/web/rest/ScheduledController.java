package com.comunicamosmas.api.web.rest;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.IApiRestService;
import com.comunicamosmas.api.service.IClienteService;
import com.comunicamosmas.api.service.IPagoLineaVersionDosService;
import com.comunicamosmas.api.service.IPaymentOnlineService;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.IEmailCampaignService;
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.IFacturasEmitidasService;
import com.comunicamosmas.api.service.INuevoServicio;
 

@RestController
@RequestMapping("/api/controlmas")
public class ScheduledController {

    private final IApiRestService apiRestService;

    private final IPaymentOnlineService paymentOnlineService;

    private final ISystemConfigDao systemConfigDao;

    private final IPagoLineaVersionDosService pagoLineaVersionDosService;

    private final IClienteService clienteService;

    private final IEmailCampaignService emailCampaignService;

    private final IEmailCampaignDetalleService emailCampaignDetalleService;

    private final IFacturasEmitidasService facturasEmitidasService;

    private final INuevoServicio nuevoServicio;

    public ScheduledController(IApiRestService apiRestService, 
                               IPaymentOnlineService paymentOnlineService, 
                               ISystemConfigDao systemConfigDao, 
                               IPagoLineaVersionDosService pagoLineaVersionDosService,
                               IClienteService clienteService, 
                               IEmailCampaignService emailCampaignService, 
                               IEmailCampaignDetalleService emailCampaignDetalleService, 
                               IFacturasEmitidasService facturasEmitidasService,
                               INuevoServicio nuevoServicio) {
        this.apiRestService = apiRestService;
        this.paymentOnlineService = paymentOnlineService;
        this.systemConfigDao = systemConfigDao;
        this.pagoLineaVersionDosService = pagoLineaVersionDosService;
        this.clienteService = clienteService;
        this.emailCampaignService = emailCampaignService;
        this.emailCampaignDetalleService = emailCampaignDetalleService;
        this.facturasEmitidasService = facturasEmitidasService;
        this.nuevoServicio = nuevoServicio;
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

    /*Sincronizate clientes to portalWeb */
    @GetMapping("/scheduled/sincroniceportalweb")
    public ResponseEntity<?> sincronicePortalWeb(@RequestParam("token") String token)
    {
        try {
            if(valitateToken(token))
            {
                PageRequest page = PageRequest.of(0, 10);

                Page<ClientePortalWebDTO> clients = clienteService.pageClienteSyncronicePortalWeb(page);

                return ResponseEntity.status(HttpStatus.OK).body(clients.getContent());
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorization");
            }

        } catch (Exception e) {
            // TODO: handle exception

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/scheduled/sincroniceportalweb/{id}")
    public ResponseEntity<?> updateClienteSicronicePortalweb(@RequestParam("token")String token, @PathVariable long id)
    {
        try {
            if(valitateToken(token))
            {
                clienteService.updatedClientPortalWebSincronice(id);

                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorization");
            }

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*SantinyOficial */
    @RequestMapping("/scheduled/send-facturas")
    public ResponseEntity<?> sendFacturasAutomatizadas(@RequestParam("token") String token, @RequestBody EmailCampaignDetalleDTO detalle) {
        try {
            if (valitateToken(token)) {
                EmailCampaign campaign = emailCampaignService.findById(detalle.getIdCampaign());

                if (campaign.getEstado().equals("PortalWeb")) {
                    facturasEmitidasService.sendFactura(detalle, campaign);
                } else {
                    String result = emailCampaignDetalleService.sendMailUnitario(detalle);
                    return ResponseEntity.status(HttpStatus.OK).body("Facturas enviadas: " + result);
                }

                return ResponseEntity.status(HttpStatus.OK).body("Facturas enviadas correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
        } catch (Exception e) {
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
