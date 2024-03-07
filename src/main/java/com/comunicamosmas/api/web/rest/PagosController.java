package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.security.AuthoritiesConstants;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IPagoService;
 

@RestController
@RequestMapping("/api/controlmas")
public class PagosController {

    @Autowired
    IPagoService pagosService;
    
    @Autowired
    IContratoService contratoService;

    @PostMapping("/pagos/test")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> test(@RequestParam Long idContrato , @RequesParam int valor , @RequesParam String comentario)
    {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Contrato contrato = contratoService.findById(idContrato);


            pagosService.registerPagoSupergiros(contrato, valor, comentario);

            response.put("response", "OK");

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
            
        } catch (Exception e) {
            // TODO: handle exception
            response.put("errro" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.INSUFFICIENT_STORAGE);
        }
    }

    @GetMapping("/pagos/lasted")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> rcLastet(@RequestParam(name="servicio" ,required = true) Long servicio, @RequestParam(name="origen", required = true)String origen)
    {
        Map<String, Object> response = new HashMap<>();
        
        try { 


           int result =  pagosService.findLastRc(servicio , origen);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
            
        } catch (Exception e) {
            // TODO: handle exception
            response.put("errro" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.INSUFFICIENT_STORAGE);
        }
    }
}
