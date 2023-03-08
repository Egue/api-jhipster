package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.Tarifa; 
import com.comunicamosmas.api.repository.ITarifaDao;
import com.comunicamosmas.api.service.dto.DisctVelocidadDTO;
import com.comunicamosmas.api.service.dto.TarifasForCambioDTO;
import com.comunicamosmas.api.service.dto.valorDTO;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifaServiceImpl implements ITarifaService {

    @Autowired
    ITarifaDao tarifaDao;

	@Override
	public List<Tarifa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarifa save(Tarifa tarifa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarifa findById(Long id) {
		
		return tarifaDao.findById(id).orElse(null);
	}

	@Override
	public List<DisctVelocidadDTO> findVelocidadDisctByIdServicio(Long idServicio, Long idTecnologia) {
		List<Object[]> result = tarifaDao.disctVelocidadByIdServicio(idServicio , idTecnologia);
		List<DisctVelocidadDTO> velocidad = new ArrayList<>();
		for(Object[] rs : result)
		{
			DisctVelocidadDTO object = new DisctVelocidadDTO();
			object.setVelocidad((Integer) rs[0]);
			velocidad.add(object);
		}
		return velocidad;
	}

	@Override
	public List<TarifasForCambioDTO> tarifasForCambio(Long idServicio, Long velocidad, Long tipoTarifa,
			Long idTecnologia , Long valor) {
		List<Object[]> result = tarifaDao.findTarifaForCambio(idServicio, velocidad, tipoTarifa, idTecnologia, valor);
		List<TarifasForCambioDTO> tarifas = new ArrayList<>();
		for(Object[] rs :result)
		{
			TarifasForCambioDTO object = new  TarifasForCambioDTO();
			object.setIdTarifa((Integer) rs[0]);
			object.setNombre((String) rs[1]);
			
			tarifas.add(object);
		}
		return tarifas;
	}

	@Override
	public List<valorDTO> findTarifaValor(Long idServicio, Long velocidad, Long tipoTarifa , Long idTecnologia) {
		// TODO Auto-generated method stub
		List<Object[]> result = tarifaDao.findTarifaValor(idServicio, velocidad, tipoTarifa , idTecnologia);
		
		List<valorDTO> valor  = new ArrayList<>();
		
		for(Object[] rs : result)
		{
			valorDTO object = new valorDTO();
			object.setValor((Integer) rs[0]);
			
			valor.add(object);
		}
		return valor;
	}
 
}
