package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Authority;
import com.comunicamosmas.api.service.IAuthorityService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class AuthorityController {
    
    @Autowired
    IAuthorityService authorityService;

    @GetMapping("/authority")
    public ResponseEntity<?> findAll()
    {
        Map<String, Object> response = new HashMap<>();
        
        try{

            List<Authority> result = authorityService.findAll();

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
            
        }catch(Exception e)
        {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);
        }
    }

     
}
