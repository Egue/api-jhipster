package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.domain.MikrotikSegmentoIp;
import com.comunicamosmas.api.service.IEstacionService;
import com.comunicamosmas.api.service.IMikrotikIpService;
import com.comunicamosmas.api.service.IMikrotikSegmentoIpService;
import com.comunicamosmas.api.service.dto.SegmentoIPDTO;

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
public class MikrotikSegmentoIpController {

    @Autowired
    IMikrotikSegmentoIpService mikrotikSegmentoIpService;

    @Autowired
    IMikrotikIpService mikrotikIpService;

    @Autowired
    IEstacionService estacionService;

    /**
     * ENDPOINT get*/
    @GetMapping("/mikrotiksegmento/findByIdEstacion/{id}")
    public ResponseEntity<?> list(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikSegmentoIp> list = mikrotikSegmentoIpService.findByIdEstacion(id);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET */
    @GetMapping("/mikrotiksegmento/findByIdPool/{id}")
    public ResponseEntity<?> findByIdPool(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MikrotikSegmentoIp> result = mikrotikSegmentoIpService.findByIdPool(id);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**ENDPOINT GET*/
    @GetMapping("/mikrotiksegmento/findByidPoolAndEstado/{id}")
    public ResponseEntity<?> findByidPoolAndEstados(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MikrotikSegmentoIp> result = mikrotikSegmentoIpService.findByIdPool(id);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * ENDPOINT POST save*/
    /*@PostMapping("/mikrotiksegmento/save")
    public ResponseEntity<?> save(@RequestBody MikrotikSegmentoIp mikrotikSegmentoIp) {
        Map<String, Object> response = new HashMap<>();
        try {
            //buscar la ippublic de la estacion
            Estacion estacion = estacionService.findById(mikrotikSegmentoIp.getIdEstacion());

            //int ifExist = mikrotikSegmentoIpService.countFindByIdEstacionAndName(mikrotikSegmentoIp.getIdEstacion(), mikrotikSegmentoIp.getSegmento());
            int ifExist = mikrotikSegmentoIpService.countDFindIpByPublic(estacion.getApiIp(), mikrotikSegmentoIp.getSegmento());
            if (ifExist > 0) {
                response.put("response", "Ya existe el segmento en la estación");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            mikrotikSegmentoIpService.save(mikrotikSegmentoIp);
            //recuperar el id del segmento
            Long id = mikrotikSegmentoIp.getId();
            //enviamos a separar el segmento 10.250.2.0/32
            String[] segmento = mikrotikSegmentoIpService.splitSegment(mikrotikSegmentoIp);
            //generamos lista de ip de acuerdo al segmento y almacenamos en mikrotik_ip
            mikrotikIpService.recorrer(segmento, id);

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }*/
    @PostMapping("/mikrotiksegmento/save")
    public ResponseEntity<?> save(@RequestBody SegmentoIPDTO segmento)
    {
        Map<String, Object> response = new HashMap<>();

        try {

            mikrotikSegmentoIpService.save(segmento);

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }   
}
