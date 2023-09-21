package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Pago;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.IPagoDao;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.ICacheContratoSaldoService;
import com.comunicamosmas.api.service.IPagoRetencionService;
import com.comunicamosmas.api.service.IPagoService;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

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

    @Override
    public List<Pago> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Pago save(Pago pago) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
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

}
