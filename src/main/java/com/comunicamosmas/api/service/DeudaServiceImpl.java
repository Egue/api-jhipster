package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.Deuda; 
import com.comunicamosmas.api.repository.IDeudaDao;
import com.comunicamosmas.api.repository.IPagoDao;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;
import com.comunicamosmas.api.service.dto.PagosEstadoCuentaDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeudaServiceImpl implements IDeudaService {

	@Autowired
	IDeudaDao deudaDao;
	
	@Autowired
	IPagoDao pagoDao;
	
	@Override
	public List<Deuda> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deuda save(Deuda deuda) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deuda findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EstadoCuentaDeudasDTO> findByIdContrato(Long contrato) {
		List<Object[]> deudas = deudaDao.findByIdContrato(contrato);
		List<EstadoCuentaDeudasDTO> listDeudas = new ArrayList<>();
		//recorremos la lista para mappear y consultar pagos, nc, saldo favor
		for(Object[] rs : deudas)
		{
			EstadoCuentaDeudasDTO object = new EstadoCuentaDeudasDTO();
			object.setIdDeuda((Integer) rs[0]);
			object.setFactura((Integer) rs[1]);
			object.setPeriodo((Integer) rs[2]);
			object.setGenerador((String) rs[3]);
			object.setValor((Double) rs[4]);
			object.setAbono((Float) rs[5]);
			//consultamos pagos
			List<Object[]> resultPagos = pagoDao.findByIdDeuda((Integer)rs[0]);
			List<PagosEstadoCuentaDTO> pagos = new ArrayList<>();
			for(Object[] rsPa : resultPagos)
			{
				PagosEstadoCuentaDTO obj = new PagosEstadoCuentaDTO();
				obj.setIdPago((Integer) rsPa[0]);
				obj.setReciboCaja((Integer) rsPa[1]);
				obj.setValorCobrado((Float) rsPa[2]);
				obj.setMarca((Timestamp) rsPa[3]);
				pagos.add(obj);
			}
			object.setPagos((ArrayList) pagos);
			listDeudas.add(object);
		}
		return listDeudas;
	}

	@Override
	public String findDeudaByIdContrato(Long idContrato) {
		// TODO Auto-generated method stub
		return deudaDao.findDeudaByIdContrato(idContrato);
	}

	@Override
	public List<DeudasForFacturaDTO> findDeudaByFacturaAndMesServiceAndIdEmpresa(Long factura, Long mesServicio, Long idEmpresa) {
		// TODO Auto-generated method stub
		List<Object[]>  result = deudaDao.findDeudaByFacturaAndMesServiceAndIdEmpresa(factura, mesServicio, idEmpresa);
		
		if(result.isEmpty())
		{
			throw new ExceptionNullSql(new Date(), "Consulta Vacia" , "no se encontro deudas para la factura");
		}
		 
		List<DeudasForFacturaDTO> deudas =  new ArrayList<DeudasForFacturaDTO>();
		for(Object[] rs: result)
		{
			DeudasForFacturaDTO obj = new DeudasForFacturaDTO();
			obj.setId_deuda((Integer) rs[0]);
			obj.setId_cliente((Integer) rs[1]);
			obj.setRefiere((String) rs[2]);
			obj.setEstado((Integer) rs[3]);
			obj.setFactura((Integer) rs[4]);
			obj.setServicio((String) rs[5]);
			obj.setId_tarifa((Integer) rs[6]);
			obj.setValor_base((Double) rs[7]);
			obj.setValor_iva((Float) rs[8]);
			obj.setValor_reteiva((Float) rs[9]);
			obj.setValor_refuente((Float) rs[10]);
			obj.setValor_reteica((Float) rs[11]);
			obj.setValor_bomberil((Float) rs[12]);
			obj.setValor_otrosimp((Float) rs[13]);
			obj.setValor_parcial((Float) rs[14]);
			obj.setValor_total((Double) rs[15]);
			obj.setValor_intereses((Integer) rs[16]);
			obj.setId_contrato((Integer) rs[17]);
			obj.setId_servicio((Integer) rs[18]);
			obj.setId_empresa((Integer) rs[19]);
			obj.setMarca((Timestamp) rs[20]);
			obj.setId_ciudad((Integer) rs[21]);
			obj.setMes_servicio((Integer) rs[22]);
			obj.setFechaf_genera((Integer) rs[23]);
			obj.setFechaf_corte((Integer) rs[24]);
			obj.setFecha_limite((Integer) rs[25]);
			obj.setRepetitivo((Integer) rs[26]);
			obj.setPucc((String) rs[27]);
			obj.setNiff((String) rs[28]);
			obj.setInstalacion((Integer) rs[29]);
			obj.setReconexion((Integer) rs[30]);
			obj.setMateriales((Integer) rs[31]);
			obj.setTraslado((Integer) rs[32]);
			obj.setOtros((Integer) rs[33]);
			obj.setConcepto_aux((String) rs[34]);
			obj.setGenerador((Integer) rs[35]);
			obj.setId_usuario((Integer) rs[36]);
			obj.setFacturado_fecha((Integer) rs[37]);
			obj.setFacturado_hora((String) rs[38]);
			obj.setFacturado_id_resolucion((Integer) rs[39]);
			obj.setPasarela((String) rs[40]);
			obj.setFac_electronica((String) rs[41]);
			obj.setResultado_factura_electronica((String) rs[42]);
			obj.setNombre((String) rs[43]);
			obj.setRango_inicio((String) rs[44]);
			obj.setRango_final((String) rs[45]);
			obj.setPrefijo((String) rs[46]);
			obj.setNum_resolucion((String) rs[47]);
			obj.setVigencia((Integer)rs[48]);
			obj.setFecha_resolucion((Integer) rs[49]);			
			deudas.add(obj);
		}
		
		return deudas;
	}

	@Override
	public List<Object[]> findSalgoAnterior(Integer fecha, String consulta) {
		
		  List<Object[]> result = deudaDao.saldoAnterior(fecha, consulta);

		  return result;
	}
	
	

     
}
