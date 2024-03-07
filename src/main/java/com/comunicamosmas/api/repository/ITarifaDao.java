package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Tarifa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ITarifaDao extends CrudRepository<Tarifa, Long> {
	
	@Query(value="select distinct(ta.velocidad) as velocidad from tarifas  ta where ta.id_servicio = :idServicio and ta.id_tecnologia = :idTecnologia order by ta.velocidad" , nativeQuery= true)
	public List<Object[]> disctVelocidadByIdServicio(@Param("idServicio") Long idServicio , @Param("idTecnologia") Long idTecnologia);
	
	@Query(value=" select \n"
			+ " tarifas.id_tarifa,\n"
			+ " concat(tarifas.valor,'/', tarifas.nombre, '/',tarifas.codigo_mikrotik) as nombre\n"
			+ " from tarifas\n"
			+ " #id_tecnologia\n"
			+ " where tarifas.id_servicio = :idServicio and tarifas.velocidad = :velocidad and tarifas.tipo_tarifa = :tipoTarifa\n"
			+ " and tarifas.id_tecnologia = :idTecnologia and tarifas.valor = :valor and tarifas.estado = 1\n"
			+ " order by tarifas.valor" , nativeQuery=true)
	public List<Object[]> findTarifaForCambio(@Param("idServicio")Long idServicio , 
			@Param("velocidad")Long velocidad , @Param("tipoTarifa")Long tipoTarifa , 
			@Param("idTecnologia")Long idTecnologia , @Param("valor") Long valor);
	
	@Query(value="select distinct(ta.valor) from tarifas  ta \n"
			+ "where \n"
			+ "ta.id_servicio = :idServicio and \n"
			+ "ta.velocidad = :velocidad and \n"
			+ "ta.tipo_tarifa = :tipoTarifa and \n"
			+ "ta.id_tecnologia = :idTecnologia and \n"
			+ "ta.estado  = 1\n"
			+ "order by ta.valor" , nativeQuery =  true)
	public List<Object[]> findTarifaValor(@Param("idServicio")Long idServicio , 
			@Param("velocidad") Long velocidad , @Param("tipoTarifa")Long tipoTarifa , @Param("idTecnologia") Long idTecnologia);

	@Query(value="select velocidad from tarifas ta 	INNER JOIN contratos co ON co.id_tarifa_promo = ta.id_tarifa \n" 
				+ "WHERE co.id_contrato = :idContrato" , nativeQuery = true)
	public int findSpeedByIdContrato(@Param("idContrato") Long idContrato); 
}
