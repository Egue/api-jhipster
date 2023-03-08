package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.service.IEstacionService;
import com.comunicamosmas.api.service.IWinmaxPassService;
import com.comunicamosmas.api.service.dto.ListWinmaxPassDTO;

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
public class WinmaxPassController {

    @Autowired
    IWinmaxPassService winmaxPassService;

    @Autowired
    IEstacionService estacionService;

    /**
     * ENDPOINT GET FIND BY ID CONTRATO*/
    @PostMapping("/winmaxpass/findByIdContrato")
    public ResponseEntity<?> findByIdContrato(
        @RequestParam Long idContrato,
        @RequestParam Long idOrden,
        @RequestParam String plan,
        @RequestParam Long idEstacion
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            WinmaxPass winmaxPass = winmaxPassService.findByIdContrato(idContrato);
            Estacion estacion = estacionService.findById(idEstacion);

            String msm =
                "Controlmas te informa: Para Orden:" +
                idOrden +
                " de CUS " +
                idContrato +
                ", usa ->Estacion: " +
                estacion.getNombre() +
                "/Usuario:" +
                winmaxPass.getUsuario() +
                " /Clave:" +
                winmaxPass.getPass() +
                " /Plan:" +
                plan +
                "";

            response.put("response", msm);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
    /**ENDPOINT GET BUSCAR POR ESTACION CON DTO*/
    @GetMapping("/winmaxpass/findByIdEstacion/{id}")
    public ResponseEntity<?> getFindByEstacion(@PathVariable Long id)
    {
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		List<ListWinmaxPassDTO> list = winmaxPassService.findByIdEstacionWithDatos(id);
    		
    		response.put("response", list);
    		
    		return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
    	}catch(Exception e)
    	{
    		return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
    	}
    }
    
   
}
