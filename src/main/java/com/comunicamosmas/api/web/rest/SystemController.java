package com.comunicamosmas.api.web.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class SystemController {

    @Autowired
    ISystemConfigService systemService;
    
    @GetMapping("/systemConfig/tipo_pqr")
    public ResponseEntity<?> findTipoRetiro()
    {
        Map<String, Object> response = new HashMap<>();

        try{

            List<ValorStringDTO> result = systemService.findTipoPqr();

            response.put("response" , result);

            
            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK); 

        }catch(ExceptionNullSql e)
        {
            response.put("response" , e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);

        }catch(Exception e)
        {   
            response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
        }
    }
    
}
