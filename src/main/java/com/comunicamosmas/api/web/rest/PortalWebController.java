package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.security.AuthoritiesConstants;
import com.comunicamosmas.api.service.IClienteService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IPortalWebService;
import com.comunicamosmas.api.service.dto.AdminPortalWebDTO;
import com.comunicamosmas.api.service.dto.AdminUserDTO;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;
 

@RestController
@RequestMapping("/api/controlmas")
public class PortalWebController {
    
    @Autowired
    IPortalWebService portalWebService;

    @Autowired
    IContratoService contratoService;

    @Autowired
    IClienteService clienteService;

    @PostMapping("/portalweb/cliente")
    public ResponseEntity<?> save(@RequestBody ClientePortalWebDTO cliente , @RequestHeader("Authorization") String token)
    {
        Map<String, Object> response = new HashMap<>();

        try {
            portalWebService.addUser(cliente , token);

            response.put("portal", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/portalweb/users")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> users(@RequestHeader("Authorization") String token)
    {
        Map<String, Object> response = new HashMap<>();

        try {
            List<AdminUserDTO> users =  portalWebService.userPortalWeb(token);

            response.put("userPortal", users );

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/portalweb/users")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> create(@RequestBody AdminPortalWebDTO adminUserDTO)
    {
        Map<String, Object> response = new HashMap<>();

        try {
             portalWebService.createUser(adminUserDTO);

            response.put("Creado", "OK" );

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/portalweb/activate/{token}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> activated(@PathVariable String token)
    {
        Map<String, Object> response = new HashMap<>();

        try {
             portalWebService.activate(token);

            response.put("Activate", "OK" );

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/portalweb/cliente/{documento}")
    public ResponseEntity<?> findCliente(@PathVariable String documento , @RequestHeader("Authorization") String token)
    {
        Map<String, Object> response = new HashMap<>();

        try {

            ClientePortalWebDTO clientePortal =  portalWebService.findByDocumento(documento, token);

            List<Cliente> cliente=  clienteService.findByDocumento(documento);

            response.put("ClientePortal", clientePortal  );
            response.put("Cliente", cliente);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * @param generate
     */
    @GetMapping("/portalweb/generatelink")
    public ResponseEntity<?> generate(@RequestParam Long idContrato , @RequestHeader("Authorization") String token)
    {
        try {
            String url = "https://portalweb.server.internetinalambrico.com.co/#/c3b73a718e2971292c9101fb1b3ea3445754c49261c8ecf513427d84a47bf03c/" + idContrato;
            contratoService.generateLink(idContrato , token);
            Map<String,Object> resp = new HashMap<>();
            resp.put("url" , url);
            return ResponseEntity.status(HttpStatus.OK).body(resp);  
        } catch (Exception e) {
            // TODO: handle exception
            Map<String ,Object> response = new HashMap<>();

            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
