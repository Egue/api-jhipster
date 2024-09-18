package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.comunicamosmas.api.service.IUsuarioService;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.service.dto.userLoginDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql; 
 


@RestController
@RequestMapping("/api/controlmas")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @PutMapping("/usuarios/changepassword/{id}")
    public ResponseEntity<?> changePassword(@RequestParam("password") String newPassword , @PathVariable("id") Long id)
    {
        Map<String, Object> response = new HashMap<>();

        try{

            usuarioService.updatePassword(id , newPassword);

            response.put("response" , "Actualizado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }catch(ExceptionNullSql e)
        {
            response.put("response" , e.getMessage() +":"+e.getDetails() );

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }catch(Exception e)
        {
            response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //controlador para buscar usuarios
    @GetMapping("/usuarios/users")
    public ResponseEntity<?> getUsers(){
        Map<String, Object> response = new HashMap<>();

        try{
            List<userLoginDTO> result = usuarioService.findAllUsers();

            response.put("response", result);
 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }catch(ExceptionNullSql e)
        {
            response.put("response" , e.getMessage() +":"+e.getDetails() );

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }catch(Exception e)
        {
            response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //buscar por name user
    @GetMapping("/usuarios/query/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name){

        Map<String, Object> response = new HashMap<>();

        try{
            List<userLoginDTO> result = usuarioService.findByName(name);

            response.put("response", result);
 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }catch(ExceptionNullSql e)
        {
            response.put("response" , e.getMessage() +":"+e.getDetails() );

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }catch(Exception e)
        {
            response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/usuarios/roles")
    public ResponseEntity<?> roles()
    {
        Map<String,Object > response=new  HashMap<> ();
        try {

            List<ValorStringDTO> result = usuarioService.roles();

             response.put("response", result);
 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            // TODO: handle exception
             response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/usuarios/new")
    public ResponseEntity<?> usersNew(@RequestParam("id")  Integer id , @RequestParam("role") String role)
    {
        Map<String,Object > response=new  HashMap<> ();
        try {

             usuarioService.newUser(id , role);

             response.put("response", "creado");
 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            
        }catch(ExceptionNullSql e){

            response.put("response" , e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: handle exception
             response.put("response" , e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }


    /*
     * init reset passwors
     * @username
     */
    @PostMapping("/usuarios/reset/password-init")
    public ResponseEntity<?> initResetPassword(@RequestBody String username)
    {
        try {

            usuarioService.initResetPassword(username);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/usuarios/reset/password-finish")
    public ResponseEntity<?> validateCode(@RequestBody() ResetPassword resetPassword)
    {
        try {
            Boolean user = usuarioService.finishResetPassword(resetPassword.getCode(), resetPassword.getPassword());
            if(user)
            {
                return ResponseEntity.status(HttpStatus.OK).build();

            } else{

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    static class ResetPassword{
        private String code;

        private String password;

        

        public ResetPassword() {
        }

        public ResetPassword(String code, String password) {
            this.code = code;
            this.password = password;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        
    }
    
}
