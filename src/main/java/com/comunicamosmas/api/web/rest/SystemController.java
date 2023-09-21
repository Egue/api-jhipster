package com.comunicamosmas.api.web.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.GrupoMailDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import io.jsonwebtoken.io.IOException;
 

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

    //consulta de grupo
    @GetMapping("/systemConfig/grupo")//mail
    public ResponseEntity<?> findGrupo()//id
    {
        Map<String, Object> response = new HashMap<>();
        try {

            GrupoMailDTO result = systemService.grupoMail();

            response.put("response" , result);

            
            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
            
        }catch(ExceptionNullSql e)
        {
            response.put("response" , e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //buscar nombre de archivos
    @PostMapping("/systemConfig/findDocument")
    public ResponseEntity<Resource> nameDocument(@RequestParam String tipo , @RequestParam String document)  
    {
            SystemConfig ruta = systemService.findByOrigen(tipo);
            System.out.println(tipo + ":" + document);
            Path filePath = Paths.get(ruta.getComando() , document);

           try {
             if(Files.exists(filePath))
            {
                byte[] fileContent = Files.readAllBytes(filePath);

                /*HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", document);*/

                ByteArrayResource resource = new ByteArrayResource(fileContent);

                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename="+document)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                  
                    .body(resource);
            }else{
                throw  new Exception("No existe el archivo");
            } 
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        } catch (Exception e) {
             

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        }
    }
    
}
