package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.dto.ContratosFirmasDTO;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO;  
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class ContratoController {

    @Autowired
    IContratoService contratoService;

    /**
     * ENDPOINT GET
     * buscar lista de contrato por idCliente*/
    @GetMapping("/contratos/listByidCliente/{idCliente}")
    public ResponseEntity<?> findByIdCliente(@PathVariable Long idCliente) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ListContratoDTO> list = contratoService.findByIdCliente(idCliente);
            response.put("response", list);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * ENDPOINT GET buscar datos de contacto de un contrato*/
    @GetMapping("/contratos/datoscontacto/{id}")
    public ResponseEntity<?> datosContactoByIdContrato(@PathVariable long id)
    {
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		DatosClienteDTO cliente = contratoService.datosContactoByIdContrato(id);
    		
    		response.put("response", cliente);
    		
    		return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
    		
    	}catch(Exception e)
    	{
    		response.put("response", e.getMessage());
    		
    		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    	}
    }

    /**
     * @param idContrato
     */
    @GetMapping("/contratos/find")
    public ResponseEntity<?> find(@RequestParam Long idContrato)
    {
        try {
            String contrato = "ok";
            return ResponseEntity.status(HttpStatus.OK).body(contrato);
        } catch (Exception e) {
            // TODO: handle exception
            Map<String, Object> response = new HashMap<>();
             response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
