package com.comunicamosmas.api.web.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.ListaDepartamento;
import com.comunicamosmas.api.service.IListaDepartamentoService;

@RestController
@RequestMapping("/api/controlmas")
public class DepartamentoController {
    
    private final IListaDepartamentoService listaDepartamentoService;

    public DepartamentoController(IListaDepartamentoService listaDepartamentoService)
    {
        this.listaDepartamentoService = listaDepartamentoService;
    }

    @GetMapping("/departamentos")
    public ResponseEntity<?> find(@RequestParam("estado") Long estado)
    {
        try {
            List<ListaDepartamento> list = listaDepartamentoService.findByEstado(estado);

            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
