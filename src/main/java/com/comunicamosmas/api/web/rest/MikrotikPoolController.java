package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.MikrotikPool;
import com.comunicamosmas.api.service.IMikrotikPoolService;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class MikrotikPoolController {

    @Autowired
    private IMikrotikPoolService mikrotikPoolService;

    /**
     * END POINT GET*/
    @GetMapping("/mikrotikpool/findbyidestacion/{id}")
    public ResponseEntity<?> findByIdEstacion(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikPool> result = mikrotikPoolService.findByIdEstacion(id);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/mikrotikpool/save")
    public ResponseEntity<?> save(@RequestBody MikrotikPool mikrotikPool) {
        Map<String, Object> response = new HashMap<>();

        try {
            mikrotikPoolService.save(mikrotikPool);

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
