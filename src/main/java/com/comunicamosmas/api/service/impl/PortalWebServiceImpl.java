package com.comunicamosmas.api.service.impl;
 
import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;  
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.service.IPortalWebService;
import com.comunicamosmas.api.service.dto.AdminPortalWebDTO;
import com.comunicamosmas.api.service.dto.AdminUserDTO;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql; 

@Service
public class PortalWebServiceImpl implements IPortalWebService{

    private final String url;

    private final RestTemplate restTemplate;

    private final HttpHeaders headers;

    PortalWebServiceImpl()
    {
        this.url = "http://192.168.24.138:8089/api/";
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    @Override
    public void addUser(ClientePortalWebDTO clientePortalWebDTO , String token) {
        // TODO Auto-generated method stub
        try {
            String edpoint = url + "portal/cliente";
  
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.set("Authorization", token);
  
            HttpEntity<ClientePortalWebDTO> cliente = new HttpEntity<>(clientePortalWebDTO , headers);

            ResponseEntity<String> response = restTemplate.exchange(edpoint, HttpMethod.POST, cliente, String.class);
            //System.out.println(clientePortalWebDTO);

        } catch(HttpMessageConversionException e){

            throw new ExceptionNullSql(new Date(), "Error enviando a Portal Web", e.getMessage());

        } catch (Exception e) {

            throw new ExceptionNullSql(new Date(), "Error Inesperado con Portal Web", e.getMessage() );
        }
    }

    

    @Override
    public List<AdminUserDTO> userPortalWeb(String token) {
        // TODO Auto-generated method stub
       try {
        String endpoint = url + "admin/users";
         
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<AdminUserDTO>> responseEntity =restTemplate.exchange(endpoint, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AdminUserDTO>>() {});

        return responseEntity.getBody();
        
       } catch(HttpMessageConversionException e){

        throw new ExceptionNullSql(new Date(), "Error enviando a Portal Web", e.getMessage());

    } catch (Exception e) {

        throw new ExceptionNullSql(new Date(), "Error Inesperado con Portal Web", e.getMessage() );
    }
    }

    @Override
    public void createUser(AdminPortalWebDTO user) {
        // TODO Auto-generated method stub
        try {
        String endpoint = url + "register"; 

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AdminPortalWebDTO> entity = new HttpEntity<>(user ,headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);
        } catch(HttpMessageConversionException e){

            throw new ExceptionNullSql(new Date(), "Error enviando a Portal Web", e.getMessage());
    
        } catch (Exception e) {
    
            throw new ExceptionNullSql(new Date(), "Error Inesperado con Portal Web", e.getMessage() );
        }

    }

    @Override
    public void activate(String token) {
        // TODO Auto-generated method stub
         try {
            String endpoint = url + "activate?key="+token;
            
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, entity , String.class);
         } catch(HttpMessageConversionException e){

            throw new ExceptionNullSql(new Date(), "Error enviando a Portal Web", e.getMessage());
    
        } catch (Exception e) {
    
            throw new ExceptionNullSql(new Date(), "Error Inesperado con Portal Web", e.getMessage() );
        }
    }

    @Override
    public ClientePortalWebDTO findByDocumento(String documento , String token) {
        // TODO Auto-generated method stub
        try {
            String endpoint = url + "portal/cliente/findbydocumento/" + documento;
            
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.set("Authorization" , token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            

            ResponseEntity<ClientePortalJson> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET ,entity , ClientePortalJson.class);

            return responseEntity.getBody().cliente;

        } catch(HttpMessageConversionException e){

            throw new ExceptionNullSql(new Date(), "Error enviando a Portal Web", e.getMessage());
    
        } catch (Exception e) {
    
            throw new ExceptionNullSql(new Date(), "Error Inesperado con Portal Web", e.getMessage() );
        }
    }

    static class ClientePortalJson{
        ClientePortalWebDTO cliente;

        public ClientePortalWebDTO getCliente() {
            return cliente;
        }

        public void setCliente(ClientePortalWebDTO cliente) {
            this.cliente = cliente;
        }
        
    }
    
}
