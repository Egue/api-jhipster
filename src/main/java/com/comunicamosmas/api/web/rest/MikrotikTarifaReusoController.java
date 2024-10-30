package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import com.comunicamosmas.api.service.IMikrotikTarifaReusoService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class MikrotikTarifaReusoController {

    @Autowired
    IMikrotikTarifaReusoService mikrotikTarifaReusoService;

    @GetMapping("/mikrotiktarifareuso/findByIdEstacion/{id}")
    public ResponseEntity<?> findByIdEstacion(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikTarifaReuso> list = mikrotikTarifaReusoService.findByIdEstacion(id);

            response.put("response", list);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 1. se debe crear el padre en la base de datos, despues es q se crea en winbox*/
    //ENDPOINT POST save
    @PostMapping("/mikrotiktarifareuso/save")
    public ResponseEntity<?> save(@RequestBody MikrotikTarifaReuso mikrotikTarifaReuso) {
        Map<String, Object> response = new HashMap<>();

        try {
            mikrotikTarifaReusoService.save(mikrotikTarifaReuso);

            response.put("response", "Creado con Éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /*updated */
    @PutMapping("/mikrotiktarifareuso/{id}")
    public ResponseEntity<?> updated(@PathVariable("id") Long id,  @RequestBody MikrotikTarifaReuso tarifaReuso)
    {
        try {
            MikrotikTarifaReuso reuso = mikrotikTarifaReusoService.updated(tarifaReuso);

            return ResponseEntity.ok().body(reuso);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().build();
        }
    }

    /**ENDPOINT*/
    @GetMapping("/mikrotiktarifareuso/findbylikename/{name}/{idEstacion}")
    public ResponseEntity<?> list(@PathVariable String name, @PathVariable Long idEstacion) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikTarifaReuso> planes = mikrotikTarifaReusoService.findByLikeEstado(name, idEstacion);

            response.put("response", planes);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**ENDPOINT POST FIND BUSCAR PLAN POR NOMBRE, TIPO, IDESTACION*/
    @PostMapping("mikrotiktarifareuso/findByidTipoAndIdEstacionAndName")
    public ResponseEntity<?> findByidTipoAndIdEstacionAndName(
        @RequestParam Long tipo,
        @RequestParam Long idEstacion,
        @RequestParam String nombrePadre
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MikrotikTarifaReuso> result = mikrotikTarifaReusoService.findByidTipoAndIdEstacionAndName(tipo, idEstacion, nombrePadre);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
