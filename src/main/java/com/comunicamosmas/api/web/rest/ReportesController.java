package com.comunicamosmas.api.web.rest;

import java.io.ByteArrayOutputStream; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.service.IContratoSaldoFavorLogService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IHanRetirosService;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.IPagoService;
import com.comunicamosmas.api.service.ISoporteTicketService;
import com.comunicamosmas.api.service.dto.AnoWithListIntegerDTO;
import com.comunicamosmas.api.service.dto.ArrayListDTO;
import com.comunicamosmas.api.service.dto.CarteraDTO;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;
import com.comunicamosmas.api.service.dto.ReporteHanRetirosDTO;
import com.comunicamosmas.api.service.dto.ReporteMediosPagosDTO;
import com.comunicamosmas.api.service.dto.ReporteOrdenConVisitaFallidaDTO;
import com.comunicamosmas.api.service.dto.ReporteSiustOneThreeDTO; 
import com.comunicamosmas.api.service.dto.SoporteTicketDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
 
import io.jsonwebtoken.io.IOException;
//import io.jsonwebtoken.lang.Arrays;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/api/controlmas")
public class ReportesController {

    @Autowired
    IContratoService contratoService;

    @Autowired
    IOrdenService ordenService;

    @Autowired
    ISoporteTicketService ticketService;

    @Autowired
    IHanRetirosService retirosService;

    @Autowired
    IPagoService pagosService;

    @Autowired
    IContratoSaldoFavorLogService saldoFavorLogService;

    @PostMapping("/reportes/cartera")
    public ResponseEntity<Resource> carteraByIdServicio(@RequestBody ArrayListDTO datosDto) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<CarteraDTO> result = contratoService.carteraByServicio(datosDto);

            if (result == null) {
                return ResponseEntity.notFound().build();
            }

            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Cartera");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Cus");
            headerRow.createCell(1).setCellValue("Físico");
            headerRow.createCell(2).setCellValue("Zona");
            headerRow.createCell(3).setCellValue("Tipo C");
            headerRow.createCell(4).setCellValue("Cliente");
            headerRow.createCell(5).setCellValue("Teléfono");
            headerRow.createCell(6).setCellValue("-");
            headerRow.createCell(7).setCellValue("Tipo");
            headerRow.createCell(8).setCellValue("Dirección");
            headerRow.createCell(9).setCellValue("Servicio");
            headerRow.createCell(10).setCellValue("Velocidad/Canale");
            headerRow.createCell(11).setCellValue("Meses debe");
            headerRow.createCell(12).setCellValue("Saldo");
            headerRow.createCell(13).setCellValue("Registro");
            headerRow.createCell(14).setCellValue("Inicio");
            headerRow.createCell(15).setCellValue("Último pago");
            headerRow.createCell(16).setCellValue("Tarifa");
            headerRow.createCell(17).setCellValue("Tarifa Nombre");
            headerRow.createCell(18).setCellValue("Tipo Banda");
            headerRow.createCell(19).setCellValue("Estado");
            headerRow.createCell(20).setCellValue("Fecha Estado");
            headerRow.createCell(21).setCellValue("Estrato");
            headerRow.createCell(22).setCellValue("Tipo tecnologia");
            headerRow.createCell(23).setCellValue("Modalidad");
            headerRow.createCell(24).setCellValue("Vendedor");
            headerRow.createCell(25).setCellValue("Correo");
            headerRow.createCell(26).setCellValue("Estación");

            int rowNum = 1;

            for (CarteraDTO registro : result) {
                Row row = sheet.createRow(rowNum++);
                String origen = registro.getFf_grupocontra().equals("B") ? "*" : null;
                row.createCell(0).setCellValue(registro.getFf_iddelcontrato() + origen);
                row.createCell(1).setCellValue(registro.getFf_fisico());
                row.createCell(2).setCellValue(registro.getZona_nombre());
                row.createCell(3).setCellValue(registro.getCliente_tipo_cliente());
                row.createCell(4).setCellValue(registro.getNombre_cliente() + "/" + registro.getCliente_documento());
                row.createCell(5).setCellValue(registro.getContacto());
                row.createCell(6).setCellValue(
                        registro.getDirr_barrio() + " " + registro.getMunicipio() + " " + registro.getDepartamento());
                row.createCell(7).setCellValue(registro.getDirr_tipo());
                row.createCell(8).setCellValue(registro.getDireccion());
                row.createCell(9).setCellValue(registro.getServicios_nombre());
                row.createCell(10).setCellValue(registro.getVelocidad() + "/" + registro.getNumero_canales());
                row.createCell(11).setCellValue(registro.getMesesdebe());
                Double saldo = registro.getTotal_debe() - registro.getTotal_abonos();
                row.createCell(12).setCellValue(saldo);
                row.createCell(13).setCellValue(registro.getFf_marcacontrato());
                row.createCell(14).setCellValue(registro.getFf_iniciocontrato());
                row.createCell(15).setCellValue(registro.getUltimo_pago());
                row.createCell(16)
                        .setCellValue(registro.getTarifa_general_valor() + "/" + registro.getTarifa_promo_valor());
                row.createCell(17).setCellValue(registro.getTarifa_nombre());
                row.createCell(18).setCellValue(registro.getT_tipo_banda());
                row.createCell(19).setCellValue(registro.getFf_estadocontrato());
                row.createCell(20).setCellValue(registro.getFecha_ultimo_estado());
                row.createCell(21).setCellValue(registro.getEstrato_contrato());
                row.createCell(22).setCellValue(registro.getT_tipo_tecnologia());
                row.createCell(23).setCellValue(registro.getModalidad());
                row.createCell(24).setCellValue(registro.getVendedor());
                row.createCell(25).setCellValue(registro.getMail());
                row.createCell(26).setCellValue(registro.getEstacion());
                origen = null;
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            workbook.close();

            ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=registros.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        } catch (ExceptionNullSql e) {
            response.put("error", e.getMessage() + "-" + e.getDetails());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        }
    }

    // reporte por ordenes de servicio
    @PostMapping("/reportes/ordenes/tipo")
    public ResponseEntity<?> ordenesByTipo(@RequestParam("idServicio") Long idServicio, @RequestParam("tipo") Long tipo,
            @RequestParam("inicio") String inicio, @RequestParam("final") String finalf) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<ReporteOrdenConVisitaFallidaDTO> result = ordenService.reporteConVisitaFallida(idServicio, tipo,
                    inicio, finalf);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("error", e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reportes/tickets")
    public ResponseEntity<?> reporteTickets(@RequestParam("tipo") Long tipo, @RequestParam("inicio") Long inicio,
            @RequestParam("final") Long finalf) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<SoporteTicketDTO> result = ticketService.reporteTicket(tipo, inicio, finalf);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /** Reporte de retirados de han_retirados */
    @PostMapping("/reportes/hanretiros/all")
    public ResponseEntity<?> hanRetirosAll(@RequestBody AnoWithListIntegerDTO datos) {
        Map<String, Object> response = new HashMap<>();

        try {

            String estado = "";
            switch (datos.getAno()) {
                case 1:
                    estado = "Abierto";
                    break;
                case 2:
                    estado = "Cerrado";
                    break;
            }
            List<ReporteHanRetirosDTO> result = retirosService.reporteHanRetiros(datos.getArrayInteger(), estado);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {

            response.put("error", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // *RECIBOS DE CAJA POR LISTA DE CIUDADES Y RANGO DE FECHAS */
    @PostMapping("/reportes/recibos-caja")
    public ResponseEntity<Resource> recibosCaja(@RequestParam List<Integer> ciudades,
            @RequestParam Integer fecha_inicial,
            @RequestParam Integer fecha_final) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<ReciboCajaDTO> recibos = pagosService.reporteReciboCaja(ciudades, fecha_inicial, fecha_final);
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Rc");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("#R");
            headerRow.createCell(1).setCellValue("Marca");
            headerRow.createCell(2).setCellValue("Tipo");
            headerRow.createCell(3).setCellValue("Contrato");
            headerRow.createCell(4).setCellValue("Cliente");
            headerRow.createCell(5).setCellValue("Estrato");
            headerRow.createCell(6).setCellValue("ID");
            headerRow.createCell(7).setCellValue("Servicio");
            headerRow.createCell(8).setCellValue("Concepto");
            headerRow.createCell(9).setCellValue("Periodo");
            headerRow.createCell(10).setCellValue("Valor");
            headerRow.createCell(11).setCellValue("M Pago");
            headerRow.createCell(12).setCellValue("Gravado");
            headerRow.createCell(13).setCellValue("Cajero");
            headerRow.createCell(14).setCellValue("Grupo");
            headerRow.createCell(15).setCellValue("Barrio");
            headerRow.createCell(16).setCellValue("Comprobante");

            // List<SoporteTicketDTO> result = ticketService.reporteTicket(tipo, inicio,
            int rowNum = 1;
            for (ReciboCajaDTO registro : recibos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(registro.getRc());
                row.createCell(1).setCellValue(registro.getMarca());
                row.createCell(2).setCellValue(registro.getTipo());
                row.createCell(3).setCellValue(registro.getContrato());
                row.createCell(4).setCellValue(registro.getCliente());
                row.createCell(5).setCellValue(registro.getEstrato());
                row.createCell(6).setCellValue(registro.getID());
                row.createCell(7).setCellValue(registro.getServicio());
                row.createCell(8).setCellValue(registro.getConcepto());
                row.createCell(9).setCellValue(registro.getPeriodo());
                row.createCell(10).setCellValue(registro.getValor());
                row.createCell(11).setCellValue(registro.getM_Pago());
                row.createCell(12).setCellValue(registro.getGravado());
                row.createCell(13).setCellValue(registro.getCajero());
                row.createCell(14).setCellValue(registro.getGrupo());
                row.createCell(15).setCellValue(registro.getBarrio());
                row.createCell(16).setCellValue(registro.getComprobante());
                // ... Agregar más celdas según tus variables
            }
            // finalf);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            workbook.close();

            ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=registros.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        }
    }

    /* Reporte de pagos por medio_pago independiente de la empresa , fecha */

    @GetMapping("/reportes/medios-pago")
    public ResponseEntity<Resource> mediosPago(@RequestParam List<Integer> payment, @RequesParam String first,
            @RequesParam String last) {
        try {
            List<ReporteMediosPagosDTO> result = pagosService.findMedioPago(payment, first, last);
            List<ReporteMediosPagosDTO> saldoFavor = saldoFavorLogService.findByMedioPago(payment, first, last);
            List<ReporteMediosPagosDTO> union = new ArrayList<>(result);
            union.addAll(saldoFavor);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("MedioPago");
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Código");
            headerRow.createCell(1).setCellValue("#Rc");
            headerRow.createCell(2).setCellValue("Ciudad");
            headerRow.createCell(3).setCellValue("Servicio");
            headerRow.createCell(4).setCellValue("Cliente");
            headerRow.createCell(5).setCellValue("Cajero");
            headerRow.createCell(6).setCellValue("Valor");
            headerRow.createCell(7).setCellValue("Marca");
            headerRow.createCell(8).setCellValue("Origen");
            headerRow.createCell(9).setCellValue("Contrato");
            headerRow.createCell(10).setCellValue("Medio de pago");
            
             int rowNum = 1;
             for(ReporteMediosPagosDTO rs: union)
             {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getCodigo());
                row.createCell(1).setCellValue(rs.getRc());
                row.createCell(2).setCellValue(rs.getCiudad());
                row.createCell(3).setCellValue(rs.getServicio());
                row.createCell(4).setCellValue(rs.getCliente());
                row.createCell(5).setCellValue(rs.getCajero());
                row.createCell(6).setCellValue(rs.getValor());
                row.createCell(7).setCellValue(rs.getMarca());
                row.createCell(8).setCellValue(rs.getOrigen());
                row.createCell(9).setCellValue(rs.getContrato());
                row.createCell(10).setCellValue(rs.getPayments());
             }
             

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            workbook.close();

            ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=registros.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
        }
    }

    /**
     * REPORTE SIUST 1.3.FORMATO T.1.3. LÍNEAS O ACCESOS Y VALORES FACTURADOS O COBRADOS DE
        SERVICIOS FIJOS INDIVIDUALES Y EMPAQUETADOS
        //@param(List<Integer> servicios)
        //@param(Integer firts , Integer end)
     */
    @GetMapping("/reportes/siust13")
    public ResponseEntity<Resource> reporteSiustOneThree(@RequestParam("servicios") List<Integer> servicios, @RequestParam("firts") Integer firts , @RequestParam("end") Integer end)
    {
        try {
                //List<String> serviciosList = Arrays.asList(servicios.split(","));
                List<ReporteSiustOneThreeDTO> result = pagosService.reporteOneToThree(servicios, firts, end);
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("SIUST13");
                // Crear encabezados
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Servicio");
                headerRow.createCell(1).setCellValue("Tipo Cliente");
                headerRow.createCell(2).setCellValue("Deuda");
                headerRow.createCell(3).setCellValue("Contrato");
                headerRow.createCell(4).setCellValue("Factura");
                headerRow.createCell(5).setCellValue("Base");
                headerRow.createCell(6).setCellValue("IVA");
                headerRow.createCell(7).setCellValue("# Tarifa");
                headerRow.createCell(8).setCellValue("Estrato");
                headerRow.createCell(9).setCellValue("Velocidad");
                headerRow.createCell(10).setCellValue("Tecnologia");
                headerRow.createCell(11).setCellValue("Concepto");
                headerRow.createCell(12).setCellValue("NC Base");
                headerRow.createCell(13).setCellValue("NC IVA");
                int rowNum = 1;
                 for(ReporteSiustOneThreeDTO rs : result)
                 {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(rs.getServicio());
                        row.createCell(1).setCellValue(rs.getTipoCliente());
                        row.createCell(2).setCellValue(rs.getDeuda());
                        row.createCell(3).setCellValue(rs.getContrato());
                        row.createCell(4).setCellValue(rs.getFactura());
                        row.createCell(5).setCellValue(rs.getBase());
                        row.createCell(6).setCellValue(rs.getIva());
                        row.createCell(7).setCellValue(rs.getTarifa());
                        row.createCell(8).setCellValue(rs.getEstrato());
                        row.createCell(9).setCellValue(rs.getVelocidad());
                        row.createCell(10).setCellValue(rs.getTecnologia());
                        row.createCell(11).setCellValue(rs.getConcepto());
                        row.createCell(12).setCellValue(rs.getNcBase());
                        row.createCell(13).setCellValue(rs.getNcIva());
                 }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                workbook.write(byteArrayOutputStream);
                workbook.close();
    
                ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
    
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=registros.xlsx")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
    
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
            } catch (Exception e) {
    
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ByteArrayResource(e.getMessage().getBytes()));
            }
    }
}
