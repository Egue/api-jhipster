package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.service.IClienteService; 
import com.comunicamosmas.api.service.dto.ClienteDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
 

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
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
     * POST BUSCAR CLIENTE POR DOCUMENTO
     */
    @PostMapping("/clientes/findByDocumento")
    public ResponseEntity<?> findByDocumento(@RequestParam String documento) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Cliente> list = clienteService.findByDocumento(documento);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // buscar clientes por nombre
    @GetMapping("/clientes/findbyname/{nombre}")
    public ResponseEntity<?> findByName(@PathVariable final String nombre) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<ClienteDTO> list = clienteService.findByName(nombre);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // buscar por cus
    @GetMapping("/clientes/findByCus/{cus}")
    public ResponseEntity<?> findByCus(@PathVariable final Long cus) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<ClienteDTO> list = clienteService.findByCus(cus);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clientes/save")
    public ResponseEntity<?> save(@RequestBody Cliente cliente) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<ClienteDTO> findByDocumento = clienteService.validExisteCliente(cliente.getDocumento().toString());

            if (!findByDocumento.isEmpty()) {
                throw new ExceptionNullSql(new Date(), "Documento ya existe",
                        "el documento " + cliente.getDocumento() + " ya registrado");
            } else {
                clienteService.save(cliente);
            }

            response.put("response", "Creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
