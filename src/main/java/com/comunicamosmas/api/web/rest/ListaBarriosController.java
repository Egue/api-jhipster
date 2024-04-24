package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.ListaBarrio;
import com.comunicamosmas.api.repository.IListaBarrioDao;
import com.comunicamosmas.api.service.IListaBarrioService;

@RestController
@RequestMapping("/api/controlmas")
public class ListaBarriosController {
    
    private final IListaBarrioService listaBarrioService;

    public ListaBarriosController(IListaBarrioService listaBarrioService)
    {
        this.listaBarrioService = listaBarrioService;
    }

    @GetMapping("/barrios")
    public ResponseEntity<?> find(@RequestParam("municipio") Long municipio)
    {
        try {
            List<ListaBarrio> list  = listaBarrioService.findByIdMunicipio(municipio);

            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            // TODO: handle exception
            Map<String, Object> response = new HashMap<>();

            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
