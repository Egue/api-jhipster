package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.service.IEstacionService;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.web.rest.vm.ClassHablame;
import com.comunicamosmas.api.web.rest.vm.ClassResponseHablame;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class WhatsappController {

    @Autowired
    IEstacionService estacionService;

    @Autowired
    IOrdenService ordenService;

    @PostMapping("/whatsapp/msm")
    public ResponseEntity<?> priority(
        @RequestParam Long idAppMaster,
        @RequestParam Long idContrato,
        @RequestParam Long idEstacion,
        @RequestParam String name,
        @RequestParam String nameSecrect,
        @RequestParam String pass,
        @RequestParam Long idOrden
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            //encontrar telefono
            Estacion estacion = estacionService.findById(idEstacion);

            String telefono = "57" + ordenService.findTelefonoByIdOrden(idOrden);
            String mensage =
                "Controlmas te Informa: Para orden " +
                idOrden +
                " de " +
                "CUS " +
                idContrato +
                " usa --> Estacion: " +
                estacion.getCodigo() +
                " " +
                estacion.getNombre() +
                " " +
                "/Usuario: " +
                nameSecrect +
                " / Clave: " +
                pass +
                " / Plan: " +
                name +
                "";

            response.put("response", telefono);
            response.put("msm", mensage);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
