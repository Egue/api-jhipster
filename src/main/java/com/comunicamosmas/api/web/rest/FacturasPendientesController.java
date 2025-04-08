package com.comunicamosmas.api.web.rest;

import java.util.HashMap; 
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domainMongo.FacturasEmitidas; 
import com.comunicamosmas.api.service.IFacturasServices; 
import com.comunicamosmas.api.service.dto.FacturasControlmasDTO;
import com.comunicamosmas.api.service.dto.InfoFacturaDTO;
import com.comunicamosmas.api.serviceMongo.IFacturasEmitidasService;
 

@RestController
@RequestMapping("/api/controlmas")
public class FacturasPendientesController {

    private final String xToken = "abcdlsmnobvacaswlpwuieoqooiwoqncdhabscdcdcascwcwawc";

    private final IFacturasServices facturasServices;

    private final IFacturasEmitidasService facturasEmitidasService;

    FacturasPendientesController(IFacturasServices facturasServices , IFacturasEmitidasService facturasEmitidasService)
    {
        this.facturasServices = facturasServices;

        this.facturasEmitidasService = facturasEmitidasService;
    }

    @GetMapping("/search/pendient")
    public ResponseEntity<?> search(@RequestParam("cliente") Long idCliente)
    {
        try {
            FacturasControlmasDTO.FacturasPendientes pendient = facturasServices.findFacturaByMouth(idCliente);
            return ResponseEntity.status(HttpStatus.OK).body(pendient);
        } catch (Exception e) {
            // TODO: handle exception
            Map<String, Object> resp = new HashMap<>();

            resp.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @GetMapping("/facturas/findlistbyidcliente")
    public ResponseEntity<?> findListByIdCliente(@RequestParam("cliente") Long idCliente , @RequestParam("init") int page , @RequestParam("size") int size)
	{
		try {
			
			PageRequest pageable =  PageRequest.of(page, size);

			return ResponseEntity.status(HttpStatus.OK).body(
                           facturasEmitidasService.findByIdCliente(idCliente.intValue(), pageable));
		} catch (Exception e) {
			// TODO: handle exception
			Map<String, Object> response = new HashMap<>();

			response.put("response", e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

    @PostMapping("/facturas/info")
    public ResponseEntity<?> facturasInfo(@RequestBody  FacturasEmitidas facturaEmitidas)
    {
         
            try {
                InfoFacturaDTO facturaDTO = facturasServices.informationInFactura(facturaEmitidas);

            return ResponseEntity.ok().body(facturaDTO);
            } catch (Exception e) {
                // TODO: handle exception
                return ResponseEntity.internalServerError().build();
            }
         
    }
    
}
