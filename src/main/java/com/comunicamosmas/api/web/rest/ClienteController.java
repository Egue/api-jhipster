package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.service.IClienteService;
import com.comunicamosmas.api.service.IContratoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class ClienteController {

    @Autowired
    IClienteService clienteService;

    /**
     * POST BUSCAR CLIENTE POR DOCUMENTO*/
    @PostMapping("/clientes/findByDocumento")
    public ResponseEntity<?> findByDocumento(@RequestParam String documento) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Cliente> list = clienteService.findByDocumento(documento);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
    }
}
