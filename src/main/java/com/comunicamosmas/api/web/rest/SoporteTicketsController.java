package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.service.ISoporteTicketTipoService;
import com.comunicamosmas.api.service.dto.TicketTipoDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class SoporteTicketsController {

    @Autowired
    ISoporteTicketTipoService ticketTipoService;

    @GetMapping("/soporteTicket/tipo/{idServicio}")
    public ResponseEntity<?> findSoporteTipoByIdServicio(@PathVariable Long idServicio) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<TicketTipoDTO> result = ticketTipoService.findByIdServicio(idServicio);

            response.put("response", result);

         return   new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", e.getMessage());

           return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

     

}
