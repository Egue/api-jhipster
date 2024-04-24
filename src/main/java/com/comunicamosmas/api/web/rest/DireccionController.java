package com.comunicamosmas.api.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Direccion;
import com.comunicamosmas.api.service.IDireccionService;
import com.comunicamosmas.api.service.dto.DireccionDTO;

import java.util.List;
@RestController
@RequestMapping("/api/controlmas")
public class DireccionController {

    private final IDireccionService direccionService;

    public DireccionController(IDireccionService direccionService)
    {
        this.direccionService = direccionService;
    }
    
    @GetMapping("/direccion")
    public ResponseEntity<?> find(@RequestParam("idCliente") Long idCliente)
    {
        try {
            List<DireccionDTO> result = direccionService.findByIdCliente(idCliente);
         return    ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            // TODO: handle exception

         return    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
