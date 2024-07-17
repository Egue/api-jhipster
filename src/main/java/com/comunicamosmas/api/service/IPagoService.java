package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Pago;
import com.comunicamosmas.api.service.dto.PagosEstadoCuentaDTO;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;
import com.comunicamosmas.api.service.dto.ReporteMediosPagosDTO;
import com.comunicamosmas.api.service.dto.ReporteSiustOneThreeDTO;
import com.comunicamosmas.api.service.dto.PaymentOnlineDTO.PagosOnline;

import java.util.List;
import java.util.Optional;

public interface IPagoService {
    //listar todos
    public List<Pago> findAll();

    //guardar
    public Pago save(Pago pago);

    public List<Pago> saveAll(List<Pago> pagos);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Pago findById(Long id);

    //reporte rc
    public List<ReciboCajaDTO> reporteReciboCaja(List<Integer> ciudades , Integer fecha_inicial , Integer fecha_final);

    /*Pagos supegiros */
    public void registerPagoSupergiros(Contrato idContrato , int valorTotal , String comprobante);
    /*Pagos Online */
    public void registerPagosOnline(String reference ,   PaymentOnlineDTO.Facturas faturas);

    public int findLastRc(Long idServicio , String origen);

    //reporteMedio
    public List<ReporteMediosPagosDTO> findMedioPago(List<Integer> medio , String inicio , String fin);

    /*Reporte T.1.3 Lineas o accesos y valores facturados o 
        cobrados de servicios fijos individuales */
    public List<ReporteSiustOneThreeDTO>  reporteOneToThree(List<Integer> servicios  , Integer firts , Integer end);
    
    public List<PagosEstadoCuentaDTO> findByIdDeuda(Integer idDeuda);
}
