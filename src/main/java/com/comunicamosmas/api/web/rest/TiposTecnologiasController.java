package com.comunicamosmas.api.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.comunicamosmas.api.service.ITipoTecnologiaService;
import com.comunicamosmas.api.domain.TipoTecnologia;
@RestController
@RequestMapping("/api/controlmas")
public class TiposTecnologiasController {
    
    private final ITipoTecnologiaService tipoTecnologiaService;
    public TiposTecnologiasController(ITipoTecnologiaService tipoTecnologiaService)
    {
        this.tipoTecnologiaService = tipoTecnologiaService;
    }


    @GetMapping("/tecnologias")
    public ResponseEntity<?> findServicio(@RequestParam("servicio") Long servicio)
    {
        try {
            Optional<List<TipoTecnologia>> list = tipoTecnologiaService.findByServicio(servicio);

            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
