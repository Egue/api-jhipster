package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.repository.IEstacionDao;
import com.comunicamosmas.api.service.dto.EstacionDTO;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstacionServiceImpl implements IEstacionService {

    @Autowired
    IEstacionDao estacionDao;

    @Override
    public List<Estacion> findAll() {
        // TODO Auto-generated method stub
        return (List<Estacion>) estacionDao.findAll();
    }

    @Override
    public Estacion save(Estacion estacion) {
        estacionDao.save(estacion);
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        estacionDao.deleteById(id);
        return null;
    }

    @Override
    public Estacion findById(Long id) {
        return estacionDao.findById(id).orElse(null);
    }

    @Override
    public List<Estacion> findByNombreAndIdServicio(String nombreEstacion, Long idServicio) {
        // TODO Auto-generated method stub
        return (List<Estacion>) estacionDao.findByNombreAndIdServicio(nombreEstacion, idServicio);
    }

	@Override
	public List<EstacionDTO> findAllDTO(Long estado) {
		List<Object[]> result = estacionDao.findAllDTO(estado);
		List<EstacionDTO> estacion = new ArrayList<>();
		for(Object[] rs : result)
		{
			EstacionDTO object = new EstacionDTO();
			object.setId((Integer) rs[0]);
			object.setNombreEstacion((String) rs[1]);
			object.setTipo((String) rs[2]);
			object.setNombreComercial((String) rs[3]);
			object.setNombreServicio((String) rs[4]);
			object.setNombreCiudad((String) rs[5]);
			object.setNombreCiudad((String) rs[6]);
			object.setCodigo((String) rs[7]);
			object.setIp((String) rs[8]);
			estacion.add(object);

		}
		return estacion;
	}
}
