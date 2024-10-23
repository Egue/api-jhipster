package com.comunicamosmas.api.web.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.ListaMunicipio;
import com.comunicamosmas.api.service.IListaMunicipioService;

@RestController
@RequestMapping("/api/controlmas")
public class MunicipioController {

    private final IListaMunicipioService municipioService;

    public MunicipioController(IListaMunicipioService municipioService)
    {
        this.municipioService = municipioService;
    }

    @GetMapping("/municipios")
    public ResponseEntity<?> find(@RequestParam("departamentoid") Long departamentoid)
    {
        try {
            List<ListaMunicipio> list = municipioService.findByDepartamentoid(departamentoid);

            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            // TODO: handle exception

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
