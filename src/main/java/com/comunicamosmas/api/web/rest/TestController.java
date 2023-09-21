package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.service.IApiRBMikrotikService;
import com.comunicamosmas.api.service.IEstacionService;
import com.comunicamosmas.api.service.IMikrotikService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.legrange.mikrotik.ApiConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class TestController {

    @Autowired
    IMikrotikService mikrotikService;

    @Autowired
    IEstacionService estacionService;

    @Autowired
    IApiRBMikrotikService apimikrotikservice;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        Map<String, Object> response = new HashMap<>();

        try {
            String prueba = "10M";
            String[] relay = prueba.split("M");
            String res;
            int velocidad = 0;
            int number = Integer.parseInt(relay[0]);
            int divido = number / 5;
            if (number % 5 == 0) {
                velocidad = number / 5;
                res = "no aplica";
            } else {
                //
                float bajada = Float.parseFloat(relay[0]);
                float bky = bajada / 4L * 100;
                velocidad = (int) bky;
                res = "si aplica";
            }
            response.put("response", velocidad);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remoteaddress")
    public ResponseEntity<?> remoteAdrres(@RequestParam Long idContrato, @RequestParam String ip, @RequestParam Long idEstacion) {
        Map<String, Object> response = new HashMap<>();
        try {
            mikrotikService.test(idEstacion, ip, idContrato);

            response.put("response", "ok");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/test/findProfile")
    public ResponseEntity<?> findProfile(@RequestParam String profile , @RequestParam Long idEstacion)
    {
        Map<String, Object> response = new HashMap<>();

        try {
            Estacion estacion = estacionService.findById(idEstacion);
            String comando = "/ppp/profile/print where name="+profile;

            List<Map<String,String>> result = apimikrotikservice.listSendCommand(comando, estacion);

            if(result.isEmpty())
            {
                response.put("response" , "Sin resultados");
            }else{
                response.put("response" , result);
            }
            

             return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            
        } catch (Exception e) {
             response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
