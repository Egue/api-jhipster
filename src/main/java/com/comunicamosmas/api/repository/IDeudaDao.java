package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Deuda;

import liquibase.pro.packaged.d;
import liquibase.pro.packaged.em;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IDeudaDao extends CrudRepository<Deuda, Long> {
	
	@Query(value="SELECT \n"
			+ "deu.id_deuda as idDeuda,\n"
			+ "deu.factura,\n"
			+ "deu.mes_servicio as periodo,\n"
			+ "CASE\n"
			+ "	WHEN generador = 3 THEN 'Creado Masivamente'\n"
			+ "    WHEN generador = 1 THEN 'Orden de servicio'\n"
			+ "    WHEN generador = 2 THEN 'Cargo Manual'\n"
			+ "END as generador,\n"
			+ "deu.valor_total as valor, \n"
			+ "deu.valor_parcial as  abono, \n"
			+ "deu.instalacion , deu.reconexion , deu.materiales , deu.traslado , deu.otros , deu.concepto_aux \n"
			+ "FROM deudas deu where deu.id_contrato = :contrato ORDER BY deu.id_deuda DESC" , 
			countQuery = "SELECT count(*) FROM deudas deu WHERE deu.id_contrato = :contrato" , 
			nativeQuery = true)
	public Page<Object[]> findByIdContrato(@Param("contrato") Long contrato , Pageable pageable);
	
	@Query(value="SELECT concat('$ ' , FORMAT(sum(deudas.valor_total - (deudas.valor_parcial)) , 2))  "
			+ "as parcial FROM deudas where deudas.id_contrato = :idContrato and deudas.estado in (1,3)" , nativeQuery=true)
	public String findDeudaByIdContrato(@Param("idContrato")Long idContrato);
	
	/**
	 * @param factura numero de factura
	 * @param mesServicio mes servicio de lo facturado
	 * @param idEmpresa id de la empresa que facturo
	 * */
	 
	@Query(value="select deudas.* , servicios.nombre, \n"
			+ "res.rango_inicio , \n"
			+ "res.rango_final, res.prefijo, res.num_resolucion, \n"
			+ "res.vigencia, res.fecha_resolucion from deudas \n"
			+ "inner join servicios on servicios.id_servicio = deudas.id_servicio\n"
			+ "left join resoluciones res on res.id_resolucion = deudas.facturado_id_resolucion\n"
			+ "where deudas.factura = :factura and deudas.facturado_fecha like concat('%',:mesServicio,'%') and deudas.id_empresa = :idEmpresa and deudas.id_cliente = :idCliente and deudas.refiere = :origen ", nativeQuery=true)
	public Optional<List<Object[]>> findDeudaByFacturaAndMesServiceAndIdEmpresa(@Param("factura") Long factura , 
	@Param("mesServicio") Long mesServicio , @Param("idEmpresa") Long idEmpresa , @Param("idCliente")Integer idCliente , @Param("origen") String origen);

	@Query(value="SELECT deudas.factura, deudas.refiere , cli.documento, cli.id_cliente, cli.mail, \n" 
	+" CASE WHEN cli.tipo_cliente ='J' THEN cli.razon_social  \n"
	+" 		WHEN cli.tipo_cliente ='N' THEN concat(cli.apellido_paterno, ' ', cli.apellido_materno, ' ', cli.nombre_primer, ' ', cli.nombre_segundo)  \n"
	+" 		END as nombreCliente,  \n"
	+" servi.id_servicio , servi.nombre FROM deudas \n"
	+"INNER JOIN clientes cli ON cli.id_cliente = deudas.id_cliente \n"
	+"INNER JOIN servicios servi ON servi.id_servicio = deudas.id_servicio \n"
	+"WHERE deudas.id_empresa = :empresa AND deudas.facturado_fecha like concat('%',:mesServicio,'%') AND deudas.factura = :factura AND deudas.refiere = :origen group by deudas.id_contrato \n", nativeQuery=true)
	public Optional<List<Object[]>> findDeudaByFacturaAndMesEmpresaOrigen(@Param("factura") Integer factura ,@Param("mesServicio")Integer messervicio , @Param("empresa") Long idEmpresa , @Param("origen") String origen);
 
	
	/***/
	
	@Query(value="select deudas.id_deuda, deudas.valor_parcial, deudas.valor_total from deudas \n"
			+ "where deudas.facturado_fecha < :fecha \n"
			+ "and deudas.id_contrato in (:consulta)\n"
			+ "and deudas.factura != 0\n"
			+ "and deudas.estado in (1,3)" , nativeQuery = true)
	public Optional<List<Object[]>> saldoAnterior(@Param("fecha") Integer fecha , @Param("consulta") List<Integer> consulta);

	//*cargar deudas de un cliente */
	@Query(value = "SELECT deudas.id_deuda, \n"
	+"deudas.id_servicio, deudas.id_empresa, deudas.id_contrato, deudas.id_cliente, \n" 
	+"deudas.valor_parcial, deudas.valor_total  , deudas.mes_servicio FROM deudas WHERE id_contrato = :contrato AND estado IN (1,3) ORDER BY id_deuda ASC" , nativeQuery = true)
	public Optional<List<Object[]>> deudasByIdContrato(@Param("contrato") Long idContrato);

	//
	public Optional<List<Deuda>> findByIdIn(List<Long> ids);
  
	public Optional<List<Deuda>> findByFacturadoFechaLike(Integer mes );

	@Query(value="SELECT * FROM deudas d WHERE d.id_cliente = :idCliente AND d.estado IN (1,3) AND d.factura > 0" , nativeQuery=true)
    public Optional<List<Deuda>> findActiveDebtsWithInvoiceByClientId(@Param("idCliente") Long idCliente);

	@Query(value="SELECT \n"
+"concat(em.razon_social , ' ', em.nombre_comercial) as empresa, \n"
+"ser.nombre, \n"
+"d.id_deuda, \n"
+" d.factura \n"
+"d.refiere, \n"
+"CASE  \n"
+"	when d.estado = '1' THEN 'Pendiente' \n"
  +"  when d.estado = '2' THEN 'Pagado' \n"
+" when d.estado = '3' THEN 'Parcial' \n"
+" END as estado, \n"
+" CASE \n"
+"	WHEN cli.tipo_cliente = 'J' THEN CONCAT(cli.razon_social , '/' , cli.documento , '-' , cli.dv) ELSE CONCAT(cli.apellido_paterno, ' ', cli.apellido_materno, ' ', cli.nombre_primer, ' ', cli.nombre_segundo , '/', cli.documento , '-',cli.dv) end as name_cliente, \n"
+" co.id_contrato , \n"
+" concat(dir.barrio , '/',dir.tipo,'/',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo, ' ',dir.b_tipo,' ',b_numero,' ',dir.b_letra,' ',dir.numero) as direccion, \n"
+" cli.mail, \n"
+" cli.celular_a, \n"
+" cli.celular_b, \n"
+" d.valor_base , \n"
+" d.valor_iva, \n"
+" d.valor_reteiva, \n"
+" d.valor_refuente, \n"
+" d.valor_reteica, \n"
+" d.valor_bomberil, \n"
+" d.valor_otrosimp, \n"
+" d.valor_parcial, \n"
+" d.valor_total, \n"
+" d.instalacion, \n"
+" d.reconexion, \n"
+" d.materiales, \n"
+" d.traslado, \n"
+" d.otros, \n"
+" d.concepto_aux, \n"
+" d.facturado_fecha, \n"
+" d.fac_electronica, \n"
+" d.facturado_hora \n"
+" FROM deudas d \n"
+" inner join clientes cli on cli.id_cliente = d.id_cliente \n"
+" inner join contratos co on co.id_contrato = d.id_contrato \n"
+" inner join direcciones dir on dir.id_direccion = co.id_direccion_servicio \n"
 +" inner join servicios ser on ser.id_servicio = d.id_servicio \n"
 +" inner join empresas em on em.id_empresa = d.id_empresa \n"
+" WHERE d.id_servicio in(:servicios) and d.facturado_fecha between :inicio and :fin")
public Optional<List<Object[]>> invoiceByServiceAndDate(@Param("servicios")List<Integer> servicios , @Param("inicio") Integer inicio , @Param("final") Integer fin);

}
