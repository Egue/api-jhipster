package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.service.IEmpresaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class EmpresaController {

    @Autowired
    private IEmpresaService empresaService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/empresa/list")
    public ResponseEntity<?> list() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Empresa> listEmpresa = empresaService.findAll();

            response.put("response", listEmpresa);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/empresa/listbystatus")
    public ResponseEntity<?> listByStatus(){
        try {
            Optional<List<Empresa>> list = empresaService.findAllByStatus();
            
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/empresa/save")
    public ResponseEntity<?> save(@RequestBody Empresa empresa) {
        Map<String, Object> response = new HashMap<>();

        try {
            empresaService.save(empresa);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET buscar por nombreComercial*/
    @GetMapping("/empresa/findByLikeNombreComercial/{name}")
    public ResponseEntity<?> findByLikeNombreComercial(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Empresa> findByListLikeNombre = empresaService.findByLikeNombreComercial(name);

            response.put("response", findByListLikeNombre);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
