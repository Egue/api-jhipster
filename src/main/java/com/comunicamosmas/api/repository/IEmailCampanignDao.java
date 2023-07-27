package com.comunicamosmas.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.EmailCampaign;

public interface IEmailCampanignDao extends CrudRepository< EmailCampaign, Integer>{

	@Query(value="SELECT \n"
			+ "camp.id,\n"
			+ "camp.anno,\n"
			+ "camp.fecha,\n"
			+ "camp.fecha_limite_pago as fechaLimitePago,\n"
			+ "camp.mes,\n"
			+ "camp.nombre,\n"
			+ "empresas.nombre_comercial as nombreComercial,\n"
			+ "camp.fecha_de_corte,\n"
			+ "camp.estado\n"
			+ " FROM email_campaign as camp INNER JOIN empresas on empresas.id_empresa = camp.id_empresa" , nativeQuery = true)
	public List<Object[]> findAllEmailCampaign();
	
	@Query(value="SELECT \n"
			+ "	camp.id, \n"
			+ " camp.anno, \n"
			+ "	camp.fecha, \n"
			+ "	camp.fecha_limite_pago as fechaLimitePago, \n"
			+ "	camp.mes, \n"
			+ "	camp.nombre, \n"
			+ "	empresas.nombre_comercial as nombreComercial, \n"
			+ "camp.fecha_de_corte,\n"
			+ "camp.estado\n"
			+ "	FROM email_campaign as camp INNER JOIN empresas on empresas.id_empresa = camp.id_empresa "
			+ " WHERE camp.id = :id", nativeQuery=true)
	public List<Object[]> findByIdDTO(@Param("id") Integer id);

	/** 
	 * campaña con estado Abierto limit 1
	*/
	@Query(value="SELECT * FROM email_campaign WHERE email_campaign.estado = 'Abierto' LIMIT 0,1" ,  nativeQuery = true)
	public EmailCampaign findCampaignLimitOne();

	@Query(value="SELECT \n"
	+ "camp.id,\n"
	+ "camp.anno,\n"
	+ "camp.fecha,\n"
	+ "camp.fecha_limite_pago as fechaLimitePago,\n"
	+ "camp.mes,\n"
	+ "camp.nombre,\n"
	+ "empresas.nombre_comercial as nombreComercial,\n"
	+ "camp.fecha_de_corte,\n"
	+ "camp.estado\n"
	+ " FROM email_campaign as camp INNER JOIN empresas on empresas.id_empresa = camp.id_empresa \n"
	+ "WHERE camp.id_empresa = :idEmpresa AND camp.fecha LIKE concat('%' , :fecha , '%')" , nativeQuery = true)
	public List<Object[]> filterEmailCampaign(@Param("idEmpresa")Long idEmpresa , @Param("fecha") String fecha);

}
