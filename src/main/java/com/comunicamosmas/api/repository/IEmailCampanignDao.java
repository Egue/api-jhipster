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
			+ "empresas.nombre_comercial as nombreComercial\n"
			+ " FROM email_campaign as camp INNER JOIN empresas on empresas.id_empresa = camp.id_empresa" , nativeQuery = true)
	public List<Object[]> findAllEmailCampaign();
	
	@Query(value="SELECT \n"
			+ "	camp.id, \n"
			+ " camp.anno, \n"
			+ "	camp.fecha, \n"
			+ "	camp.fecha_limite_pago as fechaLimitePago, \n"
			+ "	camp.mes, \n"
			+ "	camp.nombre, \n"
			+ "	empresas.nombre_comercial as nombreComercial \n"
			+ "	FROM email_campaign as camp INNER JOIN empresas on empresas.id_empresa = camp.id_empresa "
			+ " WHERE camp.id = :id", nativeQuery=true)
	public List<Object[]> findByIdDTO(@Param("id") Integer id);
}
