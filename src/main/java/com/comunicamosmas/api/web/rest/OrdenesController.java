package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.dto.AnoWithListIntegerDTO;
import com.comunicamosmas.api.service.dto.ChartDataLineDTO;
import com.comunicamosmas.api.service.dto.OrdenByContratoDTO;
import com.comunicamosmas.api.service.dto.OrdenByTipoOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class OrdenesController {

    @Autowired
    IOrdenService ordenService;

    @GetMapping("/ordenes/instalacion")
    public ResponseEntity<?> listInstacion() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OrdenInstalacionDTO> ordenInstalacion = ordenService.ordenInstalacion();

            response.put("response", ordenInstalacion);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ordenes/FindByIdOrdeForInstalacion/{id}")
    public ResponseEntity<?> findByIdOrdeForInstalacion(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            OrdenForInstalacionFindByIdOrdenDTO orden = ordenService.ordenForInstalacionFindByIdOrden(id);

            response.put("response", orden);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT
     */
    @PostMapping("/ordenes/instalacion/betwenn")
    public ResponseEntity<?> instalacionBetween(@RequestParam String valor1, @RequestParam String valor2) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OrdenInstalacionDTO> result = ordenService.getListFindBetwee(valor1, valor2);
            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT BUSCAR ORDENES X CONTRATOS
     */
    @GetMapping("/ordenes/listbyidcontrato/{contrato}")
    public ResponseEntity<?> listByIdContrato(@PathVariable Long contrato) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<OrdenByContratoDTO> result = ordenService.listOrdenByIdContrato(contrato);
            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST CREAR ORDEN
     **/
    @PostMapping("/ordenes/save")
    public ResponseEntity<?> save(@RequestParam Long idContrato, @RequestParam Long tipoOrden,
            @RequestParam Long idUser, @RequestParam String nota) {
        Map<String, Object> response = new HashMap<>();
        try {

            Orden orden = new Orden();
            orden.setIdContrato(idContrato);
            orden.setTipoOrden(tipoOrden);
            orden.setIdUsuarioRegistra(idUser);
            System.out.print(orden.toString());
            response.put("response", "ok");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET BUSCAR RECONEXIONES ACTIVAS
     */
    @GetMapping("/ordenes/reconexion/list")
    public ResponseEntity<?> reconexionesList() {
        Map<String, Object> response = new HashMap<>();

        try {
            // listar reconexiones activas
            List<OrdenByTipoOrdenDTO> result = ordenService.listOrdenReconexion();
            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ENDPOINT GET BUSCAR CORTES ACTIVOS
     */
    @GetMapping("/ordenes/cortes/list")
    public ResponseEntity<?> cortesList() {
        Map<String, Object> response = new HashMap<>();

        try {
            // listar reconexiones activas
            List<OrdenByTipoOrdenDTO> result = ordenService.listOrdenesCortes();
            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * BUSCAR ORDENES DE CORTE POR IDSERVICIO Y TIPOCLIENTE
     */
    @PostMapping("/ordenes/cortes/findbyidservicioandtipocliente")
    public ResponseEntity<?> findbyidservicioandtipocliente(@RequestParam Long tipoOrden, @RequestParam Long idServicio,
            @RequestParam String tipoCliente) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<OrdenByTipoOrdenDTO> result = ordenService.listOrdenesByIdServicioAndTipoCliente(tipoOrden, idServicio,
                    tipoCliente);
            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/ordenes/charLine/cortados")
    public ResponseEntity<?> charLineCortados(@RequestBody AnoWithListIntegerDTO datos) {
        Map<String, Object> response = new HashMap<>();

        try {

            ChartDataLineDTO result = ordenService.chartLineOrdenesCortados(datos.getAno(), datos.getArrayInteger());

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (ExceptionNullSql e) {

            response.put("response", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
