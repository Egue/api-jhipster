package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.OrdenEstado;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IOrdenEstadoDao extends CrudRepository<OrdenEstado, Long> {
	
	
	@Query(value="SELECT * FROM ordenes_estados WHERE ordenes_estados.estacion != 1 \n"
			+ "AND ordenes_estados.estado = 1  \n"
			+ "AND ordenes_estados.reservada != 1 ORDER BY ordenes_estados.nombre", nativeQuery = true)
	public List<OrdenEstado> findAllByEstadoAndCliente();
	
	@Query(value="SELECT * FROM ordenes_estados WHERE ordenes_estados.estacion != 1 \n"
			+ "AND ordenes_estados.estado = 1 ORDER BY ordenes_estados.nombre", nativeQuery = true)
	public List<OrdenEstado> findAllByEstadoAndClienteADMIN();
}
