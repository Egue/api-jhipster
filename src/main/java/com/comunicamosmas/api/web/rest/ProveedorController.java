package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Proveedor;
import com.comunicamosmas.api.service.IProveedorService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    /**
     * ENDPOINT GET
     * */
    @GetMapping("/proveedor/list")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Proveedor> listProveedor = proveedorService.findAll();

            response.put("response", listProveedor);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET*/
    @GetMapping("/proveedor/findByLikeRazonSocial/{name}")
    public ResponseEntity<?> findByLikeRazonSocial(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Proveedor> findByRazonSocial = proveedorService.findByLikeRazonSocial(name);

            response.put("response", findByRazonSocial);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/proveedor/listByEstado")
    public ResponseEntity<?> findByEstado() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Proveedor> listProveedor = proveedorService.findByEstadoActivo(1);

            response.put("response", listProveedor);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET findByProveedor*/
    /*@GetMapping("/proveedor/findByProveedor/{id}")
	public ResponseEntity<?>findByProveedor(@PathVariable Long id)
	{
		Map<String , Object> response = new HashMap<>();
		try {
			Proveedor proveedor = proveedorService.findById(id);
			
			response.put("response", proveedor);
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.OK);
			
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response , HttpStatus.BAD_REQUEST); 
		}
	}*/
    //* ENDPOINT GET findById*/
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<?> findProveedorById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Proveedor proveedor = proveedorService.findById(id);
            response.put("response", proveedor);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT POST save*/
    @PostMapping("/proveedor/save")
    public ResponseEntity<?> save(@RequestBody Proveedor proveedor) {
        Map<String, Object> response = new HashMap<>();

        try {
            proveedorService.save(proveedor);
            response.put("response", "creado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT updated*/
    @PutMapping("/proveedor/{id}")
    public ResponseEntity<?> update(@RequestBody Proveedor proveedor, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            //consultar por id_proveedor
            Proveedor proveedor1 = proveedorService.findById(id);

            proveedor1.setRazonSocial(proveedor.getRazonSocial());
            proveedor1.setNit(proveedor.getNit());
            proveedor1.setDireccion(proveedor.getDireccion());
            proveedor1.setTelefono(proveedor.getTelefono());
            proveedor1.setCorreo(proveedor.getCorreo());
            proveedor1.setArchivoRut(proveedor.getArchivoRut());
            proveedor1.setArchivoCamara(proveedor.getArchivoCamara());
            proveedor1.setEstado(proveedor.getEstado());

            proveedorService.save(proveedor1);

            response.put("response", "Actualizado con éxito");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
