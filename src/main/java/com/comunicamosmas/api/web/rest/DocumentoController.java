package com.comunicamosmas.api.web.rest;
  
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Documento;
import com.comunicamosmas.api.service.IDocumentoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class DocumentoController {


    @Autowired
    IDocumentoService documentoService;


    @GetMapping("/documento/list")
    public ResponseEntity<?> list()
    {
            Map<String , Object> response  =new HashMap<>();

            try {

                List<Documento> result = documentoService.findAll();
                
                response.put("response", result);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // TODO: handle exception
                response.put("response", e.getMessage());

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
    }
    
}
