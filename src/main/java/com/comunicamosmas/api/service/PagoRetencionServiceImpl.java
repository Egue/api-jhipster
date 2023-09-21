package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.PagoRetencion;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IPagoRetencionDao;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoRetencionServiceImpl implements IPagoRetencionService {

    @Autowired
    IPagoRetencionDao retencionDao;

    @Override
    public List<PagoRetencion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public PagoRetencion save(PagoRetencion pagoRetencion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public PagoRetencion findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ReciboCajaDTO> reporte_recibo_caja(List<Integer> ciudad, Integer inicio, Integer fecha_final,
            List<String> tipo) {

        Optional<List<Object[]>> result = retencionDao.reporte_retenciones(ciudad, inicio, fecha_final, tipo);

        List<ReciboCajaDTO> retenciones = result.map(resp -> {

            List<ReciboCajaDTO> rete = new ArrayList<>();

            for (Object[] rs : resp) {

                /*
                 * if(rs[7].toString().equals("1")){rete.add(this.filter_retencion(rs,
                 * "Retefuente", rs[12].toString()));}
                 * if(rs[8].toString().equals("1")){rete.add(this.filter_retencion(rs,
                 * "ReteICA", rs[13].toString()));}
                 * if(rs[9].toString().equals("1")){rete.add(this.filter_retencion(rs,
                 * "Bomberil", rs[14].toString()));}
                 * if(rs[10].toString().equals("1")){rete.add(this.filter_retencion(rs,
                 * "ReteIVA", rs[15].toString()));}
                 * if(rs[11].toString().equals("1")){rete.add(this.filter_retencion(rs,
                 * "Otro Impuestos", rs[16].toString()));}
                 */
                String[] nombresImpuestos = { "Retefuente", "ReteICA", "Bomberil", "ReteIVA", "Otro Impuestos" };

                for (int i = 7; i <= 11; i++) {
                    if (rs[i].toString().equals("1")) {
                        rete.add(this.filter_retencion(rs, nombresImpuestos[i - 7], rs[i + 5].toString()));
                    }
                }
            }

            return rete;
        }).orElse(new ArrayList<>());

        return retenciones;
    }

    private ReciboCajaDTO filter_retencion(Object[] rs, String tipo, String valor) {
        ReciboCajaDTO obj = new ReciboCajaDTO();
        obj.setRc((String) rs[0].toString());
        obj.setMarca((String) rs[1].toString());
        obj.setContrato((String) rs[2].toString());
        obj.setCliente((String) rs[3].toString());
        obj.setEstrato((String) rs[4].toString());
        obj.setID((String) rs[5].toString());
        obj.setServicio((String) rs[6]);
        obj.setTipo(tipo);
        obj.setValor(valor);
        obj.setCajero((String) rs[19]);
        obj.setBarrio((String) rs[18]);
        obj.setConcepto("Retenciones");
        String grupo = rs[17].toString().equals("B") ? "**" : "";

        obj.setGrupo(grupo);
        obj.setM_Pago((String) rs[20]);
        if(!rs[21].toString().isEmpty())
        {
            String periodo = this.converDateFormat(rs[21].toString());
            obj.setPeriodo(periodo);
        }
        obj.setComprobante((String) rs[22].toString());
        
        return obj;
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
