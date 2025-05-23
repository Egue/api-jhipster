package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.dto.ChartDataLineDTO;
import com.comunicamosmas.api.service.dto.OrdenByContratoDTO;
import com.comunicamosmas.api.service.dto.OrdenByTipoOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO;
import com.comunicamosmas.api.service.dto.OrderDetalleDTO;
import com.comunicamosmas.api.service.dto.OrdesQueryDTO;
import com.comunicamosmas.api.service.dto.ReporteOrdenConVisitaFallidaDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrdenService {
	//buscar por tipo y activo
	public List<Orden> findAllByTipo(Long idTipo);
    //listar todos
    public List<Orden> findAll();

    //guardar
    public Orden save(Orden orden);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Orden findById(Long id);

    public List<OrdenInstalacionDTO> ordenInstalacion();

    public OrdenForInstalacionFindByIdOrdenDTO ordenForInstalacionFindByIdOrden(Long id);

    public String findTelefonoByIdOrden(Long idOrden);

    public List<OrdenInstalacionDTO> getListFindBetwee(String valor1, String valor2);
    
    public List<OrdenByContratoDTO> listOrdenByIdContrato(Long idContrato);
    
    //buscar reconexiones activas
    public List<OrdenByTipoOrdenDTO> listOrdenReconexion();
    
    //buscar ordenes por cortes
    public List<OrdenByTipoOrdenDTO> listOrdenesCortes();
    
    //buscar ordene de corte x id_servicio tipo_cliente
    public List<OrdenByTipoOrdenDTO> listOrdenesByIdServicioAndTipoCliente(Long tipoOrden, Long idServicio , String tipoCliente);
    
    //
    public Orden findLastRegisterByRefiere(String refiere , Long idServicio);

    //CREANDO ORDENES
    public void reconexion(Contrato contrato , String comentario);
    
    //buscando ordenes activas por tipo
    public Orden findOrdenActivaByTipo(Long tipoOrden , Long idContrato);
    
    //anular una orden
    public void anularOrden(Orden orden ,  String comentario);

    //reporte de ordenes de servicio por tipo fecha y visita fallidas
    public List<ReporteOrdenConVisitaFallidaDTO> reporteConVisitaFallida(Long idServicio, Long tipo , String iniciof, String finalf);

    //
    public ChartDataLineDTO chartLineOrdenesCortados(Integer ano , List<Integer> servicios);
    //crear orden de cambio de plan
    public void ordenCambioPlan(Long idContrato , Long idAdmin);	

    public Page<OrderDetalleDTO> ordenes(Long abierta, Long anulada , List<Integer> estado, Long idServicio, Long idTipo, Long fechaInicio , Long fechaFinal, Pageable pageable);
}
