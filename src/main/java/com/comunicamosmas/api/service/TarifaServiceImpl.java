package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.Tarifa; 
import com.comunicamosmas.api.repository.ITarifaDao;
import com.comunicamosmas.api.service.dto.ContratosFirmasDTO.DatosServicio;
import com.comunicamosmas.api.service.dto.ContratosFirmasDTO;
import com.comunicamosmas.api.service.dto.DisctVelocidadDTO;
import com.comunicamosmas.api.service.dto.TarifasForCambioDTO;
import com.comunicamosmas.api.service.dto.valorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.mapping.Collection;
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

	@Override
	public int findSpeedByIdContrato(Long idContrato)
	{
		return tarifaDao.findSpeedByIdContrato(idContrato);
	}

	@Override
	public ContratosFirmasDTO.Datos findInfoServicioForFirmasContrato(Long idContrato) {
		// TODO Auto-generated method stub
		Optional<List<Object[]>> result = tarifaDao.findInfoForFirma(idContrato);
		
		List<ContratosFirmasDTO.Datos> firma = result.map(res->res.stream().map(rs->{
			ContratosFirmasDTO response = new ContratosFirmasDTO();
			ContratosFirmasDTO.Datos datos = response.new Datos();
			datos.setId_contrato((String) rs[9].toString());
			datos.setId_cliente((String) rs[10].toString());
			datos.setRegistro((String) rs[0]);
			datos.setVigencia((String) rs[1].toString());
			datos.setComentario((String) rs[2]);
			ContratosFirmasDTO.DatosServicio servicio = response.new DatosServicio();
			servicio.setInstalacion((String) rs[3].toString());
			servicio.setVelocidad((String) rs[7].toString());
			servicio.setMensualidad((String) rs[8].toString());
			datos.setDatos_servicio(servicio);
			datos.setNombre_asesor((String) rs[4]);
			ContratosFirmasDTO.DatosSuscriptor suscriptor = response.new DatosSuscriptor();
			suscriptor.setEstrato((String) rs[5].toString());
			suscriptor.setFisico((String) rs[6]);
			datos.setIdServicio((Integer) rs[11]);
			datos.setDatos_suscriptor(suscriptor);
			return datos;
		}).collect(Collectors.toList())).orElse(new ArrayList<>());

		return firma.get(0);
	}
 
}
