package com.comunicamosmas.api.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.domain.Pago;
import com.comunicamosmas.api.domain.SystemConfig; 
import com.comunicamosmas.api.repository.IPagoDao;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.ICacheContratoSaldoService;
import com.comunicamosmas.api.service.IContratoSaldoFavorLogService;
import com.comunicamosmas.api.service.IDeudaService;
import com.comunicamosmas.api.service.IPagoRetencionService;
import com.comunicamosmas.api.service.IPagoService;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;
import com.comunicamosmas.api.service.dto.ReporteMediosPagosDTO;
import com.comunicamosmas.api.service.dto.ReporteSiustOneThreeDTO;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    IPagoDao pagoDao;

    @Autowired
    ISystemConfigDao systemDao;

    @Autowired
    ICacheContratoSaldoService saldoService;

    @Autowired
    IPagoRetencionService retencionService;

    @Autowired
    IDeudaService deudasService;

    @Autowired
    IContratoSaldoFavorLogService saldoFavorLogService;

    @Override
    public List<Pago> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Pago save(Pago pago) {
        // TODO Auto-generated method stub
        return pagoDao.save(pago);
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Pago findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ReciboCajaDTO> reporteReciboCaja(List<Integer> ciudades, Integer fecha_inicial, Integer fecha_final) {

        // TODO Auto-generated method stub
        // buscar el origen de la consulta
        SystemConfig origen = systemDao.findByOrigen("origen");
        String[] caracteres = origen.getComando().split(",");
        List<String> listOrigen = new ArrayList<>(Arrays.asList(caracteres));
        /*
         * <th># R</th>
         * <th>Marca</th>
         * <th>Tipo</th>
         * <th>Contrato </th>
         * <th>Cliente </th>
         * <th>Estrato </th>
         * <th>ID </th>
         * <th>Servicio </th>
         * <th>Concepto</th>
         * <th>Periodo</th>
         * <th>Valor</th>
         * <th>M Pago</th>
         * <th>Gravado</th>
         * <th>Cajero</th>
         * <th>Grupo</th>
         * <th>Barrio</th>
         * <th>Comprobante</th>
         */
        // recibo de caja pagos
        // System.out.println(origen.getComando());
        Optional<List<Object[]>> result = pagoDao.reciboCaja(ciudades, fecha_inicial, fecha_final, listOrigen);

        List<ReciboCajaDTO> reciboCaja = result.map(resp -> resp.stream().map(rs -> {
            ReciboCajaDTO dto = new ReciboCajaDTO();
            dto.setRc((String) rs[0].toString());
            dto.setMarca((String) rs[1].toString());
            dto.setTipo("Pago");
            dto.setContrato((String) rs[2].toString());
            dto.setCliente((String) rs[14].toString());
            dto.setEstrato((String) rs[13].toString());
            dto.setID((String) rs[15].toString());
            dto.setServicio((String) rs[18].toString() + "/" + rs[22].toString());
            // concetp
            String concepto = this.findConcepto((Integer) rs[24], (Integer) rs[25], (Integer) rs[26],
                    (Integer) rs[27], (Integer) rs[28], (String) rs[29]);
            dto.setConcepto(concepto);
            // perido
            String periodo = this.converDateFormat((String) rs[6].toString());
            dto.setPeriodo(periodo);
            dto.setValor((String) rs[7].toString());
            dto.setM_Pago((String) rs[19].toString());
            String iva = Double.parseDouble(rs[12].toString()) > 0.0 ? "Si" : "No";
            dto.setGravado(iva);
            dto.setCajero((String) rs[21]);
            String grupo = rs[10].toString().equals("B") ? "**" : "";
            dto.setGrupo(grupo);
            dto.setBarrio((String) rs[20]);
            dto.setComprobante((String) rs[11].toString());
            return dto;
        }).collect(Collectors.toList())).orElse(new ArrayList<>());
        /*
         * List<ReciboCajaDTO> reciboCaja = result.map(resp -> {
         * 
         * List<ReciboCajaDTO> recibo = new ArrayList<>();
         * 
         * for (Object[] rs : res) {
         * 
         * 
         * recibo.add(dto);
         * }
         * 
         * return recibo;
         * }).orElse(new ArrayList<>());
         */

        // cargar saldo a favor
        List<ReciboCajaDTO> saldo_favor = saldoService.reporte_saldo_favor(ciudades, fecha_inicial, fecha_final,
                listOrigen);
        if (!reciboCaja.isEmpty()) {

            reciboCaja.addAll(saldo_favor);
        }
        List<ReciboCajaDTO> retenciones = retencionService.reporte_recibo_caja(ciudades, fecha_inicial, fecha_final,
                listOrigen);
        if (!retenciones.isEmpty()) {
            reciboCaja.addAll(retenciones);
        }

        return reciboCaja;

    }

    private String findConcepto(Integer instalacion, Integer reconexion, Integer materiales, Integer traslado,
            Integer otros, String concepto) {
        String concepto_aux = "Mensualidad";
        if (instalacion.equals(1)) {
            concepto_aux = "Instalacion";
        } else if (reconexion.equals(1)) {
            concepto_aux = "Reconexión";
        } else if (materiales.equals(1)) {
            concepto_aux = "Materiales";
        } else if (traslado.equals(1)) {
            concepto_aux = "Traslados";
        } else if (otros.equals(1)) {
            concepto_aux = concepto;
        }

        return concepto_aux;
    }

    private String converDateFormat(String mesServicio) {
        int year = Integer.parseInt(mesServicio.substring(0, 4));
        int month = Integer.parseInt(mesServicio.substring(4));

        String monString = "";
        switch (month) {
            case 1:
                monString = "Enero";
                break;
            case 2:
                monString = "Febrero";
                break;
            case 3:
                monString = "Marzo";
                break;
            case 4:
                monString = "Abril";
                break;
            case 5:
                monString = "Mayo";
                break;
            case 6:
                monString = "Junio";
                break;
            case 7:
                monString = "Julio";
                break;
            case 8:
                monString = "Agosto";
                break;
            case 9:
                monString = "Septiembre";
                break;
            case 10:
                monString = "Octubre";
                break;
            case 11:
                monString = "Noviembre";
                break;
            case 12:
                monString = "Diciembre";
            default:

                break;
        }
        return monString + "/" + year;

    }

    @Override
    public void registerPagoSupergiros(Contrato contrato, int valorTotal , String comprobante) {
        // TODO Auto-generated method stub
        if (contrato != null) {
            // consultar deudas del contrato de la menor a la mayor
            List<DeudasForFacturaDTO> deudas = deudasService.deudasByIdContrato(contrato.getId());
            Float resultado = (float) valorTotal;
            List<Pago> listPago = new ArrayList<>();
            String uniqueId = UUID.randomUUID().toString();
            int reciboCaja = this.findLastRc(contrato.getIdServicio(), contrato.getGrupo());

            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");

            for (DeudasForFacturaDTO rs : deudas) {

                Float valor_parcial = (float) (rs.getValor_total() - rs.getValor_parcial());

                Deuda deuda = deudasService.findById(rs.getId_deuda().longValue());

                float valorDado = 0L;

                if (resultado > 0L) {
                    if (resultado >= valor_parcial) { //12000 > 500
                        resultado = resultado - valor_parcial;
                        valorDado = valor_parcial;
                        Float parcial = rs.getValor_parcial() + valor_parcial;
                        deuda.setEstado(2L);
                        deuda.setValorParcial(parcial.doubleValue());
                    } else {
                        Float parcial = rs.getValor_parcial() + resultado;
                        valorDado = resultado;
                        resultado = resultado - resultado;
                        deuda.setEstado(3L);
                        deuda.setValorParcial(parcial.doubleValue());
                    }
                    deudasService.save(deuda);
                    //register pago
                    
                    Pago pago = new Pago();
                    pago.setIdReciboCaja(((long)(reciboCaja + 1)));
                    pago.setIdCiudad(contrato.getIdCiudad());
                    pago.setIdServicio(contrato.getIdServicio());
                    pago.setIdDeuda(rs.getId_deuda().longValue());
                    pago.setIdCliente(rs.getId_cliente().longValue());
                    pago.setIdCajero(2L);
                    
                    pago.setFechaf( Long.parseLong(fechaActual.format(formato)));
                    pago.setIdMedioPago(32L);
                    pago.setComprobante(comprobante);
                    pago.setValorDado(valorDado);
                    pago.setValorCobro(valorDado);
                    pago.setValorVueltas((float) 0);
                    pago.setValorRedondeo((float) 0);
                    pago.setEstado(1L);
                    pago.setAnulaIdUsuario(0L);
                    pago.setAnulaMarca("");
                    pago.setAnulaJustifica("");
                    pago.setLugar(contrato.getGrupo());
                    pago.setTurno(uniqueId);
                    pago.setIdContrato(contrato.getId());
                    pago.setIdEmpresa(contrato.getIdEmpresa());
                    pago.setMesServicio((Long)rs.getMes_servicio().longValue());
                    pago.setInstalacion(0L);
                    pago.setReconexion(0L);
                    pago.setMateriales(0L);
                    listPago.add(pago);
                    
                    //
                }else{
                    break;
                }
                //update deuda
                               
            }
            saveAll(listPago);
            //validar si queda saldo
            if(resultado > 0)
            {
                this.saldoFavorLogService.addSaldoBySupergiros(contrato, resultado, uniqueId ,fechaActual.format(formato) );
            }
        }
    }

    @Override
    public List<Pago> saveAll(List<Pago> pagos) {
        // TODO Auto-generated method stub
        return (List<Pago>) pagoDao.saveAll(pagos);
    }

    @Override
    public int findLastRc(Long idServicio , String origen) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = pagoDao.findLastRc(idServicio , origen);
        int recibo_pago = result.map(resp->{
            int rc = 0;
            for(Object[] rs : resp){
                rc = (int) rs[0];
            }

            return rc;
        }).orElse(0);
        return recibo_pago;
    }

    @Override
    public List<ReporteMediosPagosDTO> findMedioPago(List<Integer> medio, String inicio, String fin) {
    
        Optional<List<Object[]>> result = pagoDao.pagosByMedioPago(medio, inicio, fin);        
        List<ReporteMediosPagosDTO> pagos = result.map(resp -> resp.stream().map(rs -> {
            ReporteMediosPagosDTO obj = new ReporteMediosPagosDTO();
            obj.setCodigo((int)rs[0]);
            obj.setRc((int) rs[1]);
            obj.setCiudad((String) rs[2]);
            obj.setServicio((String) rs[3]);
            obj.setCliente((String) rs[4]);
            obj.setCajero((String) rs[5]);
            obj.setValor((float) rs[6]);
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String timestampAsString = dateFormat.format(rs[7]);
            obj.setMarca(timestampAsString);
            obj.setOrigen((String) rs[8]);
            obj.setContrato((int) rs[9]);
            obj.setPayments((String) rs[10]);
            return obj;
        }).collect(Collectors.toList())).orElse(new ArrayList<>()); 

        return pagos;
    }

    @Override
    public List<ReporteSiustOneThreeDTO>  reporteOneToThree(List<Integer> servicios, Integer firts, Integer end) {
        // TODO Auto-generated method stub
            /*headerRow.createCell(1).setCellValue("Tipo Cliente");
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
                headerRow.createCell(13).setCellValue("NC IVA"); */ 
            Optional<List<Object[]>> result = pagoDao.reporteSIUSTOneToThree(servicios, firts, end);
            List<ReporteSiustOneThreeDTO> reporte = result.map(resp -> resp.stream().map(rs->{
                ReporteSiustOneThreeDTO obj = new ReporteSiustOneThreeDTO();
                obj.setServicio((String) rs[0]);
                obj.setTipoCliente((String) rs[1]);
                obj.setDeuda((Integer) rs[2]);
                obj.setContrato((Integer) rs[3]);
                obj.setFactura((Integer) rs[4]);
                obj.setBase((Double) rs[5]);
                obj.setIva((float) rs[6]);
                obj.setTarifa((Integer) rs[7]);
                obj.setEstrato((Integer) rs[8]);
                obj.setVelocidad((Integer) rs[9]);
                obj.setTecnologia((Integer) rs[10]);
                obj.setConcepto((String) rs[11]);
                obj.setNcBase((Double) rs[12]);
                obj.setNcIva((Double) rs[13]);
                return obj;
            }).collect(Collectors.toList())).orElse(new ArrayList<>());
            return reporte;
    }

}
