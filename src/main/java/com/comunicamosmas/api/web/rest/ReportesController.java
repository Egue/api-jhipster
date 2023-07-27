package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IHanRetirosService;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.ISoporteTicketService;
import com.comunicamosmas.api.service.dto.AnoWithListIntegerDTO;
import com.comunicamosmas.api.service.dto.CarteraDTO;
import com.comunicamosmas.api.service.dto.ReporteHanRetirosDTO;
import com.comunicamosmas.api.service.dto.ReporteOrdenConVisitaFallidaDTO;
import com.comunicamosmas.api.service.dto.SoporteTicketDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
 
@RestController
@RequestMapping("/api/controlmas")
public class ReportesController {


    @Autowired
    IContratoService contratoService;

    @Autowired
    IOrdenService ordenService;

    @Autowired
    ISoporteTicketService ticketService;

    @Autowired
    IHanRetirosService retirosService;

    @PostMapping("/reportes/cartera")
    public ResponseEntity<?> carteraByIdServicio(@RequestBody AnoWithListIntegerDTO servicios)
    {
        Map<String, Object> response = new HashMap<>();

        try{


            List<CarteraDTO> result = contratoService.carteraByServicio(servicios.getArrayInteger());

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);

          }catch(ExceptionNullSql e)
        {
            response.put("error" , e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);

        }catch(Exception e)
        {
            response.put("error" , e.getMessage());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
        }
    }

     

    //reporte por ordenes de servicio
    @PostMapping("/reportes/ordenes/tipo")
    public ResponseEntity<?> ordenesByTipo(@RequestParam("idServicio") Long idServicio , @RequestParam("tipo")Long tipo , @RequestParam("inicio") String inicio , @RequestParam("final") String finalf)
    {
         Map<String, Object> response = new HashMap<>();

        try{

            List<ReporteOrdenConVisitaFallidaDTO> result = ordenService.reporteConVisitaFallida(idServicio, tipo ,inicio, finalf);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);

        }catch(ExceptionNullSql e)
        {
            response.put("error" , e.getDetails());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
        }catch(Exception e)
        {
            response.put("error" , e.getMessage());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reportes/tickets")
    public ResponseEntity<?> reporteTickets(@RequestParam("tipo") Long tipo, @RequestParam("inicio") Long inicio, @RequestParam("final") Long finalf)
    {
        Map<String, Object> response = new HashMap<>();

        try{

             List<SoporteTicketDTO> result = ticketService.reporteTicket(tipo, inicio, finalf);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);

        }catch(Exception e)
        {
            response.put("error" , e.getMessage());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
        }
    }

    /**Reporte de retirados de han_retirados */
    @PostMapping("/reportes/hanretiros/all")
    public ResponseEntity<?> hanRetirosAll(@RequestBody AnoWithListIntegerDTO datos)
    {
        Map<String, Object> response = new HashMap<>();

        try{

            String estado = "";
            switch(datos.getAno()){
                case 1:
                estado = "Abierto";
                break;
                case 2:
                estado = "Cerrado";
                break;
            }
            List<ReporteHanRetirosDTO> result = retirosService.reporteHanRetiros(datos.getArrayInteger(), estado);


            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);

        }catch(ExceptionNullSql e){

             response.put("error" , e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
        } catch(Exception e)
        {
            response.put("error" , e.getMessage());

            return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
        }
    }
    
}
