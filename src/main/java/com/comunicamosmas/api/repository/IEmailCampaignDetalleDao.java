package com.comunicamosmas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.EmailCampaignDetalle;

public interface IEmailCampaignDetalleDao extends CrudRepository<EmailCampaignDetalle , Integer>{
	
	@Query(value="SELECT deudas.factura , clientes.id_cliente, clientes.mail , contratos.id_servicio , deudas.refiere FROM deudas \n"
					+ "			inner join contratos on contratos.id_contrato = deudas.id_contrato \n"
					+ "			inner join clientes on contratos.id_cliente = clientes.id_cliente\n"
					+ " 	 where deudas.facturado_fecha like concat('%',:fecha,'%')  \n"
					+ "			 and deudas.id_empresa = :idEmpresa and contratos.preferencia_factura = 2 group by deudas.factura" , nativeQuery=true)
	public List<Object[]> findEmailBySend(@Param("idEmpresa")Long idEmpresa , @Param("fecha") String fecha);
	
	@Query(value="SELECT  \n"
			+ "			detalle.factura as factura,\n"
			+ "			detalle.id_cliente as idCliente,\n"
			+ "			detalle.email,\n"
			+ "			detalle.respuesta_mail_relay response,\n"
			+ "			case \n"
			+ "				when procesado = 0 THEN 'Sin procesar'\n"
			+ "			    when procesado = 1 then 'Procesado'\n"
			+ "			end procesado,\n"
			+ "			sr.nombre as nombreServicio,\n"
			+ "			cl.tipo_cliente tipoCliente,\n"
			+ "			case\n"
			+ "				when cl.tipo_cliente = 'N' then concat(cl.apellido_paterno, ' ', cl.apellido_materno, ' ', cl.nombre_primer, ' ', cl.nombre_segundo)\n"
			+ "			    when cl.tipo_cliente = 'J' then cl.razon_social\n"
			+ "			end nombreCliente , detalle.id  , detalle.id_email_campaing , detalle.origen , detalle.name_document\n"
			+ "			FROM email_campaing_detalle detalle\n"
			+ "			inner join clientes cl on cl.id_cliente = detalle.id_cliente\n"
			+ "			inner join servicios sr on sr.id_servicio = detalle.id_servicio\n"
			+ "			where detalle.id_email_campaing = :id" , nativeQuery = true)
	public Optional<List<Object[]>> findByIdEmailCampaign(@Param("id") Long id);

	//busacar detalle de campaña por idCampaña y procesados 0
	@Query(value="SELECT  \n"
	+ "			detalle.factura as factura,\n"
	+ "			detalle.id_cliente as idCliente,\n"
	+ "			detalle.email,\n"
	+ "			detalle.respuesta_mail_relay response,\n"
	+ "			case \n"
	+ "				when procesado = 0 THEN 'Sin procesar'\n"
	+ "			    when procesado = 1 then 'Procesado'\n"
	+ "			end procesado,\n"
	+ "			sr.nombre as nombreServicio,\n"
	+ "			cl.tipo_cliente tipoCliente,\n"
	+ "			case\n"
	+ "				when cl.tipo_cliente = 'N' then concat(cl.apellido_paterno, ' ', cl.apellido_materno, ' ', cl.nombre_primer, ' ', cl.nombre_segundo)\n"
	+ "			    when cl.tipo_cliente = 'J' then cl.razon_social\n"
	+ "			end nombreCliente , detalle.id  , detalle.id_email_campaing\n"
	+ "			FROM email_campaing_detalle detalle\n"
	+ "			inner join clientes cl on cl.id_cliente = detalle.id_cliente\n"
	+ "			inner join servicios sr on sr.id_servicio = detalle.id_servicio\n"
	+ "			where detalle.id_email_campaing = :idEmailCampaign AND detalle.procesado != 1" , nativeQuery = true)
	public List<Object[]> findEmailCampaignDetalleSinProcesar(@Param("idEmailCampaign") Integer idEmailCampaign);


	@Query(value="SELECT * FROM email_campaing_detalle detalle WHERE detalle.factura = :factura AND detalle.id_email_campaing = :idCampaign", nativeQuery=true)
	public Optional<List<Object[]>> validExiste(@Param("factura") String factura , @Param("idCampaign") Integer idCampaign);

}
