package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MigracionTarifa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IMigracionTarifaDao extends CrudRepository<MigracionTarifa, Long> {
	
	@Query(value="SELECT co.id_contrato as idContrato, \n"
			+ "case  \n"
			+ "WHEN cli.tipo_cliente ='N' then concat(cli.apellido_paterno,' ',cli.apellido_materno,' ', cli.nombre_primer, ' ', cli.nombre_segundo)\n"
			+ "WHEN cli.tipo_cliente = 'J' then concat(cli.razon_social) \n"
			+ "END as nombreCliente  ,\n"
			+ "cli.documento,\n"
			+ "ta_promo.nombre as nombreTarifaPromo,\n"
			+ "ta_promo.valor as valorTarifaPromo,\n"
			+ "ta_promo.velocidad as velocidadTarifaPromo,\n"
			+ "ta.nombre as nombreTarifaGeneral,\n"
			+ "ta.valor as valorTarifaGeneral,\n"
			+ "ta.velocidad as velocidadTarifaGeneral,\n"
			+ "tipo.nombre as nombreTecnologia,\n"
			+ "co.id_servicio idServicio,\n"
			+ "cli.tipo_cliente as tipoCliente,\n"
			+ "tipo.id_tecnologia as idTecnologia,\n"
			+ "co.id_empresa as idEmpresa, \n"
			+ "co.id_ciudad as idCiudad, \n"
			+ "co.id_cliente as idCliente, \n"
			+ "co.grupo as lugar, \n"
			+ "co.id_tarifa_promo as idTarifaPromo,  \n"
			+ "co.id_tarifa as idTarifa \n"
			+ "from contratos co\n"
			+ "inner join clientes cli on cli.id_cliente = co.id_cliente\n"
			+ "inner join tarifas ta_promo on ta_promo.id_tarifa = co.id_tarifa_promo\n"
			+ "inner join tarifas ta on ta.id_tarifa = co.id_tarifa\n"
			+ "inner join tipos_tecnologia tipo on tipo.id_tecnologia = co.id_tecnologia\n"
			+ "where co.id_contrato = :idContrato", nativeQuery=true)
		public List<Object[]> infoContratoMigracion(@Param("idContrato") Long idContrato);
		
		@Query(value="SELECT \n"
				+ "migra.id_migracion as idMigracion, \n"
				+ "migra.id_contrato as idContrato,\n"
				+ "migra.id_tarifa_general as idGeneralAntes,\n"
				+ "migra.id_tarifa_promo as idPromoAntes,\n"
				+ "migra.id_tarifa_general_new as idGeneralNew,\n"
				+ "migra.id_tarifa_promo_new as idPromoNew,\n"
				+ "migra.id_servicio as idServicio,\n"
				+ "migra.id_empresa as idEmpresa,\n"
				+ "concat(ta_ge.nombre,'/',ta_ge.velocidad,'/',ta_ge.codigo_mikrotik) as tarifaAnteriorGeneral,\n"
				+ "concat(ta_pro.nombre,'/',ta_pro.velocidad,'/',ta_pro.codigo_mikrotik) as tarifaAnteriorPromo,\n"
				+ "concat(ta_ge_new.nombre,'/',ta_ge_new.velocidad,'/',ta_ge_new.codigo_mikrotik) as tarifaNewGeneral,\n"
				+ "concat(ta_pro_new.nombre,'/',ta_pro_new.velocidad,'/',ta_pro_new.codigo_mikrotik) as tarifaNewPro,\n"
				+ "concat(usuarios.nombre,'/',usuarios.apellidos) as usuario,\n"
				+ "migra.justificacion\n"
				+ "from migracion_tarifas migra\n"
				+ "inner join tarifas ta_ge on ta_ge.id_tarifa = migra.id_tarifa_general\n"
				+ "inner join tarifas ta_pro on ta_pro.id_tarifa = migra.id_tarifa_promo\n"
				+ "inner join tarifas ta_ge_new on ta_ge_new.id_tarifa = migra.id_tarifa_general_new\n"
				+ "inner join tarifas ta_pro_new on ta_pro_new.id_tarifa = migra.id_tarifa_promo_new\n"
				+ "inner join usuarios on usuarios.id_usuario = migra.id_usuario\n"
				+ "where migra.id_contrato = :idContrato AND migra.estado = 0" , nativeQuery=true)
		public List<Object[]> findMigraByIdContrato(@Param("idContrato") Long idContrato);
}
