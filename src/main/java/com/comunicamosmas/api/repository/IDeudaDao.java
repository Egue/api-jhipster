package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Deuda;

import java.util.List;
import java.util.Optional;

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
			+ "deu.valor_parcial as  abono \n"
			+ "\n"
			+ "FROM deudas deu where deu.id_contrato = :contrato" , nativeQuery = true)
	public List<Object[]> findByIdContrato(@Param("contrato") Long contrato);
	
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
	+"deudas.id_servicio, deudas.id_empresa, deudas.id_contrato, deudas.id_cliente \n" 
	+"deudas.valor_parcial, deudas.valor_total FROM deudas WHERE id_contrato = :contrato AND estado IN (1,3) ORDER BY id_deuda DESC" , nativeQuery = true)
	public Optional<List<Object[]>> findDeudaByidContrato(@Param("contrato") Long idContrato);

}
