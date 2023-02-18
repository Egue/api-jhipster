package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.MikrotikIp;
import com.comunicamosmas.api.service.IMikrotikIpService;
import com.comunicamosmas.api.service.IMikrotikSegmentoIpService;
import com.comunicamosmas.api.web.rest.vm.ClassError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class MikrotikIpController {

    @Autowired
    private IMikrotikIpService mikrotikIpService;

    @Autowired
    private IMikrotikSegmentoIpService mikrotikSegmentoipService;

    /**
     * ENDPOINT GET*/
    @GetMapping("/mikrotikip/findByIdSegmento/{id}")
    public ResponseEntity<?> findByIdSegmento(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MikrotikIp> list = mikrotikIpService.findAllBySegmentoIp(id);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT get ip activo = 1 => 0 vacio*/
    @GetMapping("/mikrotikip/findAllBySegmentoIpStatus/{id}")
    public ResponseEntity<?> findAllBySegmentoIpStatus(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikIp> listStatus = mikrotikIpService.findAllBySegmentoIpStatus(id);

            response.put("response", listStatus);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT DELETE BY IdSegmento*/
    @DeleteMapping("/mikrotikip/deleteByIdSegmento/{id}")
    public ResponseEntity<?> deleteByIdSegmento(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            ClassError error = mikrotikIpService.deleteByIdSegmento(id);
            if (error.getError()) {
                response.put("response", error.getMsm());

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }

            mikrotikSegmentoipService.deleteById(id);

            response.put("response", "eliminado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT*/
    @GetMapping("/mikrotikip/findByidPool/{id}")
    public ResponseEntity<?> findByidPool(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikIp> list = mikrotikIpService.findByIdPool(id);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**ENDPOINT GET*/
    @GetMapping("/mikrotikip/updatedforusado/{id}")
    public ResponseEntity<?> updatedForUsado(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            mikrotikIpService.updatedStatus(id);

            response.put("response", "ip Actualizada como usada");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
