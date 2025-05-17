package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.dto.AnoWithListIntegerDTO;
import com.comunicamosmas.api.service.dto.ChartDataLineDTO;
import com.comunicamosmas.api.service.dto.OrdenByContratoDTO;
import com.comunicamosmas.api.service.dto.OrdenByTipoOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO;
import com.comunicamosmas.api.service.dto.OrderDetalleDTO; 
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("ordenes")
    public ResponseEntity<?> ordenes(
        @RequestParam(defaultValue = "1") Long abierta,
        @RequestParam(defaultValue = "0") Long anulada,
        @RequestParam() List<Integer> estado,
        @RequestParam() Long idServicio ,
        @RequestParam() Long idTipo , 
        @RequestParam(required = false) Long fechaInicio , 
        @RequestParam(required = false) Long fechaFinal,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)
    {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Long fechaInicioQuery = fechaInicio != null ? fechaInicio : 20180101;
            LocalDate local =  LocalDate.now();
            Long fechaFinalQuery = fechaFinal != null ? fechaFinal : Integer.valueOf(local.format(DateTimeFormatter.BASIC_ISO_DATE));

        Page<OrderDetalleDTO> order = ordenService.ordenes(abierta, anulada ,estado ,idServicio, idTipo, fechaInicioQuery , fechaFinalQuery, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (Exception e) {
            // TODO: handle exception

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /*@GetMapping("/ordenes/withasignation")
    public ResponseEntity<?> ordenesWithAsignation(
        @RequestParam(defaultValue = "1") Long abierta,
        @RequestParam(defaultValue = "1,2,3") List<Integer> estado,
        @RequestParam() Long idServicio ,
        @RequestParam() Long idTipo ,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)
    {
         
    }*/

}
