package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.Deuda; 
import com.comunicamosmas.api.repository.IDeudaDao;
import com.comunicamosmas.api.repository.IPagoDao;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;
import com.comunicamosmas.api.service.dto.PagosEstadoCuentaDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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

     
}
