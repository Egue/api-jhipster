package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Tarifa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
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

	@Query(value = "select  DATE_FORMAT(c.marca , '%Y-%m-%d') as inicio , c.duracion  , c.nota , ti.valor instalacion, \n"+
	"CONCAT(u.nombre,' ', u.apellidos) as vendedor, c.estrato , c.fisico ,t.velocidad , t.valor mensualidad, \n" +
	"c.id_contrato , c.id_cliente, c.id_servicio from contratos c \n" +
	"inner join tarifas t  on t.id_tarifa = c.id_tarifa_promo \n"+ 
	"inner join tarifas_instalacion ti on ti.id_tarifa = c.id_tarifa_instalacion\n"+ 
	"inner join usuarios u on u.id_usuario = c.id_vendedor \n"+
	"WHERE c.id_contrato = :idContrato" , nativeQuery = true)
	public Optional<List<Object[]>> findInfoForFirma(@Param("idContrato") Long idContrato);
}
